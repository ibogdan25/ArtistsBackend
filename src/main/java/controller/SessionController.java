package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.UserPOJO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Logger;

@RestController
public class SessionController {
    Logger log = Logger.getLogger(SessionController.class.getName());
    final String userNameMock = "ilie";
    final String passwordMock = "iliee";
    final String sessionTokenMock = "asdasda";

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
        return userPOJO.getUserOrEmail().equals(userNameMock) && userPOJO.getPassword().equals(passwordMock) ?
                new ResponseEntity("asdasda", HttpStatus.OK) : new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
}
