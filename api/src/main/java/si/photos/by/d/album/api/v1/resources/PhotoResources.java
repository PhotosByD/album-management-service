package si.photos.by.d.album.api.v1.resources;


import si.photos.by.d.album.models.entities.Album;
import si.photos.by.d.album.services.beans.AlbumBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
@Path("/albums")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PhotoResources {
    @Context
    private UriInfo uriInfo;

    @Inject
    private AlbumBean albumBean;

    @GET
    public Response getPhotos() {
        List<Album> photos = albumBean.getAlbums();

        return Response.ok(photos).build();
    }
}
