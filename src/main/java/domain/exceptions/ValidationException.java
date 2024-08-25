package domain.exceptions;

import java.io.Serial;

public class ValidationException extends RuntimeException {


    public ValidationException(String message) {
        super(message);
    }
}
