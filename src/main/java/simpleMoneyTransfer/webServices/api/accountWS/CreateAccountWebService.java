package simpleMoneyTransfer.webServices.api.accountWS;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import com.google.inject.Inject;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simpleMoneyTransfer.accessor.DatabaseAccessor;
import simpleMoneyTransfer.constants.CommonConstants;
import simpleMoneyTransfer.constants.Errors;
import simpleMoneyTransfer.exceptions.SimpleMoneyTransferApplicationException;
import simpleMoneyTransfer.manager.AccountWebServiceManager;
import simpleMoneyTransfer.webServices.dto.AccountDTO;
import simpleMoneyTransfer.webServices.validation.ValidCreateAccountJson;
import simpleMoneyTransfer.webServices.validation.ValidLanguageCode;

@Path("/account")
@Api(value = "Account Web Service")
public class CreateAccountWebService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateAccountWebService.class);

    @Inject
    private AccountWebServiceManager accountWebServiceManager;

    @POST
    @Path("/create")
    @ApiOperation(value = "Creates an Account", response = CreateAccountWebService.class)
    @ApiResponses(value = {
            @ApiResponse(code = CommonConstants.HTTP_STATUS_CREATED,
                    message = "Successfully created an account", response = CreateAccountWebService.class),
            @ApiResponse(code = CommonConstants.HTTP_STATUS_NOT_FOUND,
                    message = "Not Found", response = CreateAccountWebService.class),
            @ApiResponse(code = CommonConstants.HTTP_STATUS_INTERNAL_SERVER_ERROR,
                    message = "Error while processing the request",response = CreateAccountWebService.class),
            @ApiResponse(code = CommonConstants.HTTP_STATUS_BAD_REQUEST,
                    message = "Bad Request", response = CreateAccountWebService.class)
    })
    public Response createAccount(@ValidCreateAccountJson String inputString,
                  @ApiParam(name = "Accept-Language", value = "The value to be passed as header parameter",
                          required = true, defaultValue = "en-US")
                  @HeaderParam("Accept-Language") @ValidLanguageCode String languageCode) {
        AccountDTO accountDTO = accountWebServiceManager.parseAccountJson(inputString);
        accountWebServiceManager.createAccount(accountDTO);
        return Response.status(Response.Status.CREATED).build();
    }
}

//todo check for duplicate account creation
//todo fix annotation validation