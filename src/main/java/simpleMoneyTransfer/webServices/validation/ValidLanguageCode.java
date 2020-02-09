package simpleMoneyTransfer.webServices.validation;

import lombok.extern.slf4j.Slf4j;
import simpleMoneyTransfer.constants.Errors;
import simpleMoneyTransfer.constants.ValidLanguageCodes;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.PARAMETER, ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LanguageCodeValidator.class)
public @interface ValidLanguageCode {

    String message() default "{ Invalid Language Code }";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    ValidLanguageCodes value();

    @Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        ValidLanguageCode[] value();
    }
}

@Slf4j
class LanguageCodeValidator implements ConstraintValidator<ValidLanguageCode, Object> {

    private ValidLanguageCodes validLanguageCodes;

    @Override
    public void initialize(ValidLanguageCode validLanguageCode) {
        this.validLanguageCodes = validLanguageCode.value();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        if (object == null) {
            return true;
        }
        if (validLanguageCodes == ValidLanguageCodes.EN_US) {
            return true;
        } else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(Errors.INVALID_LANGUAGE_CODE_ERR.getDescription())
                    .addConstraintViolation();
            return false;
        }
    }
}
