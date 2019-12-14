package model;

import lombok.Getter;
import lombok.Setter;


public class UserPOJO {
    @Getter
    @Setter
    private String userOrEmail;
    @Getter
    @Setter
    private String password;

    @Override
    public String toString() {
        return "UserPOJO{" +
                "userOrEmail='" + userOrEmail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
