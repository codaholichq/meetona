package meetona.web.advice;

import meetona.core.Dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import meetona.core.exception.*;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
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
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class AuthControllerAdvice {

    private final MessageSource messageSource;

    public AuthControllerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse<String> processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<ObjectError> allErrors = result.getAllErrors();
        var response = new ApiResponse<String>();
        response.setSuccess(false);
        response.setData(String.join("\n", processAllErrors(allErrors)));
        return response;
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
        Locale currentLocale = LocaleContextHolder.getLocale();
        String localizedErrorMessage = messageSource.getMessage(objectError, currentLocale);
        log.info(localizedErrorMessage);
        return localizedErrorMessage;
    }

    @ExceptionHandler(value = AppException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiResponse<String> handleAppException(AppException ex) {
        var apiResponse = new ApiResponse<String>();
        apiResponse.setSuccess(false);
        apiResponse.setData(ex.getMessage());
        return apiResponse;
    }

    @ExceptionHandler(value = ResourceAlreadyInUseException.class)
    @ResponseStatus(HttpStatus.IM_USED)
    @ResponseBody
    public ApiResponse<String> handleResourceAlreadyInUseException(ResourceAlreadyInUseException ex) {
        var apiResponse = new ApiResponse<String>();
        apiResponse.setSuccess(false);
        apiResponse.setData(ex.getMessage());
        return apiResponse;
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiResponse<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        var apiResponse = new ApiResponse<String>();
        apiResponse.setSuccess(false);
        apiResponse.setData(ex.getMessage());
        return apiResponse;
    }

    @ExceptionHandler(value = BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse<String> handleBadRequestException(BadRequestException ex) {
        var apiResponse = new ApiResponse<String>();
        apiResponse.setSuccess(false);
        apiResponse.setData(ex.getMessage());
        return apiResponse;
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiResponse<String> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        var apiResponse = new ApiResponse<String>();
        apiResponse.setSuccess(false);
        apiResponse.setData(ex.getMessage());
        return apiResponse;
    }

    @ExceptionHandler(value = LoginException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    public ApiResponse<String> handleUserLoginException(LoginException ex) {
        var apiResponse = new ApiResponse<String>();
        apiResponse.setSuccess(false);
        apiResponse.setData(ex.getMessage());
        return apiResponse;
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    public ApiResponse<String> handleBadCredentialsException(BadCredentialsException ex) {
        var apiResponse = new ApiResponse<String>();
        apiResponse.setSuccess(false);
        apiResponse.setData(ex.getMessage());
        return apiResponse;
    }

    @ExceptionHandler(value = SignupException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    public ApiResponse<String> handleUserRegistrationException(SignupException ex) {
        var apiResponse = new ApiResponse<String>();
        apiResponse.setSuccess(false);
        apiResponse.setData(ex.getMessage());
        return apiResponse;
    }

    @ExceptionHandler(value = PasswordResetLinkException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    public ApiResponse<String> handlePasswordResetLinkException(PasswordResetLinkException ex) {
        var apiResponse = new ApiResponse<String>();
        apiResponse.setSuccess(false);
        apiResponse.setData(ex.getMessage());
        return apiResponse;
    }

    @ExceptionHandler(value = PasswordResetException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    public ApiResponse<String> handlePasswordResetException(PasswordResetException ex) {
        var apiResponse = new ApiResponse<String>();
        apiResponse.setSuccess(false);
        apiResponse.setData(ex.getMessage());
        return apiResponse;
    }

    @ExceptionHandler(value = MailSendException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public ApiResponse<String> handleMailSendException(MailSendException ex) {
        var apiResponse = new ApiResponse<String>();
        apiResponse.setSuccess(false);
        apiResponse.setData(ex.getMessage());
        return apiResponse;
    }

    @ExceptionHandler(value = InvalidTokenRequestException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public ApiResponse<String> handleInvalidTokenException(InvalidTokenRequestException ex) {
        var apiResponse = new ApiResponse<String>();
        apiResponse.setSuccess(false);
        apiResponse.setData(ex.getMessage());
        return apiResponse;
    }

    @ExceptionHandler(value = UpdatePasswordException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    public ApiResponse<String> handleUpdatePasswordException(UpdatePasswordException ex) {
        var apiResponse = new ApiResponse<String>();
        apiResponse.setSuccess(false);
        apiResponse.setData(ex.getMessage());
        return apiResponse;
    }


    @ExceptionHandler(value = TokenRefreshException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    public ApiResponse<String> handleTokenRefreshException(TokenRefreshException ex) {
        var apiResponse = new ApiResponse<String>();
        apiResponse.setSuccess(false);
        apiResponse.setData(ex.getMessage());
        return apiResponse;
    }

    @ExceptionHandler(value = LogoutException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    public ApiResponse<String> handleUserLogoutException(LogoutException ex) {
        var apiResponse = new ApiResponse<String>();
        apiResponse.setSuccess(false);
        apiResponse.setData(ex.getMessage());
        return apiResponse;
    }

}
