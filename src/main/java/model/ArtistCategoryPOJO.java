package model;

import lombok.Getter;
import lombok.Setter;

public class ArtistCategoryPOJO {

    @Getter
    @Setter
    private long idArtistCategory;
    @Getter
    @Setter
    private String name;


    @Override
    public String toString() {
        return "ArtistCategoryPOJO{" +
                "idArtistCategory=" + idArtistCategory +
                ", name='" + name + '\'' +
                '}';
    }
}
