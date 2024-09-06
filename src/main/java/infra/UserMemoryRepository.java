package infra;

import domain.User;
import service.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserMemoryRepository implements UserRepository {
    private static List<User> users;
    private Long currentId;

    public UserMemoryRepository() {
        currentId = 0L;
        users = new ArrayList<>();
        save(new User(null, "User 1", "user1@email.com", "123445"));
    }

    @Override
    public User save(User user) {
        User newUser = new User(nextId(), user.getUsername(), user.getEmail(), user.getPassword());
        users.add(newUser);
        return newUser;
    }

    @Override
    public Optional<User> getUserByMail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    private Long nextId(){
        return ++currentId;
    }

    private static void printUsers() {
        System.out.println(users);
    }

    public static void main(String[] args) {
        UserMemoryRepository repo = new UserMemoryRepository();
        printUsers();
        repo.save(new User(null, null, "user2@email.com", "123456"));
        printUsers();
    }


}
