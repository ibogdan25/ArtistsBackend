package model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "artist_posts")
@Setter
@Getter
public class ArtistPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_artist_post")
    private Long idArtistPost;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    //imaginile vor fi splituite dupa ;
    // ex : url1,url2,url3
    @Column(name = "images")
    private String images;


    @Column(name="date")
    private LocalDateTime date = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name="artist_id", nullable = false)
    @JsonManagedReference
    private Artist byArtist;

}