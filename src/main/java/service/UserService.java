package service;

import domain.User;
import domain.exceptions.ValidationException;
import service.repository.UserRepository;

import java.util.Optional;

public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        userRepository.getUserByMail(user.getEmail()).ifPresent(u -> {
        throw new ValidationException(String.format("User %s already exists", user.getEmail()));
        });
        return userRepository.save(user);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.getUserByMail(email);
    }
}
