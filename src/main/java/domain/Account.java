package domain;

import domain.exceptions.ValidationException;

public class Account {

    private Long id;
    private String name;
    private User user;

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
