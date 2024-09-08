package infra;

import domain.User;
import domain.builders.UserBuilder;
import service.repository.UserRepository;

import java.util.Optional;

public class UserDummyRepository implements UserRepository {


    @Override
    public User save(User user) {
        return UserBuilder.oneUser()
                .withUsername(user.getUsername())
                .withEmail(user.getEmail())
                .withPassword(user.getPassword())
                .readyToUse();
    }

    @Override
    public Optional<User> getUserByMail(String email) {
        if("user1@email.com".equals(email))
            return Optional.of(UserBuilder.oneUser().withEmail(email).readyToUse());
        return Optional.empty();
    }
}
