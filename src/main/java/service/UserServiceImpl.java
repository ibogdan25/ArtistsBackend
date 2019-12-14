package service;

import model.RegisterState;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UserRepository;

import java.util.Optional;

@Service
public class UserServiceImpl {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public RegisterState registerUser(final String userName, final String password, final String email) {
        if (userRepository.findFirstByUsername(userName).isPresent()) {
            return RegisterState.USERNAME_DUPLICATE;
        }
        if(userRepository.findFirstByEmail(email).isPresent()) {
            return RegisterState.EMAIL_DUPLICATE;
        }
        User user = new User();
        user.setUsername(userName);
        user.setPassword(password);
        user.setEmail(email);

        userRepository.save(user);
        return RegisterState.REGISTERED;
    }

    public User getUser(final String userNameOrEmail, final String passowrd) {
        Optional<User> user = userRepository.findFirstByUsernameAndPassword(userNameOrEmail, passowrd);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }
}