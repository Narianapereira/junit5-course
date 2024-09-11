package service;

import domain.User;
import domain.builders.UserBuilder;
import domain.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import service.repository.UserRepository;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock private UserRepository repository;
    @InjectMocks private UserService userService;

    @Test
    public void shouldReturnEmptyIfUserDoesNotExists(){
        Mockito.when(repository.getUserByMail("mail@email.com"))
                .thenReturn(Optional.empty());
        Optional<User> user = userService.getUserByEmail("mail@email.com");
        Assertions.assertTrue(user.isEmpty());
    }

    @Test
    public void shouldReturnUserByEmail(){
        Mockito.when(repository.getUserByMail("mail@email.com"))
                .thenReturn(Optional.of(UserBuilder.oneUser().readyToUse()));
        Optional<User> user = userService.getUserByEmail("mail@email.com");
        Assertions.assertTrue(user.isPresent());
    }

    @Test
    public void shouldSaveUserSuccessfully(){
        User userToSave = UserBuilder.oneUser().withId(null).readyToUse();
        Mockito.when(repository.getUserByMail(userToSave.getEmail()))
                .thenReturn(Optional.empty());
        Mockito.when(repository.save(userToSave))
                        .thenReturn(UserBuilder.oneUser().readyToUse());

        User savedUser = userService.save(userToSave);
        Assertions.assertNotNull(savedUser.getId());
        Mockito.verify(repository).getUserByMail(userToSave.getEmail());
    }

    @Test
    public void shuldRejectExistentUser(){
     User userToSave = UserBuilder.oneUser().withId(null).readyToUse();

     Mockito.when(repository.getUserByMail(userToSave.getEmail()))
             .thenReturn(Optional.of(UserBuilder.oneUser().readyToUse()));

     ValidationException exception = Assertions.assertThrows(ValidationException.class,
             () -> userService.save(userToSave));
     Assertions.assertTrue(exception.getMessage().contains("already exists"));
     Mockito.verify(repository, Mockito.never()).save(userToSave);
    }
}
