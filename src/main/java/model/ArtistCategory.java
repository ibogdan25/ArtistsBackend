package model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "artistcategories")
@Setter
@Getter
public class ArtistCategory {
    @Id
    @GeneratedValue
    @Column(name = "id_artist_category")
    private Integer idArtistCategory;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public ArtistCategory() {
    }

    public ArtistCategory(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
