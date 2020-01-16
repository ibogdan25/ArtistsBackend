package model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import utils.EventDeserializer;

import java.time.LocalDateTime;

@JsonDeserialize(using = EventDeserializer.class)
public class EventFollowPOJO {

    @Getter
    @Setter
    private Event event;

    @Getter
    @Setter
    private LocalDateTime data;


    @Override
    public String toString() {
        return "EventFollowPOJO{" +
                "event=" + event +
                ", data=" + data +
                '}';
    }
}
