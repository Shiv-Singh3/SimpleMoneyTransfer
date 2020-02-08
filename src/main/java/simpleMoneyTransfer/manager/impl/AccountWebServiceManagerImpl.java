package simpleMoneyTransfer.manager.impl;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simpleMoneyTransfer.accessor.impl.DataBaseAccessorImpl;
import simpleMoneyTransfer.constants.CommonConstants;
import simpleMoneyTransfer.constants.Errors;
import simpleMoneyTransfer.exceptions.SimpleMoneyTransferApplicationException;
import simpleMoneyTransfer.exceptions.SimpleMoneyTransferValidationException;
import simpleMoneyTransfer.manager.spi.AccountWebServiceManager;
import simpleMoneyTransfer.utils.CommonUtils;
import simpleMoneyTransfer.webServices.dto.AccountDTO;
import com.google.inject.Inject;
import java.util.Currency;

public class AccountWebServiceManagerImpl implements AccountWebServiceManager{

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountWebServiceManagerImpl.class);

    @Inject
    private DataBaseAccessorImpl accessor;

    @Override
    public void createAccount(AccountDTO accountDTO) {
        Integer accountNumber = accountDTO.getAccountNumber();
        if (!accessor.hasKey(accountNumber)) {
            accessor.save(accountNumber, accountDTO);
        } else {
            LOGGER.error("Account already exists for the account number : {}", accountNumber);
            throw new SimpleMoneyTransferApplicationException(Errors.ACCOUNT_NUMBER_ALREADY_EXISTS_ERR);
        }
    }

    @Override
    public void updateAccount(AccountDTO accountDTO) {
        Integer accountNumber = accountDTO.getAccountNumber();
        if (accessor.hasKey(accountNumber)) {
            accessor.save(accountNumber, accountDTO);
        } else {
            LOGGER.error("Account Not Found for account number : {}", accountNumber);
            throw new SimpleMoneyTransferApplicationException(Errors.ACCOUNT_NUMBER_NOT_FOUND_ERR);
        }
    }

    @Override
    public AccountDTO getAccount(Integer accountNumber) {
        if (accessor.hasKey(accountNumber)) {
            return accessor.get(accountNumber);
        } else {
            LOGGER.error("Account Number Not Found : {}", accountNumber);
            throw new SimpleMoneyTransferApplicationException(Errors.ACCOUNT_NUMBER_NOT_FOUND_ERR);
        }
    }

    @Override
    public void deleteAccount(Integer accountNumber) {
        if (accessor.hasKey(accountNumber)) {
            accessor.remove(accountNumber);
        } else  {
            LOGGER.error("Account not found for account number : {}", accountNumber);
            throw new SimpleMoneyTransferApplicationException(Errors.ACCOUNT_NUMBER_NOT_FOUND_ERR);
        }
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
