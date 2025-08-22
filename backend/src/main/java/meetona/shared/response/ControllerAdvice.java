package meetona.shared.response;

import lombok.extern.slf4j.Slf4j;
import meetona.shared.exception.*;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    private final MessageSource messageSource;

    public ControllerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ServiceResponse<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new ServiceResponse<>(ex.getMessage(), false));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ServiceResponse<String>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(new ServiceResponse<>(ex.getMessage(), false));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ServiceResponse<String> processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        return new ServiceResponse<>(String.join("\n", processAllErrors(result.getAllErrors())), false);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    /**
     * Utility Method to generate localized message for a list of field errors
     *
     * @param allErrors the field errors
     * @return the list
     */
    private List<String> processAllErrors(List<ObjectError> allErrors) {
        return allErrors.stream().map(this::resolveLocalizedErrorMessage).collect(Collectors.toList());
    }

    /**
     * Resolve localized error message. Utility method to generate a localized error
     * message
     *
     * @param objectError the field error
     * @return the string
     */
    private String resolveLocalizedErrorMessage(ObjectError objectError) {
        String localizedErrorMessage = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
        log.info(localizedErrorMessage);
        return localizedErrorMessage;
    }

    @ResponseBody
    @ExceptionHandler(AppException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ServiceResponse<String> handleAppException(AppException ex) {
        return new ServiceResponse<>(ex.getMessage(), false);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.IM_USED)
    @ExceptionHandler(ResourceAlreadyInUseException.class)
    public ServiceResponse<String> handleResourceAlreadyInUseException(ResourceAlreadyInUseException ex) {
        return new ServiceResponse<>(ex.getMessage(), false);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ServiceResponse<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ServiceResponse<>(ex.getMessage(), false);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ServiceResponse<String> handleBadRequestException(BadRequestException ex) {
        return new ServiceResponse<>(ex.getMessage(), false);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ServiceResponse<String> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return new ServiceResponse<>(ex.getMessage(), false);
    }

    @ResponseBody
    @ExceptionHandler(LoginException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ServiceResponse<String> handleUserLoginException(LoginException ex) {
        return new ServiceResponse<>(ex.getMessage(), false);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(BadCredentialsException.class)
    public ServiceResponse<String> handleBadCredentialsException(BadCredentialsException ex) {
        return new ServiceResponse<>(ex.getMessage(), false);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(InsertionFailedException.class)
    public ServiceResponse<String> handleUserRegistrationException(InsertionFailedException ex) {
        return new ServiceResponse<>(ex.getMessage(), false);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(PasswordResetLinkException.class)
    public ServiceResponse<String> handlePasswordResetLinkException(PasswordResetLinkException ex) {
        return new ServiceResponse<>(ex.getMessage(), false);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(PasswordResetException.class)
    public ServiceResponse<String> handlePasswordResetException(PasswordResetException ex) {
        return new ServiceResponse<>(ex.getMessage(), false);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(InvalidTokenRequestException.class)
    public ServiceResponse<String> handleInvalidTokenException(InvalidTokenRequestException ex) {
        return new ServiceResponse<>(ex.getMessage(), false);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(UpdatePasswordException.class)
    public ServiceResponse<String> handleUpdatePasswordException(UpdatePasswordException ex) {
        return new ServiceResponse<>(ex.getMessage(), false);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(TokenRefreshException.class)
    public ServiceResponse<String> handleTokenRefreshException(TokenRefreshException ex) {
        return new ServiceResponse<>(ex.getMessage(), false);
    }

    @ResponseBody
    @ExceptionHandler(LogoutException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ServiceResponse<String> handleUserLogoutException(LogoutException ex) {
        return new ServiceResponse<>(ex.getMessage(), false);
    }
}
