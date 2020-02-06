package simpleMoneyTransfer.webServices;

import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import io.swagger.annotations.*;
import simpleMoneyTransfer.constants.CommonConstants;
import simpleMoneyTransfer.manager.AccountWebServiceManager;
import simpleMoneyTransfer.webServices.dto.AccountDTO;
import simpleMoneyTransfer.webServices.validation.ValidCreateAccountJson;
import simpleMoneyTransfer.webServices.validation.ValidLanguageCode;
import java.util.concurrent.ConcurrentHashMap;

@Path("/account")
@Api(value = "Account Web Service")
public class AccountWebServices {

    private AccountWebServiceManager accountWebServiceManager;

    private ConcurrentHashMap<Integer, AccountDTO> accounts;

    @Inject
    public AccountWebServices(@Named("accountWebServiceManager") AccountWebServiceManager accountWebServiceManager) {
        this.accountWebServiceManager = accountWebServiceManager;
        this.accounts = new ConcurrentHashMap<>();
    }

    @POST
    @Path("/create")
    @ApiOperation(value = "Creates an Account", response = AccountWebServices.class)
    @ApiResponses(value = {
            @ApiResponse(code = CommonConstants.HTTP_STATUS_CREATED,
                    message = "Successfully created an account", response = AccountWebServices.class),
            @ApiResponse(code = CommonConstants.HTTP_STATUS_NOT_FOUND,
                    message = "Not Found", response = AccountWebServices.class),
            @ApiResponse(code = CommonConstants.HTTP_STATUS_INTERNAL_SERVER_ERROR,
                    message = "Error while processing the request",response = AccountWebServices.class),
            @ApiResponse(code = CommonConstants.HTTP_STATUS_BAD_REQUEST,
                    message = "Bad Request", response = AccountWebServices.class)
    })
    public Response createAccount(@ValidCreateAccountJson String inputString,
                  @ApiParam(name = "Accept-Language", value = "The value to be passed as header parameter",
                          required = true, defaultValue = "en-US")
                  @HeaderParam("Accept-Language") @ValidLanguageCode String languageCode) {
        AccountDTO accountDTO = accountWebServiceManager.parseAccountJson(inputString);
        accountWebServiceManager.createAccount(accountDTO, accounts);
        return Response.status(Response.Status.CREATED).entity(accounts.toString()).build();
    }
}

//todo check for duplicate account creation
//todo fix annotation validation