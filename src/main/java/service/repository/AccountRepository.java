package service.repository;

import domain.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {

    Account save(Account account);

    List<Account> findByUser(Long userId);

}
