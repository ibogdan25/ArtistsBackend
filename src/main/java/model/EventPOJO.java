package model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import utils.EventDeserializer;

import java.time.LocalDateTime;

@JsonDeserialize(using = EventDeserializer.class)
public class EventPOJO {
    @Getter @Setter
    private long id;

    @Getter @Setter
    private String title;

    @Getter @Setter
    private String description;

    @Getter @Setter
    private LocalDateTime startTime;

    @Getter @Setter
    private LocalDateTime endTime;

    @Getter @Setter
    private String urlPoster;

    @Getter @Setter
    private Address address;

    @Getter @Setter
    private String artists;

    @Getter @Setter
    private String organizers;

    @Getter @Setter
    private String linksToTickets;

    @Getter @Setter
    private User user;
}
