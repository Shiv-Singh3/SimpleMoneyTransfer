package simpleMoneyTransfer.manager.impl;

import com.google.inject.Inject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import simpleMoneyTransfer.constants.CommonConstants;
import simpleMoneyTransfer.constants.Errors;
import simpleMoneyTransfer.exceptions.SimpleMoneyTransferValidationException;
import simpleMoneyTransfer.manager.spi.TransferWebServiceManager;
import simpleMoneyTransfer.utils.CommonUtils;
import simpleMoneyTransfer.webServices.dto.AccountDTO;
import simpleMoneyTransfer.webServices.dto.TransferDTO;

public class TransferWebServiceManagerImpl implements TransferWebServiceManager {

    @Inject
    private AccountWebServiceManagerImpl accountWebServiceManagerImpl;

    @Inject
    private MoneyTransferImpl moneyTransferImpl;

    public TransferDTO parseTransferJson(String transferJson) {

        TransferDTO transferDTO;

        try {
            JSONObject jsonObject = new JSONObject(transferJson);
            Integer sourceAccount = (Integer) CommonUtils.getObjectFromJson(
                    jsonObject, CommonConstants.SOURCE_ACCOUNT_NUM);
            Integer destinationAccount = (Integer) CommonUtils.getObjectFromJson(
                    jsonObject, CommonConstants.DESTINATION_ACCOUNT_NUM);
            Double amount = (Double) CommonUtils.getObjectFromJson(
                    jsonObject, CommonConstants.TRANSFER_AMOUNT);

            transferDTO = TransferDTO.builder()
                    .sourceAccountNumber(sourceAccount)
                    .destinationAccountNumber(destinationAccount)
                    .amount(amount)
                    .build();
            return transferDTO;
        } catch (JSONException e) {
            throw new SimpleMoneyTransferValidationException(
                    Errors.INVALID_ACCOUNT_TRANSFER_JSON_ERR, "Exception occurred while parsing json request body", e);
        }
    }

    @Override
    public void transfer(TransferDTO transferDTO) {
        Integer sourceAccountNum = transferDTO.getSourceAccountNumber();
        Integer destAccountNum = transferDTO.getDestinationAccountNumber();
        Double amount = transferDTO.getAmount();

        AccountDTO sourceAccount = accountWebServiceManagerImpl.getAccount(sourceAccountNum);
        AccountDTO destAccount = accountWebServiceManagerImpl.getAccount(destAccountNum);

        if (checkAccountBalanceForWithdrawl(sourceAccount, amount)) {
            sourceAccount = moneyTransferImpl.withdraw(sourceAccount, amount);
            destAccount = moneyTransferImpl.credit(destAccount, amount);
            accountWebServiceManagerImpl.updateAccount(sourceAccount);
            accountWebServiceManagerImpl.updateAccount(destAccount);
        }
    }

    public boolean checkAccountBalanceForWithdrawl(AccountDTO accountDTO, Double amount) {
        return amount <= accountDTO.getBalance();
    }
}
