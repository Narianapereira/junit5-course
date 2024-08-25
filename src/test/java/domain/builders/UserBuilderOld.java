package domain.builders;

import domain.User;

public class UserBuilderOld {

    private Long id;
    private String username;
    private String email;
    private String password;

    public static UserBuilderOld oneUser() {
        UserBuilderOld userBuilder = new UserBuilderOld();
        loadDefaultData(userBuilder);
        return userBuilder;
    }

    private static void loadDefaultData(UserBuilderOld userBuilder) {
        userBuilder.id = 1L;
        userBuilder.username = "Valid User";
        userBuilder.email = "user@gmail.com";
        userBuilder.password = "123456";
    }

    public UserBuilderOld withId(Long idParam) {
        id = idParam;
        return this;
    }

    public UserBuilderOld withEmail(String emailParam) {
        email = emailParam;
        return this;
    }

    public UserBuilderOld withPassword(String passwordParam) {
        password = passwordParam;
        return this;
    }

    public UserBuilderOld withUsername(String usernameParam) {
        username = usernameParam;
        return this;
    }

    public User readyToUse() {
        return new User(id, username, email, password);
    }
}


