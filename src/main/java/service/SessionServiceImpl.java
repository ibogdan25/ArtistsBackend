package service;

import model.Session;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService{
    @Override
    public Session createSession(String username, String password) {
        //TODO persist into session repo
        return null;
    }
}
