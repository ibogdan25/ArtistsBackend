package service;

import model.Session;
import model.SessionState;
import model.User;

public interface SessionService {
    Session createSession(User user);
    SessionState getSessionState(User user);
    User getSessionByToken(final String token);
}
