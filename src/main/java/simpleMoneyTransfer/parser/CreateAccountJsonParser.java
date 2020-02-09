package simpleMoneyTransfer.parser;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import simpleMoneyTransfer.constants.CommonConstants;
import simpleMoneyTransfer.constants.Errors;
import simpleMoneyTransfer.exceptions.SimpleMoneyTransferValidationException;
import simpleMoneyTransfer.utils.CommonUtils;
import simpleMoneyTransfer.webServices.dto.AccountDTO;
import java.util.Currency;

public class CreateAccountJsonParser {

    public AccountDTO parseAccountJson(String accountJson) throws SimpleMoneyTransferValidationException {

        AccountDTO accountDTO;
        try {
            JSONObject jsonObject = new JSONObject(accountJson);

            String name = null;
            Long accountNumber = null;
            Double balance = null;
            String currencyString;
            Currency currency = null;
            String emailId = null;
            String mobileNo = null;

            if (jsonObject.has(CommonConstants.NAME)) {
                name = jsonObject.getString(CommonConstants.NAME);
            }

            if (jsonObject.has(CommonConstants.ACCOUNT_NUMBER)) {
                accountNumber = jsonObject.getLong(CommonConstants.ACCOUNT_NUMBER);
            }

            if (jsonObject.has(CommonConstants.BALANCE)) {
                balance = jsonObject.getDouble(CommonConstants.BALANCE);
            }

            if (jsonObject.has(CommonConstants.CURRENCY)) {
                currencyString = jsonObject.getString(CommonConstants.CURRENCY);
                currency = Currency.getInstance(currencyString);
            }

            if (jsonObject.has(CommonConstants.EMAIL_ID)) {
                emailId = jsonObject.getString(CommonConstants.EMAIL_ID);
            }

            if (jsonObject.has(CommonConstants.MOBILE_NO)) {
                mobileNo = jsonObject.getString(CommonConstants.MOBILE_NO);
            }

            accountNumber = accountNumber == null ? CommonUtils.generateUniqueAccountNumber() : accountNumber;
            balance = (balance == null) ? CommonUtils.getDefaultBalance() : balance;
            currency = (currency == null) ? CommonUtils.getDefaultCurrency() : currency;

            accountDTO = AccountDTO.builder().name(name).accountNumber(accountNumber).balance(balance).
                    currency(currency).emailId(emailId).mobileNo(mobileNo).build();
            return accountDTO;
        } catch (JSONException e) {
            throw new SimpleMoneyTransferValidationException(
                    Errors.INVALID_ACCOUNT_CREATE_JSON_ERR, "Exception occurred while parsing json request body", e);
        }
    }
}
