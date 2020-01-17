package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArtistPostPOJO {
    private String title;
    private String description;
    private String images;
    private String date;
    private Long idArtist;
}
