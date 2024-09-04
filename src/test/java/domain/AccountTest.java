package domain;

import domain.builders.AccountBuilder;
import domain.builders.UserBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
}
