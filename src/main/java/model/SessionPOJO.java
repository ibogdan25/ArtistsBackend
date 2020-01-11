package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionPOJO {
    private final String token;

    public SessionPOJO(String token) {
        this.token = token;
    }
}
