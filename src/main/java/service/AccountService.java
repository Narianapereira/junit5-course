package service;

import domain.Account;
import domain.exceptions.EventException;
import domain.exceptions.ValidationException;
import service.external.AccountEvent;
import service.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

public class AccountService {

    private AccountRepository accountRepository;
    private AccountEvent event;

    public AccountService(AccountRepository accountRepository, AccountEvent event) {
        this.accountRepository = accountRepository;
        this.event = event;
    }

    public Account save(Account account) {
        List<Account> accountList = accountRepository.findByUser(account.getUser().getId());
        boolean userExists = accountList.stream().anyMatch(acc -> acc.getName().equals(account.getName()));
        if (userExists) {
            throw new ValidationException("Account already exists for this user");
        }
        Account savedAccount = accountRepository.save(account);
        try{
            event.dispatch(savedAccount, AccountEvent.EventType.CREATED);
        } catch (Exception e) {
            accountRepository.delete(savedAccount);
            throw new EventException("Error while dispatching event");
        }
        return savedAccount;
    }

    public List<Account> findByUser(Long userId) {
        return accountRepository.findByUser(userId);
    }

    public void delete(Account account) {
        accountRepository.delete(account);
    }

}
