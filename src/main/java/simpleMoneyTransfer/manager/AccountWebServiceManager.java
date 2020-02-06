package simpleMoneyTransfer.manager;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simpleMoneyTransfer.constants.CommonConstants;
import simpleMoneyTransfer.constants.Errors;
import simpleMoneyTransfer.exceptions.SimpleMoneyTransferApplicationException;
import simpleMoneyTransfer.exceptions.SimpleMoneyTransferValidationException;
import simpleMoneyTransfer.utils.CommonUtils;
import simpleMoneyTransfer.webServices.dto.AccountDTO;
import java.util.Currency;
import java.util.concurrent.ConcurrentHashMap;

public class AccountWebServiceManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountWebServiceManager.class);

    public void createAccount(AccountDTO accountDTO, ConcurrentHashMap<Integer, AccountDTO> accounts) {
        Integer accountNumber = accountDTO.getAccountNumber();
        accounts.put(accountNumber, accountDTO);
    }

    public AccountDTO parseAccountJson(String accountJson) {

        AccountDTO accountDTO;
        try {
            JSONObject jsonObject = new JSONObject(accountJson);

            String name = (String) CommonUtils.getObjectFromJson(jsonObject, CommonConstants.NAME);
            Integer accountNumber = (Integer) CommonUtils.getObjectFromJson(jsonObject, CommonConstants.ACCOUNT_NUMBER);
            Double balance = (Double) CommonUtils.getObjectFromJson(jsonObject, CommonConstants.BALANCE);
            Currency currency = (Currency) CommonUtils.getObjectFromJson(jsonObject, CommonConstants.CURRENCY);

            accountNumber = (accountNumber == null) ? CommonUtils.generateUniqueAccountNumber() : accountNumber;
            balance = (balance == null) ? CommonUtils.getDefaultBalance() : balance;
            currency = (currency == null) ? CommonUtils.getDefaultCurrency() : currency;

            accountDTO = AccountDTO.builder()
                    .name(name).accountNumber(accountNumber).balance(balance).currency(currency).build();
            return accountDTO;
        } catch (JSONException e) {
            throw new SimpleMoneyTransferValidationException(
                    Errors.INVALID_ACCOUNT_CREATE_JSON_ERR, "Exception occurred while parsing json request body", e);
        }
    }
}
