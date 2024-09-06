package domain;

import domain.exceptions.ValidationException;

import java.util.Objects;

public class User {
    private Long id;
    private String username;
    private String email;
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, password);
    }

    public User(Long id, String username, String email, String password) {
        if(username == null || username.isEmpty()) throw new ValidationException("Username cannot be null or empty");
        if(email == null || email.isEmpty()) throw new ValidationException("Email cannot be null or empty");
        if(password == null || password.isEmpty()) throw new ValidationException("Password cannot be null or empty");

        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
