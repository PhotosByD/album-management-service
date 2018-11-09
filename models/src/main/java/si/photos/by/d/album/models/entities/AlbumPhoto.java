package si.photos.by.d.album.models.entities;

import javax.persistence.*;

@Entity(name="album_photo")
@NamedQueries(value =
        {
                @NamedQuery(name = "AlbumPhoto.getAll", query = "SELECT a FROM album_photo a")
        })
public class AlbumPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "album_id")
    private Integer albumId;

    @Column(name = "photo_id")
    private Integer photoId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public Integer getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Integer photoId) {
        this.photoId = photoId;
    }
}
