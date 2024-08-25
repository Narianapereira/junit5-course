package domain.builders;

import domain.User;

public class UserBuilder {
    private Long id;
    private String username;
    private String email;
    private String password;

    private UserBuilder(){}

    public static UserBuilder oneUser() {
        UserBuilder userBuilder = new UserBuilder();
        loadDefaultData(userBuilder);
        return userBuilder;
    }

    private static void loadDefaultData(UserBuilder userBuilder) {
        userBuilder.id = 1L;
        userBuilder.username = "Valid User";
        userBuilder.email = "user@gmail.com";
        userBuilder.password = "123456";
    }

    public UserBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public User readyToUse() {
        return new User(id, username, email, password);
    }
}
