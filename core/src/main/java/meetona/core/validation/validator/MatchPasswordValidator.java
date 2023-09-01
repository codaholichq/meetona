package meetona.core.validation.validator;

import meetona.core.Dto.request.ResetPasswordDto;
import meetona.core.validation.annotation.MatchPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class MatchPasswordValidator implements ConstraintValidator<MatchPassword, ResetPasswordDto> {

    private Boolean allowNull;

    @Override
    public void initialize(MatchPassword constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(ResetPasswordDto value, ConstraintValidatorContext context) {
        String password = value.password();
        String confirmPassword = value.confirmPassword();
        if (allowNull) {
            return null == password && null == confirmPassword;
        }
        return password.equals(confirmPassword);
    }
}
