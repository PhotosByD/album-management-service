package si.photos.by.d.album.models.entities;

import si.photos.by.d.album.models.dtos.Photo;

import javax.persistence.*;
import java.util.List;

@Entity(name="album")
@NamedQueries(value =
        {
                @NamedQuery(name = "Album.getAll", query = "SELECT a FROM album a")
        })
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "album_name")
    private String name;

    @Column(name = "user_id")
    private Integer userId;

    @Transient
    private List<Photo> photos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
