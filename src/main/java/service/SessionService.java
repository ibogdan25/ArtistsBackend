package service;

import model.Session;

public interface SessionService {
    Session createSession(String username, String password);
}
