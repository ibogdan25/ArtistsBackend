package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterPOJO {
    private String username;
    private String password;
    private String repeatPassword;
    private String email;
}
