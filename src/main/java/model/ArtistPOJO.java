package model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import utils.ArtistDeserializer;

@JsonDeserialize(using = ArtistDeserializer.class)
public class ArtistPOJO {
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String description;

    @Getter @Setter
    private String avatarUrl;

    @Getter @Setter
    private String coverUrl;

    @Getter @Setter
    private Integer stars;

    @Getter @Setter
    private ContactInfo contactInfo;

    @Getter @Setter
    private User user;

    @Getter @Setter
    private ArtistSubcategory artistSubcategory;

    @Getter @Setter
    private String education;

    @Getter @Setter
    private String awards;

    @Getter @Setter
    private String pastEvents;

    @Getter @Setter
    private String highlightedWork;
}
