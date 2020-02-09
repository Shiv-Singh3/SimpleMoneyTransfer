package simpleMoneyTransfer.constants;

import lombok.Getter;

@Getter
public enum ValidLanguageCodes {
    EN_US("en-US");

    private String languageCode;

    ValidLanguageCodes(String languageCode) {
        this.languageCode = languageCode;
    }
}
