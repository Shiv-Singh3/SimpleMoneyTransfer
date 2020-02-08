package simpleMoneyTransfer.parser;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import simpleMoneyTransfer.constants.CommonConstants;
import simpleMoneyTransfer.constants.Errors;
import simpleMoneyTransfer.exceptions.SimpleMoneyTransferValidationException;
import simpleMoneyTransfer.utils.CommonUtils;
import simpleMoneyTransfer.webServices.dto.TransferDTO;

public class MoneyTransferJsonParser {

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
}
