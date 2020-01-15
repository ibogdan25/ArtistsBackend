package service;

import jdk.nashorn.internal.runtime.options.Option;
import model.PasswordPOJO;
import model.RegisterState;
import model.ResetPasswordResponse;
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

    public User updateUserInfo(final Long userId, final User newUser){
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()){
            userRepository.save(newUser);
            return newUser;
        }

        return null;
    }

    public User getUserById(final Long userId)
    {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()){
            return user.get();
        }
        return null;
    }

    public ResetPasswordResponse resetPassword(final String token, final PasswordPOJO passwordPOJO) {
        if(!passwordPOJO.getPassword().equals(passwordPOJO.getRepeatPassword())) {
            return ResetPasswordResponse.PASSWORD_DOESNT_MATCH;
        }
        final Optional<User> userOptional = userRepository.findFirstByRecoverPassowrdCode(token);
        if (token.trim().isEmpty() || !userOptional.isPresent()) {
            return ResetPasswordResponse.TOKEN_INVALID;
        }
        final User user = userOptional.get();
        user.setPassword(passwordPOJO.getPassword());
        user.setRecoverPassowrdCode(null);

        userRepository.save(user);
        return ResetPasswordResponse.OK;
    }
}
