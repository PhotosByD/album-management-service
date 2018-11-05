package si.photos.by.d.album.services.beans;

import com.kumuluz.ee.discovery.annotations.DiscoverService;
import si.photos.by.d.album.models.entities.Album;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.util.List;
import java.util.Optional;

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

    @PostConstruct
    private void init() {
        // This here will connect to photo service and get me photos for user
        httpClient = ClientBuilder.newClient();
        //baseUrl = "http://localhost:8081"; // only for demonstration
    }

    public List<Album> getAlbums() {
        TypedQuery<Album> query = em.createNamedQuery("Album.getAll", Album.class);
        return query.getResultList();
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
