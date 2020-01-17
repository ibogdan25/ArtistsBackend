package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArtistWrapper implements WrapperInterface {
    private Artist artist;

    public ArtistWrapper(Artist artist) {
        this.artist = artist;
    }

    @Override
    @JsonIgnore
    public Object getEntity() {
        return artist;
    }
}
