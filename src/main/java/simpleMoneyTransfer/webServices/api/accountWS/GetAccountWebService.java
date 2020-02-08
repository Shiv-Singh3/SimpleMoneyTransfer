package simpleMoneyTransfer.webServices.api.accountWS;

import com.google.inject.Inject;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simpleMoneyTransfer.constants.CommonConstants;
import simpleMoneyTransfer.constants.Errors;
import simpleMoneyTransfer.exceptions.SimpleMoneyTransferApplicationException;
import simpleMoneyTransfer.manager.impl.AccountWebServiceManagerImpl;
import simpleMoneyTransfer.utils.CommonUtils;
import simpleMoneyTransfer.webServices.dto.AccountDTO;
import simpleMoneyTransfer.webServices.validation.ValidLanguageCode;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/account")
@Api(value = "Account Web Service")
public class GetAccountWebService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetAccountWebService.class);

    @Inject
    private AccountWebServiceManagerImpl accountWebServiceManagerImpl;

    @GET
    @Path("/getAccount")
    @ApiOperation(value = "Fecthes Account for given account number", response = CreateAccountWebService.class)
    @ApiResponses(value = {
            @ApiResponse(code = CommonConstants.HTTP_STATUS_OK,
                    message = "Successfully retreived account info", response = CreateAccountWebService.class),
            @ApiResponse(code = CommonConstants.HTTP_STATUS_NO_CONTENT,
                    message = "No Content Found for given account number", response = CreateAccountWebService.class),
            @ApiResponse(code = CommonConstants.HTTP_STATUS_BAD_REQUEST,
                    message = "Bad Request", response = CreateAccountWebService.class),
            @ApiResponse(code = CommonConstants.HTTP_STATUS_NOT_FOUND,
                    message = "404 Not Found", response = CreateAccountWebService.class),
            @ApiResponse(code = CommonConstants.HTTP_STATUS_INTERNAL_SERVER_ERROR,
                    message = "Internal Server Error", response = CreateAccountWebService.class)
    })
    public Response getAccount(@ApiParam(name = "Account Number", value = "Account Number", required = true)
                               @QueryParam("accountNumber") Integer accountNumber,
                               @ApiParam(name = "Accept-Language", value = "The value to be passed as header parameter",
                                       required = true, defaultValue = "en-US")
                               @HeaderParam("Accept-Language") @ValidLanguageCode String languageCode) {

        LOGGER.info("Received request for fetching account for account number : {}", accountNumber);
        AccountDTO accountDTO;

        try {
            accountDTO = accountWebServiceManagerImpl.getAccount(accountNumber);
            LOGGER.info("Successfully fetched account for account number : {}", accountNumber);
            return Response.status(Response.Status.OK).entity(accountDTO.toString()).build();
        } catch (SimpleMoneyTransferApplicationException e) {
            LOGGER.error("Unable to fetch account for account number : {}, Application exception : {}",
                    accountNumber, e.toString());
            return CommonUtils.createWebServiceErrorResponse(e);
        }
    }
}
