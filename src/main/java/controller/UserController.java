package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.UserRepository;
import service.SessionServiceImpl;
import service.UserServiceImpl;

import java.io.IOException;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "*")
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
            return new ResponseEntity(new SessionPOJO(session.getSessionToken()), HttpStatus.OK);
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
            return new ResponseEntity(new ErrorPOJO("Passowrd and repeatPassword must match."), HttpStatus.BAD_REQUEST);
        }
        RegisterState registerState = userService.registerUser(userRegisterPOJO.getUsername(), userRegisterPOJO.getPassword(), userRegisterPOJO.getEmail());
        switch (registerState) {
            case EMAIL_DUPLICATE:
                return new ResponseEntity(new ErrorPOJO("Email exists."), HttpStatus.BAD_REQUEST);
            case USERNAME_DUPLICATE:
                return new ResponseEntity(new ErrorPOJO("Username exists."), HttpStatus.BAD_REQUEST);
            case PASSOWRD_WEAK:
                return new ResponseEntity(new ErrorPOJO("Password weak."), HttpStatus.BAD_REQUEST);
            case REGISTERED:
                return new ResponseEntity(new ErrorPOJO("User registered."), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/user/info", method = RequestMethod.GET)
    public ResponseEntity getUserInfo(@RequestHeader(name="Authorization") String token) {
        User user = sessionService.getSessionByToken(token);
        if (user != null) {
            user.setPassword("");
            return new ResponseEntity(user, HttpStatus.OK);
        }
        return new ResponseEntity(new ErrorPOJO("TOKEN INVALID"), HttpStatus.UNAUTHORIZED);

    }

    @SuppressWarnings("Duplicates")
    @RequestMapping(value = "/user/updateUserInfo", method = RequestMethod.POST)
    public ResponseEntity updateUserInfo(@RequestHeader(name = "Authorization") String token, @RequestBody String json){
        /*
        userId, username, email, password, repeatPassword
        */
        User user = sessionService.getSessionByToken(token);
        if (user != null) {
            final ObjectMapper objectMapper = new ObjectMapper();
            UserRegisterPOJO userUpdateInfoPOJO = null;
            try {
                userUpdateInfoPOJO = objectMapper.readValue(json, UserRegisterPOJO.class);
            } catch (IOException e) {
                log.info(String.format("BAD_REQUEST for %s", json.replaceAll("\n", "")));
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }

            User foundUser = userService.getUserById(user.getUserId());
            if (null == foundUser) {
                return new ResponseEntity(new ErrorPOJO("Invalid user ID!"), HttpStatus.BAD_REQUEST);
            }

            if (null != userUpdateInfoPOJO.getPassword() && null != userUpdateInfoPOJO.getRepeatPassword()){
                if (!userUpdateInfoPOJO.getPassword().equals(userUpdateInfoPOJO.getRepeatPassword())) {
                    return new ResponseEntity(new ErrorPOJO("Password and repeatPassword must match."), HttpStatus.BAD_REQUEST);
                }
                else {
                    foundUser.setPassword(userUpdateInfoPOJO.getPassword());
                }
            }
            if (null != userUpdateInfoPOJO.getEmail()){
                foundUser.setEmail(userUpdateInfoPOJO.getEmail());
            }
            if (null != userUpdateInfoPOJO.getUsername()){
                foundUser.setUsername(userUpdateInfoPOJO.getUsername());
            }

            if (null != userService.updateUserInfo(user.getUserId(), foundUser))
            {
                return new ResponseEntity(new ErrorPOJO("USER INFORMATION UPDATED"), HttpStatus.OK);
            }

            return new ResponseEntity(new ErrorPOJO("FAILED TO UPDATE USER INFO"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(new ErrorPOJO("TOKEN INVALID"), HttpStatus.UNAUTHORIZED);
    }
}
