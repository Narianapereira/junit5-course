package service;

import domain.Transaction;
import domain.builders.TransactionBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import service.repository.TransactionDAO;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @InjectMocks private TransactionService transactionService;
    @Mock private TransactionDAO transactionDAO;

    @Test
    public void shouldSaveValidTransaction() {
        Transaction toSave = TransactionBuilder.oneTransaction().withId(null).readyToUse();
        Mockito.when(transactionDAO.save(toSave))
                .thenReturn(TransactionBuilder.oneTransaction().readyToUse());

        Transaction saved = transactionService.save(toSave);
        Assertions.assertEquals(toSave, saved);
    }
}
