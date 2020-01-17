package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventWrapper implements WrapperInterface {
    private Event event;

    public EventWrapper(Event event) {
        this.event = event;
    }

    @Override
    @JsonIgnore
    public Object getEntity() {
        return event;
    }
}
