package domain.builders;
import domain.User;
import domain.Account;

public class AccountBuilder {
    private Long id;
    private String name;
    private User user;

    private AccountBuilder(){}

    public static AccountBuilder oneAccount() {
        AccountBuilder builder = new AccountBuilder();
        loadDefaultData(builder);
        return builder;
    }

    private static void loadDefaultData(AccountBuilder builder) {
        builder.id = 1L;
        builder.name = "Valid Account";
        builder.user = UserBuilder.oneUser().readyToUse();
    }

    public AccountBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public AccountBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public AccountBuilder withUser(User user) {
        this.user = user;
        return this;
    }

    public Account readyToUse() {
        return new Account(id, name, user);
    }
}
