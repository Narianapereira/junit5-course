package service.repository;

import domain.Transaction;

public interface TransactionDAO {

    Transaction save(Transaction transaction);
}
