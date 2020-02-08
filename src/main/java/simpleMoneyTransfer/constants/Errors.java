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
    INVALID_ACCOUNT_TRANSFER_JSON_ERR("2003", "Invalid JSON for transferring amount"),
    INVALID_TRANSFER_REQUEST("2004", "Invalid Transaction Request"),
    ACCOUNT_NUMBER_NOT_FOUND_ERR("3001", "Account Number Not Found"),
    ACCOUNT_NUMBER_ALREADY_EXISTS_ERR("3002", "Account Number Already Exists");

    @ToString.Include
    private String code;

    @ToString.Include
    private String description;
}
