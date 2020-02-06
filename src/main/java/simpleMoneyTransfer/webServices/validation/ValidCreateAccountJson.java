package simpleMoneyTransfer.webServices.validation;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simpleMoneyTransfer.constants.CommonConstants;
import simpleMoneyTransfer.constants.Errors;
import simpleMoneyTransfer.exceptions.SimpleMoneyTransferApplicationException;
import simpleMoneyTransfer.utils.CommonUtils;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Currency;

@Target({ ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = JsonValidator.class)
public @interface ValidCreateAccountJson {

    String message() default "{ Invalid Language Code }";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

class JsonValidator implements ConstraintValidator<ValidCreateAccountJson, Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonValidator.class);

    @Override
    public void initialize(ValidCreateAccountJson validJson) {
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        String accountJson = (String) object;
        try {
            JSONObject jsonObject = new JSONObject(accountJson);

            String name = (String) CommonUtils.getObjectFromJson(jsonObject, CommonConstants.NAME);
            Integer accountNumber = (Integer) CommonUtils.getObjectFromJson(jsonObject, CommonConstants.ACCOUNT_NUMBER);
            Double balance = (Double) CommonUtils.getObjectFromJson(jsonObject, CommonConstants.BALANCE);
            Currency currency = (Currency) CommonUtils.getObjectFromJson(jsonObject, CommonConstants.CURRENCY);

            if (name == null || name.isEmpty()) {
                LOGGER.error("Cannot create Account with empty Name");
                return false;
            }

            if ((accountNumber == null || accountNumber == 0) && (balance != null || currency != null)) {
                LOGGER.error("Cannot Create A Balance/Currency Account with empty Account Number");
                return false;
            }
        } catch (JSONException e) {
            LOGGER.error("Exception occurred while validating Json");
            return false;
        }
        return true;
    }
}

