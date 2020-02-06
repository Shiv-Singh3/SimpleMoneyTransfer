package simpleMoneyTransfer.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString(onlyExplicitlyIncluded = true)
public enum Errors {

    INVALID_LANGUAGE_CODE_ERR("1001", "Invalid Language Code"),
    INVALID_NAME_ERR("2001", "Invalid/Empty Name"),
    INVALID_ACCOUNT_CREATE_JSON_ERR("2002", "Invalid JSON for creating account"),
    ACCOUNT_NUMBER_NOT_FOUND_ERR("3001", "Account Number Not Found");

    @ToString.Include
    private String code;

    @ToString.Include
    private String description;
}
