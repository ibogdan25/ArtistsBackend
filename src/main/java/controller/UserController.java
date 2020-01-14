package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.FollowArtistEventServiceImpl;
import service.SessionServiceImpl;
import service.UserServiceImpl;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private SessionServiceImpl sessionService;
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private FollowArtistEventServiceImpl followArtistEventService;


    Logger log = Logger.getLogger(UserController.class.getName());


    @RequestMapping(value = "/followsArtist",method = RequestMethod.GET)
    @ResponseBody
    public boolean followArtist(@RequestHeader(name = "Authorization")String token,@RequestParam("artistId")String artistId){
        User user = sessionService.getSessionByToken(token);
        if(user == null)
            return false;

        return followArtistEventService.followsArtist(user,artistId);

    }

    @RequestMapping(value="/followArtistUser",method = RequestMethod.POST)
    public ResponseEntity<?> addFollowArtistUser(@RequestHeader(name = "Authorization") String token, @RequestParam("artistId") String artistId) {
        User user = sessionService.getSessionByToken(token);
        if (user == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        if(!followArtistEventService.addFollowArtistUser(user.getUserId().toString(), artistId))
            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @RequestMapping(value="/unfollowArtistUser",method = RequestMethod.DELETE)
    public ResponseEntity<?> unfollowArtistUser(@RequestHeader(name = "Authorization") String token, @RequestParam("artistId") String artistId){
        User user = sessionService.getSessionByToken(token);
        if(user == null)
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);

        if(!followArtistEventService.unfollowArtistUser(user.getUserId().toString(),artistId))
            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @RequestMapping(value="/followEventUser",method = RequestMethod.POST)
    public ResponseEntity<?> addFollowEventUser(@RequestHeader(name = "Authorization") String token, @RequestParam("eventId") String eventId) {
        User user = sessionService.getSessionByToken(token);
        if (user == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        if(!followArtistEventService.addFollowEventUser(user.getUserId().toString(), eventId))
            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @RequestMapping(value="/followArtistUser/allFollowedArtists",method = RequestMethod.GET)
    public List<Artist> getAllFollowedArtists(@RequestHeader(name= "Authorization") String token){
        User user = sessionService.getSessionByToken(token);
        if(user == null){
            return null;
        }

        return followArtistEventService.getAllFollowedArtists(user);
    }

    @RequestMapping(value="/followsEvent",method = RequestMethod.GET)
    @ResponseBody
    public boolean followsEvent(@RequestHeader(name="Authorization") String token,@RequestParam("eventId") String eventId){
        User user = sessionService.getSessionByToken(token);
        if(user == null){
            return false;
        }

        return followArtistEventService.followsEvent(user,eventId);
    }

    @RequestMapping(value="/followEventUser/allFollowedEvents",method = RequestMethod.GET)
    public List<Event> getAllFollowedEvents(@RequestHeader(name="Authorization") String token){
        User user = sessionService.getSessionByToken(token);
        if(user == null){
            return null;
        }


        return followArtistEventService.getAllFollowedEvents(user);
    }

    @RequestMapping(value="/unfollowEventUser",method=RequestMethod.DELETE)
    public ResponseEntity<?> unfollowEventUser(@RequestHeader(name="Authorization")String token, @RequestParam("eventId") String eventId){
        User user = sessionService.getSessionByToken(token);
        if(user == null)
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);

        if(!followArtistEventService.unfollowEventUser(user.getUserId().toString(),eventId))
            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);

        return new ResponseEntity<>(HttpStatus.OK);
    }


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
                return new ResponseEntity(foundUser, HttpStatus.OK);
            }

            return new ResponseEntity(new ErrorPOJO("FAILED TO UPDATE USER INFO"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(new ErrorPOJO("TOKEN INVALID"), HttpStatus.UNAUTHORIZED);
    }

}
