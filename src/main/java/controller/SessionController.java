package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import service.SessionServiceImpl;
import service.UserServiceImpl;
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
