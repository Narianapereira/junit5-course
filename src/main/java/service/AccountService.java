package service;

import domain.Account;
import domain.exceptions.ValidationException;
import service.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account save(Account account) {
        List<Account> accountList = accountRepository.findByUser(account.getUser().getId());
        boolean userExists = accountList.stream().anyMatch(acc -> acc.getName().equals(account.getName()));
        if (userExists) {
            throw new ValidationException("Account already exists for this user");
        }
        return accountRepository.save(account);
    }

    public List<Account> findByUser(Long userId) {
        return accountRepository.findByUser(userId);
    }
}
