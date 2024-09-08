package service;

import domain.User;
import domain.builders.UserBuilder;
import infra.UserDummyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserServiceTest {

    private UserService userService;

    @Test
    public void shouldSaveUser(){
        userService = new UserService(new UserDummyRepository());
        User user = UserBuilder.oneUser().withId(null).readyToUse();
        User savedUser = userService.save(user);
        Assertions.assertNotNull(savedUser.getId());
    }
}
