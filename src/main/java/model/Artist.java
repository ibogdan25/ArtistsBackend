package model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "artists")
@Getter @Setter
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artist_id")
    private Long artistId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    @JsonManagedReference
    private User user;

    @OneToOne
    @JoinColumn(name="id_artist_subcategory", nullable = false)
    @JsonManagedReference
    private ArtistSubcategory artistSubcategory;
}
