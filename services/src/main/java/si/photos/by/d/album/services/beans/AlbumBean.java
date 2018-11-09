package si.photos.by.d.album.services.beans;

import com.kumuluz.ee.discovery.annotations.DiscoverService;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.photos.by.d.album.models.dtos.Photo;
import si.photos.by.d.album.models.entities.Album;
import si.photos.by.d.album.models.entities.AlbumPhoto;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@ApplicationScoped
public class AlbumBean {
    @Inject
    private EntityManager em;

    @Inject
    @DiscoverService("photo-management-service")
    private Optional<String> photoUrl;

    @Inject
    @DiscoverService("user-management-service")
    private Optional<String> userUrl;

    private Client httpClient;

    private Logger log = Logger.getLogger(AlbumBean.class.getName());

    @PostConstruct
    private void init() {
        // This here will connect to photo service and get me photos for user
        httpClient = ClientBuilder.newClient();
        //baseUrl = "http://localhost:8081"; // only for demonstration
    }

    public List<Album> getAlbums(UriInfo uriInfo) {
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery())
                .defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, Album.class, queryParameters);
    }

    public List<Album> getAlbumsForUser(Integer id) {
        TypedQuery<Album> query = em.createQuery("SELECT a FROM album a WHERE a.userId = :id", Album.class);
        query.setParameter("id", id);

        return query.getResultList();
    }

    public Album getAlbum(Integer id) {
        TypedQuery<Album> query = em.createQuery("SELECT a FROM album a WHERE a.id = :id", Album.class);
        query.setParameter("id", id);

        Album album = query.getSingleResult();

        album.setPhotos(getPhotosForAlbum(id));

        return album;
    }

    private List<Photo> getPhotosForAlbum(Integer albumId) {
        if (photoUrl.isPresent()) {
            TypedQuery<AlbumPhoto> query = em.createQuery("SELECT a FROM album_photo a WHERE a.albumId = :id", AlbumPhoto.class);
            query.setParameter("id", albumId);
            List<AlbumPhoto> albumPhotos = query.getResultList();
            String  photoIds = "";
            for(AlbumPhoto a : albumPhotos) {
                photoIds = photoIds.concat(Integer.toString(a.getPhotoId())).concat(",");
            }
            try {
                return httpClient
                        .target(photoUrl.get() + "/v1/photos?where=id:IN:[" + photoIds + "]")
                        .request().get(new GenericType<List<Photo>>() {
                        });
            } catch (WebApplicationException | ProcessingException e) {
                log.severe(e.getMessage());
                throw new InternalServerErrorException(e);
            }
        }
        return null;
    }

    private void beginTx() {
        if (!em.getTransaction().isActive())
            em.getTransaction().begin();
    }

    private void commitTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().commit();
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().rollback();
    }
}
