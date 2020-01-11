package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorPOJO {
    private final String message;

    public ErrorPOJO(String message) {
        this.message = message;
    }
}
