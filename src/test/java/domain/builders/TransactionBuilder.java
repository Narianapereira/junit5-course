package domain.builders;

import domain.Account;
import java.lang.Long;
import java.util.Arrays;
import java.time.LocalDate;
import java.lang.Boolean;
import java.lang.String;
import domain.Transaction;


import domain.Account;
import java.lang.Long;
import java.util.Arrays;
import java.time.LocalDate;
import java.lang.Boolean;
import java.lang.String;
import domain.Transaction;


public class TransactionBuilder {
    private Transaction element;
    private TransactionBuilder(){}

    public static TransactionBuilder oneTransaction() {
        TransactionBuilder builder = new TransactionBuilder();
        loadDefaultData(builder);
        return builder;
    }

    public static void loadDefaultData(TransactionBuilder builder) {
        builder.element = new Transaction();
        Transaction element = builder.element;


        element.setId(1L);
        element.setDescription("Transaction Description");
        element.setAmount(500L);
        element.setAccount(AccountBuilder.oneAccount().readyToUse());
        element.setDate(LocalDate.now());
        element.setStatus(false);
    }

    public TransactionBuilder withId(Long param) {
        element.setId(param);
        return this;
    }

    public TransactionBuilder withDescription(String param) {
        element.setDescription(param);
        return this;
    }

    public TransactionBuilder withAmount(Long param) {
        element.setAmount(param);
        return this;
    }

    public TransactionBuilder withAccount(Account param) {
        element.setAccount(param);
        return this;
    }

    public TransactionBuilder withDate(LocalDate param) {
        element.setDate(param);
        return this;
    }

    public TransactionBuilder withStatus(Boolean param) {
        element.setStatus(param);
        return this;
    }

    public Transaction readyToUse() {
        return element;
    }

}
