package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventPostPOJO {
    private String title;
    private String description;
    private String date;
    private String images;
    private Long idEvent;
}
