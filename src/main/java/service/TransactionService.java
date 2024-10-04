package service;

import domain.Transaction;
import domain.exceptions.ValidationException;
import service.external.ClockService;
import service.repository.TransactionDAO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TransactionService {

    private TransactionDAO dao;
    private ClockService clockService;

    public Transaction save(Transaction transaction) {
        if(clockService.getCurrentTime().getHour() > 22) throw new RuntimeException("Try again tomorrow");

        validarTransactionFields(transaction);

        return dao.save(transaction);
    }

    private void validarTransactionFields(Transaction transaction) {
        if(transaction.getDescription() == null) throw new ValidationException("Transaction description is required");
        if(transaction.getAmount() == null) throw new ValidationException("Transaction amount is required");
        if(transaction.getDate() == null) throw new ValidationException("Transaction date is required");
        if(transaction.getAccount() == null) throw new ValidationException("Transaction account is required");
        if(transaction.getStatus() == null) transaction.setStatus(false);
    }
}
