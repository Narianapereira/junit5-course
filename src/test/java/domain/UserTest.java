package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import domain.builders.UserBuilder;
import domain.builders.UserBuilderOld;
import domain.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Domain: User")
class UserTest {

    @Test
    @DisplayName("Should create a valid user")
    public void shouldCreateValidUser() {
        User user = UserBuilder.oneUser().readyToUse();
        Assertions.assertAll("User",
                () -> assertEquals(1l, user.getId()),
                () -> assertEquals("Valid User", user.getUsername()),
                () -> assertEquals("user@gmail.com", user.getEmail()),
                () -> assertEquals("123456", user.getPassword())
        );
    }

    @Test
    @DisplayName("Should throw user without name")
    public void shouldThrowUserWithoutName() {
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () ->
                UserBuilder.oneUser().withUsername(null).readyToUse()
        );
        assertEquals("Username cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw user without email")
    public void shouldThrowUserWithoutEmail() {
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () ->
                UserBuilder.oneUser().withEmail(null).readyToUse()
        );
        assertEquals("Email cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw user without password")
    public void shouldThrowUserWithoutPassword() {
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () ->
                UserBuilder.oneUser().withPassword(null).readyToUse()
        );
        assertEquals("Password cannot be null or empty", exception.getMessage());
    }

}