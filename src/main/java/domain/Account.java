package domain;

import domain.exceptions.ValidationException;

import java.util.Objects;

public class Account {

    private Long id;
    private String name;
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(name, account.name) && Objects.equals(user, account.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, user);
    }

    public Account(Long id, String name, User user) {
        if(name == null) throw new ValidationException("Name cannot be null");
        if(user == null) throw new ValidationException("User cannot be null");

        this.id = id;
        this.name = name;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getUser() {
        return user;
    }
}
