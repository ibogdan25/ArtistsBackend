package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.SessionServiceImpl;
import service.UserServiceImpl;

import java.io.IOException;
import java.util.logging.Logger;

@RestController
public class UserController {
    @Autowired
    private SessionServiceImpl sessionService;
    @Autowired
    private UserServiceImpl userService;

    Logger log = Logger.getLogger(UserController.class.getName());

    @RequestMapping(value = "/user/connect", method = RequestMethod.POST)
    public ResponseEntity connect(@RequestBody String json) {
        final ObjectMapper objectMapper = new ObjectMapper();
        UserPOJO userPOJO = null;
        try {
            userPOJO = objectMapper.readValue(json, UserPOJO.class);
        } catch (IOException e) {
            log.info(String.format("BAD_REQUEST for %s", json.replaceAll("\n", "")));
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        final User user = userService.getUser(userPOJO.getUserOrEmail(), userPOJO.getPassword());
        if (user == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        final Session session = sessionService.createSession(user);
        if (session != null) {
            return new ResponseEntity(session.getSessionToken(), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody String json) {
        final ObjectMapper objectMapper = new ObjectMapper();
        UserRegisterPOJO userRegisterPOJO = null;
        try {
            userRegisterPOJO = objectMapper.readValue(json, UserRegisterPOJO.class);
        } catch (IOException e) {
            log.info(String.format("BAD_REQUEST for %s", json.replaceAll("\n", "")));
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (!userRegisterPOJO.getPassword().equals(userRegisterPOJO.getRepeatPassword())) {
            return new ResponseEntity("Passowrd and repeatPassword must match.", HttpStatus.BAD_REQUEST);
        }
        RegisterState registerState = userService.registerUser(userRegisterPOJO.getUsername(), userRegisterPOJO.getPassword(), userRegisterPOJO.getEmail());
        switch (registerState) {
            case EMAIL_DUPLICATE:
                return new ResponseEntity("Email exists.", HttpStatus.BAD_REQUEST);
            case USERNAME_DUPLICATE:
                return new ResponseEntity("Username exists.", HttpStatus.BAD_REQUEST);
            case PASSOWRD_WEAK:
                return new ResponseEntity("Password weak.", HttpStatus.BAD_REQUEST);
            case REGISTERED:
                return new ResponseEntity("User registered.", HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}