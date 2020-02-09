package simpleMoneyTransfer.parser;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import simpleMoneyTransfer.constants.CommonConstants;
import simpleMoneyTransfer.constants.Errors;
import simpleMoneyTransfer.exceptions.SimpleMoneyTransferValidationException;
import simpleMoneyTransfer.webServices.dto.TransferDTO;

public class MoneyTransferJsonParser {

    public TransferDTO parseTransferJson(String transferJson) {

        TransferDTO transferDTO;

        try {
            JSONObject jsonObject = new JSONObject(transferJson);
            Long sourceAccount = jsonObject.getLong(CommonConstants.SOURCE_ACCOUNT_NUM);
            Long destinationAccount = jsonObject.getLong(CommonConstants.DESTINATION_ACCOUNT_NUM);
            Double amount = jsonObject.getDouble(CommonConstants.TRANSFER_AMOUNT);

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
}
