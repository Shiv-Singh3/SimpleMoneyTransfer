package simpleMoneyTransfer.webServices.api.accountWS;

import com.google.inject.Inject;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simpleMoneyTransfer.constants.CommonConstants;
import simpleMoneyTransfer.exceptions.SimpleMoneyTransferApplicationException;
import simpleMoneyTransfer.exceptions.SimpleMoneyTransferValidationException;
import simpleMoneyTransfer.manager.impl.AccountWebServiceManagerImpl;
import simpleMoneyTransfer.parser.UpdateAccountJsonParser;
import simpleMoneyTransfer.utils.CommonUtils;
import simpleMoneyTransfer.webServices.dto.UpdateDTO;
import simpleMoneyTransfer.webServices.validation.ValidLanguageCode;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/account")
@Api(value = "Account Web Service")
public class UpdateAccountWebService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateAccountWebService.class);

    @Inject
    private UpdateAccountJsonParser updateAccountJsonParser;

    @Inject
    private AccountWebServiceManagerImpl accountWebServiceManager;

    @PUT
    @Path("/updateAccount")
    @ApiOperation(value = "Updates an Account", response = UpdateAccountWebService.class)
    @ApiResponses(value = {
            @ApiResponse(code = CommonConstants.HTTP_STATUS_CREATED,
                    message = "Successfully Updated Account", response = UpdateAccountWebService.class),
            @ApiResponse(code = CommonConstants.HTTP_STATUS_NO_CONTENT,
                    message = "No Content Found", response = UpdateAccountWebService.class),
            @ApiResponse(code = CommonConstants.HTTP_STATUS_NOT_FOUND,
                    message = "404 Not Found", response = UpdateAccountWebService.class),
            @ApiResponse(code = CommonConstants.HTTP_STATUS_BAD_REQUEST,
                    message = "Bad Request",  response = UpdateAccountWebService.class),
            @ApiResponse(code = CommonConstants.HTTP_STATUS_INTERNAL_SERVER_ERROR,
                    message = "Internal Server Error", response = UpdateAccountWebService.class)
    })
    public Response updateAccount(
            @ApiParam(name = "Account Number", value = "Account Number", required = true)
            @QueryParam("accountNumber") Long accountNumber,
            @ApiParam(name = "Accept-Language", value = "The value to be passed as header parameter",
                    required = true, defaultValue = "en-US")
            @HeaderParam("Accept-Language") @ValidLanguageCode String languageCode,
            String inputString) {

        LOGGER.info("Received request for updating account for account no. : {}, with input string : {}",
                accountNumber, inputString);
        UpdateDTO updateDTO;
        try {
            updateDTO = updateAccountJsonParser.parseUpdateJson(inputString, accountNumber);
            accountWebServiceManager.updateAccount(updateDTO);
            LOGGER.info("Successfully Updated account for account no. : {}", updateDTO.getAccountNumber());
            return Response.status(Response.Status.CREATED).build();
        } catch (SimpleMoneyTransferValidationException e) {
            LOGGER.error("Validation Exception occurred while parsing json request body : {}", inputString);
            return CommonUtils.createWebServiceErrorResponse(e);
        } catch(SimpleMoneyTransferApplicationException e) {
            LOGGER.error("Unable To Update Account for account number : {}, Application Exception : {}",
                    accountNumber, e.toString());
            return CommonUtils.createWebServiceErrorResponse(e);
        }
    }
}
