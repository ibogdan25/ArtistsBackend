package model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "artistcategories")
@Setter
@Getter
public class ArtistCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_artist_category")
    private Long idArtistCategory;

    @Column(name = "name")
    private String name;


    @OneToMany(mappedBy = "artistCategory")
    @JsonBackReference
    private Set<ArtistSubcategory> artistSubcategorySet;


    public ArtistCategory() {
    }

    public ArtistCategory(String name) {
        this.name = name;
    }
}
