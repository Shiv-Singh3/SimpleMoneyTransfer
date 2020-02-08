package simpleMoneyTransfer.parser;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simpleMoneyTransfer.constants.CommonConstants;
import simpleMoneyTransfer.constants.Errors;
import simpleMoneyTransfer.exceptions.SimpleMoneyTransferValidationException;
import simpleMoneyTransfer.utils.CommonUtils;
import simpleMoneyTransfer.webServices.dto.UpdateDTO;

public class UpdateAccountJsonParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateAccountJsonParser.class);

    public UpdateDTO parseUpdateJson(String updateJson, Integer accountNumber) {

        try {
            JSONObject jsonObject = new JSONObject(updateJson);

            String emailId = (String) CommonUtils.getObjectFromJson(jsonObject, CommonConstants.EMAIL_ID);
            String mobileNo = (String) CommonUtils.getObjectFromJson(jsonObject, CommonConstants.MOBILE_NO);

            if (StringUtils.isEmpty(emailId) && StringUtils.isEmpty(mobileNo)) {
                LOGGER.error("Invalid Request Json : {}, mobile and email both cannot be null", updateJson);
                throw new SimpleMoneyTransferValidationException(Errors.INVALID_ACCOUNT_UPDATE_JSON_ERR);
            }
            return UpdateDTO.builder().accountNumber(accountNumber).emailId(emailId).mobileNo(mobileNo).build();
        } catch (JSONException e) {
            LOGGER.error("Exception occurred while parsing update account request json for account number : {} : {}",
                    accountNumber, e.toString());
            throw new SimpleMoneyTransferValidationException(Errors.INVALID_ACCOUNT_UPDATE_JSON_ERR,
                    "Exception occurred while parsing json request body", e);
        }
    }
}
