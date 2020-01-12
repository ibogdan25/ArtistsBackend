package model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import utils.EventDeserializer;

@JsonDeserialize(using = EventDeserializer.class)
public class EventPOJO {
    @Getter @Setter
    private long id;

    @Getter @Setter
    private String title;

    @Getter @Setter
    private String description;

    @Getter @Setter
    private String startTime;

    @Getter @Setter
    private String endTime;

    @Getter @Setter
    private String poster;

    @Getter @Setter
    private Address address;
}
