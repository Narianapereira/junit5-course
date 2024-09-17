package service;

import domain.Transaction;
import domain.exceptions.ValidationException;
import service.repository.TransactionDAO;

public class TransactionService {

    private TransactionDAO dao;

    public Transaction save(Transaction transaction) {
        if(transaction.getDescription() == null) throw new ValidationException("Transaction description is required");
        if(transaction.getAmount() == null) throw new ValidationException("Transaction amount is required");
        if(transaction.getDate() == null) throw new ValidationException("Transaction date is required");
        if(transaction.getAccount() == null) throw new ValidationException("Transaction account is required");
        if(transaction.getStatus() == null) transaction.setStatus(false);

        return dao.save(transaction);
    }
}
