package service;

import model.Session;
import model.SessionState;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.SessionRepository;
import repository.UserRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

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
    public Session createSession(final User user) {
        final Optional<Session> optionalSession = sessionRepository.findFirstByUser(user);
        final LocalDateTime dateNow = LocalDateTime.now();
        dateNow.plusDays(1);
        final Date expiredDate = Date.from(dateNow.atZone(ZoneId.systemDefault()).toInstant());
        final String token = String.format("%s%s%s",UUID.randomUUID().toString().toUpperCase(), user.getUsername(), dateNow.toString());
        optionalSession.ifPresent(session -> {
            session.setExpireTime(expiredDate);
            session.setSessionToken(token);
            sessionRepository.save(session);
        });
        if (!optionalSession.isPresent()) {
            Session newSession = new Session();
            newSession.setUser(user);
            newSession.setSessionToken(token);
            newSession.setExpireTime(expiredDate);

            sessionRepository.save(newSession);
            return newSession;
        }
        return optionalSession.get();
    }

    @Override
    public SessionState getSessionState(User user) {
        final Optional<Session> optionalSession = sessionRepository.findFirstByUser(user);
        if (!optionalSession.isPresent()) {
            return SessionState.NOT_CREATED;
        }
        Session session = optionalSession.get();
        final Date dateNow = Date.from(Instant.now());
        if (session.getExpireTime().after(dateNow)) {
            return SessionState.AVAILABLE;
        }
        return SessionState.EXPIRED;
    }
}
