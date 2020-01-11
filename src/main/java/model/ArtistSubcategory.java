package model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "artistsubcategories")
@Setter
@Getter
public class ArtistSubcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_artist_subcategory")
    private Long idArtistSubcategory;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name="id_artist_category", nullable = false)
    @JsonManagedReference
    private ArtistCategory artistCategory;

    public ArtistSubcategory(String name) {
        this.name = name;
    }

    public ArtistSubcategory() {
    }
}
