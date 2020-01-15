package model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import utils.EventDeserializer;

import java.time.LocalDateTime;

@JsonDeserialize(using = EventDeserializer.class)
public class ArtistFollowPOJO{

    @Setter @Getter
    private Artist artist;

    @Getter @Setter
    private LocalDateTime data;


    @Override
    public String toString() {
        return "ArtistFollowPOJO{" +
                "artist=" + artist +
                ", data=" + data +
                '}';
    }
}
