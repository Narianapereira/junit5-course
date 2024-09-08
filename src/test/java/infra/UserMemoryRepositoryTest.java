package infra;

import domain.User;
import domain.builders.UserBuilder;
import domain.exceptions.ValidationException;
import org.junit.jupiter.api.*;
import service.UserService;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserMemoryRepositoryTest {
    private static UserService userService = new UserService(new UserMemoryRepository());

    @Test
    @Order(1)
    public void shouldSaveValidUser() {
        User user = userService.save(UserBuilder.oneUser().withId(null).readyToUse());
        Assertions.assertNotNull(user.getId());
    }

    @Test
    @Order(2)
    public void shouldThrowExistingUser(){
        ValidationException exception = Assertions.assertThrows(ValidationException.class,
                () -> userService.save(UserBuilder.oneUser().withId(null).readyToUse()));
        Assertions.assertEquals("User user@gmail.com already exists", exception.getMessage());
    }
}
