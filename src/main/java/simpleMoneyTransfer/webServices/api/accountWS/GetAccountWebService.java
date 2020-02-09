package simpleMoneyTransfer.webServices.api.accountWS;

import com.google.inject.Inject;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import simpleMoneyTransfer.constants.CommonConstants;
import simpleMoneyTransfer.constants.ValidLanguageCodes;
import simpleMoneyTransfer.exceptions.SimpleMoneyTransferApplicationException;
import simpleMoneyTransfer.manager.impl.AccountWebServiceManagerImpl;
import simpleMoneyTransfer.utils.CommonUtils;
import simpleMoneyTransfer.webServices.dto.AccountDTO;
import simpleMoneyTransfer.webServices.response.GetAccountResponse;
import simpleMoneyTransfer.webServices.validation.ValidLanguageCode;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/account")
@Api(value = "Account Web Service")
@Slf4j
public class GetAccountWebService {

    @Inject
    private AccountWebServiceManagerImpl accountWebServiceManagerImpl;

    @GET
    @Path("/getAccount")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Fetches Account for given account number", response = CreateAccountWebService.class)
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
    public Response getAccount(@ApiParam(name = "accountNumber", value = "Account Number", required = true)
                               @QueryParam("accountNumber") Long accountNumber,
                               @ApiParam(name = "Accept-Language", value = "The value to be passed as header parameter",
                                       required = true, defaultValue = "en-US")
                               @HeaderParam("Accept-Language") @ValidLanguageCode(ValidLanguageCodes.EN_US) String languageCode) {

        log.info("Received request for fetching account for account number : {}", accountNumber);
        AccountDTO accountDTO;

        try {
            accountDTO = accountWebServiceManagerImpl.getAccount(accountNumber);
            log.info("Successfully fetched account for account number : {} : {}", accountNumber, accountDTO.toString());
            return getWebResponseForGetAccount(accountDTO);
        } catch (SimpleMoneyTransferApplicationException e) {
            log.error("Unable to fetch account for account number : {}, Application exception : {}",
                    accountNumber, e.toString());
            return CommonUtils.createWebServiceErrorResponse(e);
        }
    }

    public Response getWebResponseForGetAccount(AccountDTO accountDTO) {
        GetAccountResponse response = new GetAccountResponse(accountDTO);
        String responseString = CommonUtils.serializeToJsonStringSilently(response);
        return Response.status(Response.Status.OK).entity(responseString).build();
    }
}
