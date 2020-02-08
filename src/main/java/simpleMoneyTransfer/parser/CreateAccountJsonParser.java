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

    public AccountDTO parseAccountJson(String accountJson) {

        AccountDTO accountDTO;
        try {
            JSONObject jsonObject = new JSONObject(accountJson);

            String name = (String) CommonUtils.getObjectFromJson(jsonObject, CommonConstants.NAME);
            Integer accountNumber = (Integer) CommonUtils.getObjectFromJson(jsonObject, CommonConstants.ACCOUNT_NUMBER);
            Double balance = (Double) CommonUtils.getObjectFromJson(jsonObject, CommonConstants.BALANCE);
            Currency currency = (Currency) CommonUtils.getObjectFromJson(jsonObject, CommonConstants.CURRENCY);
            String emailId = (String) CommonUtils.getObjectFromJson(jsonObject, CommonConstants.EMAIL_ID);
            String mobileNo = (String) CommonUtils.getObjectFromJson(jsonObject, CommonConstants.MOBILE_NO);

            accountNumber = (accountNumber == null) ? CommonUtils.generateUniqueAccountNumber() : accountNumber;
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
