package meetona.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class LogoutException extends RuntimeException {

    private final String user;
    private final String message;

    public LogoutException(String user, String message) {
        super(String.format("Couldn't log out device [%s]: [%s])", user, message));
        this.user = user;
        this.message = message;
    }
}