package model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArtistReviewPOJO {
    private String comment;
    private Integer rating;
    private Long artist;
}

