package service;

import domain.Account;
import domain.Transaction;
import domain.builders.AccountBuilder;
import domain.builders.TransactionBuilder;
import domain.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import service.external.ClockService;
import service.repository.TransactionDAO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @InjectMocks private TransactionService transactionService;
    @Mock private TransactionDAO transactionDAO;
    @Mock private ClockService clockService;
    @Captor private ArgumentCaptor<Transaction> captor;

    @BeforeEach
    public void setUp() {
        Mockito.when(clockService.getCurrentTime())
                .thenReturn(LocalDateTime.of(2024, 1, 1, 5, 0));
    }

    @Test
    public void shouldSaveValidTransaction() {
        Transaction toSave = TransactionBuilder.oneTransaction().withId(null).readyToUse();
        Mockito.when(transactionDAO.save(toSave))
                .thenReturn(TransactionBuilder.oneTransaction().readyToUse());

        Transaction saved = transactionService.save(toSave);
        Assertions.assertEquals(toSave, saved);

    }

    @ParameterizedTest(name = "{6}")
    @MethodSource(value = "mandatoryFields")
    public void shouldRejectInvalidTransaction(Long id, String description, Long amount,
                                               LocalDate date, Account account, Boolean status, String message) {
        String error = Assertions.assertThrows(ValidationException.class, () -> {
                Transaction toSave = TransactionBuilder.oneTransaction().withId(id).withDescription(description)
                .withAmount(amount).withStatus(status)
                .withDate(date)
                .withAccount(account).readyToUse();
                transactionService.save(toSave);
        }).getMessage();
        Assertions.assertEquals(message, error);

    }

    @Test
    public void shouldRejectLateNightTransaction() {
        Mockito.when(clockService.getCurrentTime())
                .thenReturn(LocalDateTime.of(2024, 1, 1, 23, 0));
        String error = Assertions.assertThrows(RuntimeException.class, () -> {
            Transaction toSave = TransactionBuilder.oneTransaction().readyToUse();
            transactionService.save(toSave);
        }).getMessage();
        Assertions.assertEquals("Try again tomorrow", error);

    }

    @Test
    public void shouldSetFalseOnNullStatus() {
        Transaction toSave = TransactionBuilder.oneTransaction().withStatus(null).readyToUse();
        transactionService.save(toSave);

        Mockito.verify(transactionDAO).save(captor.capture());
        Transaction captured = captor.getValue();
        Assertions.assertFalse(captured.getStatus());

    }


    public static Stream<Arguments> mandatoryFields() {
        return Stream.of(
                Arguments.of(1L, null, 500L, LocalDate.now(), AccountBuilder.oneAccount().readyToUse(), true, "Transaction description is required"),
                Arguments.of(1L, "Transaction Description", 500L, null, AccountBuilder.oneAccount().readyToUse(), true, "Transaction date is required"),
                Arguments.of(1L, "Transaction Description", null, LocalDate.now(), AccountBuilder.oneAccount().readyToUse(), true, "Transaction amount is required"),
                Arguments.of(1L, "Transaction Description", 500L, LocalDate.now(), null, true, "Transaction account is required")
        );
    }
}
