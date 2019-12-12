package service;

import model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.SessionRepository;
import repository.UserRepository;

@Service
public class SessionServiceImpl implements SessionService{
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository, UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }


    @Override
    public Session createSession(String username, String password) {
        final Session session = new Session();
        return  session;
    }
}
