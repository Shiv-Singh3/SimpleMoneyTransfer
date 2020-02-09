package simpleMoneyTransfer.webServices.api.accountWS;

import com.google.inject.Inject;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simpleMoneyTransfer.constants.CommonConstants;
import simpleMoneyTransfer.constants.ValidLanguageCodes;
import simpleMoneyTransfer.exceptions.SimpleMoneyTransferApplicationException;
import simpleMoneyTransfer.manager.impl.AccountWebServiceManagerImpl;
import simpleMoneyTransfer.utils.CommonUtils;
import simpleMoneyTransfer.webServices.validation.ValidLanguageCode;
import javax.ws.rs.DELETE;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/account")
@Api(value = "Account Web Service")
@Slf4j
public class DeleteAccountWebService {

    @Inject
    private AccountWebServiceManagerImpl accountWebServiceManager;

    @DELETE
    @Path("/delete")
    @ApiOperation(value = "Deletes account", response = DeleteAccountWebService.class)
    @ApiResponses(value = {
            @ApiResponse(code = CommonConstants.HTTP_STATUS_CREATED,
                    message = "Successfully Deleted account", response = DeleteAccountWebService.class),
            @ApiResponse(code = CommonConstants.HTTP_STATUS_NO_CONTENT,
                    message = "Account Number Not Found", response = DeleteAccountWebService.class),
            @ApiResponse(code = CommonConstants.HTTP_STATUS_BAD_REQUEST,
                    message = "Bad Request", response = DeleteAccountWebService.class),
            @ApiResponse(code =  CommonConstants.HTTP_STATUS_NOT_FOUND,
                    message = "404 Not Found", response = DeleteAccountWebService.class),
            @ApiResponse(code = CommonConstants.HTTP_STATUS_INTERNAL_SERVER_ERROR,
                    message = "Internal Server Error", response = DeleteAccountWebService.class)
    })
    public Response deleteAccount(
            @ApiParam(name = "Account Number", value = "Account Number for account to be deleted", required = true)
            @QueryParam("accountNumber") Long accountNumber,
            @ApiParam(name = "Accept-Language", value = "The value to be passed as header parameter",
                    required = true, defaultValue = "en-US")
            @HeaderParam("Accept-Language") @ValidLanguageCode(ValidLanguageCodes.EN_US) String languageCode) {
        try {
            accountWebServiceManager.deleteAccount(accountNumber);
            log.info("Successfully Deleted Account for account number : {}", accountNumber);
            return Response.status(Response.Status.CREATED).build();
        } catch (SimpleMoneyTransferApplicationException e) {
            log.error("Unable to delete account for account number : {}, Application Exception : {}",
                    accountNumber, e.toString());
            return CommonUtils.createWebServiceErrorResponse(e);
        }
    }
}