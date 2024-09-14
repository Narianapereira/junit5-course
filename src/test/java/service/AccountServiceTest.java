package service;

import domain.Account;
import domain.builders.AccountBuilder;
import domain.exceptions.EventException;
import domain.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import service.external.AccountEvent;
import service.repository.AccountRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;
    @Mock
    private AccountEvent event;
    @InjectMocks
    AccountService accountService;

    @Test
    public void shouldReturnEmptyIfUserAccountDoesNotExist() {
        Mockito.when(accountRepository.findByUser(1L)).thenReturn(new ArrayList<>());
        List<Account> accountList = accountService.findByUser(1L);
        Assertions.assertTrue(accountList.isEmpty());
    }

    @Test
    public void shouldReturnAccountListByUser() {
        Mockito.when(accountRepository.findByUser(1L)).
                thenReturn(Arrays.asList(AccountBuilder.oneAccount().readyToUse(),
                        AccountBuilder.oneAccount().readyToUse()));

        List<Account> account = accountService.findByUser(1L);
        Assertions.assertFalse(account.isEmpty());
        Mockito.verify(accountRepository).findByUser(1L);
    }

    @Test
    public void shouldSaveAccount() {
        Account accountToSave = AccountBuilder.oneAccount().readyToUse();
        Mockito.when(accountService.findByUser(1L)).thenReturn(new ArrayList<>());
        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(AccountBuilder.oneAccount().readyToUse());
        Mockito.doNothing().when(event).dispatch(AccountBuilder.oneAccount().readyToUse(),
                    AccountEvent.EventType.CREATED);
        Account savedAccount = accountService.save(accountToSave);

        Assertions.assertNotNull(savedAccount);
        Mockito.verify(accountRepository).findByUser(1L);
    }

    @Test
    public void shouldSaveWhenUserAlreadyHasAccounts(){
        Account accountToSave = AccountBuilder.oneAccount().withName("Account 2").readyToUse();
        Mockito.when(accountRepository.findByUser(1L)).
                thenReturn(Arrays.asList(AccountBuilder.oneAccount().readyToUse()));
        Mockito.when(accountRepository.save(accountToSave)).thenReturn(AccountBuilder.oneAccount().readyToUse());
        Mockito.doNothing().when(event).dispatch(AccountBuilder.oneAccount().readyToUse(),
                AccountEvent.EventType.CREATED);

        Account savedAccount = accountService.save(accountToSave);
        Assertions.assertNotNull(savedAccount);
        Mockito.verify(accountRepository).findByUser(1L);
    }

    @Test
    public void shouldRejectExistentAccount() {
        Account accountToSave = AccountBuilder.oneAccount().readyToUse();
        Mockito.when(accountRepository.findByUser(1L)).
                thenReturn(Arrays.asList(AccountBuilder.oneAccount().readyToUse(),
                        AccountBuilder.oneAccount().readyToUse()));

        ValidationException exception = Assertions.assertThrows(ValidationException.class,
                ()-> accountService.save(accountToSave));
        Assertions.assertTrue(exception.getMessage().contains("already exists"));
        Mockito.verify(accountRepository, Mockito.never()).save(accountToSave);
    }

    @Test
    public void shouldThrowIfEventFails() {
        Account accountToSave = AccountBuilder.oneAccount().readyToUse();
        Mockito.when(accountRepository.findByUser(1L)).thenReturn(new ArrayList<>());
        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(AccountBuilder.oneAccount().readyToUse());
        Mockito.doThrow(new RuntimeException("Error"))
                .when(event).dispatch(AccountBuilder.oneAccount().readyToUse(),
                AccountEvent.EventType.CREATED);

        EventException exception = Assertions.assertThrows(EventException.class,
                () -> accountService.save(accountToSave));
        Assertions.assertEquals("Error while dispatching event", exception.getMessage());
        Mockito.verify(accountRepository).delete(accountToSave);
    }
}
