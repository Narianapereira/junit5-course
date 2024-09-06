package domain;

import domain.builders.AccountBuilder;
import domain.builders.UserBuilder;
import domain.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class AccountTest {

    @Test
    public void shouldCreateValidAccount() {
        Account account = AccountBuilder.oneAccount().readyToUse();
        Assertions.assertAll("Account",
                () -> Assertions.assertEquals(1L, account.getId()),
                () -> Assertions.assertEquals("Valid Account", account.getName()),
                () -> Assertions.assertEquals(UserBuilder.oneUser().readyToUse(), account.getUser())
        );
    }

    @ParameterizedTest
    @MethodSource(value = "dataProvider")
    public void shouldThrowInvalidAccount(Long id, String name, User user, String message) {
        String errorMessage = Assertions.assertThrows(ValidationException.class,
                () -> AccountBuilder.oneAccount().withId(id).withName(name).withUser(user).readyToUse()).getMessage();
        Assertions.assertEquals(message, errorMessage);
    }

    private static Stream<Arguments> dataProvider(){
        return Stream.of(
            Arguments.of(1L, null, UserBuilder.oneUser().readyToUse(), "Name cannot be null"),
                Arguments.of(1L, "Valid Account", null, "User cannot be null")
        );
    }
}
