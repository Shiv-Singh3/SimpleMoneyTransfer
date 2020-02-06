package simpleMoneyTransfer.webServices.validation;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LanguageCodeValidator.class)
public @interface ValidLanguageCode {

    String message() default "{ Invalid Language Code }";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

class LanguageCodeValidator implements ConstraintValidator<ValidLanguageCode, Object> {

    @Override
    public void initialize(ValidLanguageCode validLanguageCode) {
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        String languageCode = (String) object;
        return languageCode.equals("en-US");
    }
}
