package meetona.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class InsertionFailedException extends RuntimeException {

    public InsertionFailedException(String user, String message) {
        super(user + message);
    }
}