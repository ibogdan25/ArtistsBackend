package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.UserPOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.SessionServiceImpl;
import service.UserServiceImpl;

import java.io.IOException;
import java.util.logging.Logger;

@RestController
public class SessionController {
    private SessionServiceImpl sessionService;
    @Autowired
    private UserServiceImpl userService;

    Logger log = Logger.getLogger(SessionController.class.getName());
    final String userNameMock = "ilie";
    final String passwordMock = "iliee";
    final String sessionTokenMock = "asdasda";


}
