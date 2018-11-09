package si.photos.by.d.album.api.v1.resources;


import si.photos.by.d.album.models.entities.Album;
import si.photos.by.d.album.services.beans.AlbumBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
@Path("/albums")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AlbumResources {
    @Context
    private UriInfo uriInfo;

    @Inject
    private AlbumBean albumBean;

    @GET
    public Response getAlbums() {
        List<Album> albums = albumBean.getAlbums(uriInfo);

        return Response.ok(albums).build();
    }

    @GET
    @Path("/user/{userId}")
    public Response getAlbumsForUser(@PathParam("userId") Integer userId) {
        List<Album> albums = albumBean.getAlbumsForUser(userId);
        return Response.ok(albums).build();
    }

    @GET
    @Path("/{id}")
    public Response getAlbum(@PathParam("id") Integer id) {
        Album album = albumBean.getAlbum(id);
        if (album == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(album).build();
    }
}
