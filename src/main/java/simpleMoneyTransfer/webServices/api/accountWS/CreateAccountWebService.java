package simpleMoneyTransfer.webServices.api.accountWS;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.google.inject.Inject;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import simpleMoneyTransfer.constants.CommonConstants;
import simpleMoneyTransfer.constants.ValidLanguageCodes;
import simpleMoneyTransfer.exceptions.SimpleMoneyTransferApplicationException;
import simpleMoneyTransfer.exceptions.SimpleMoneyTransferValidationException;
import simpleMoneyTransfer.manager.impl.AccountWebServiceManagerImpl;
import simpleMoneyTransfer.parser.CreateAccountJsonParser;
import simpleMoneyTransfer.utils.CommonUtils;
import simpleMoneyTransfer.webServices.dto.AccountDTO;
import simpleMoneyTransfer.webServices.validation.ValidCreateAccountJson;
import simpleMoneyTransfer.webServices.validation.ValidLanguageCode;

@Path("/account")
@Api(value = "Account Web Service")
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class CreateAccountWebService {

    @Inject
    private AccountWebServiceManagerImpl accountWebServiceManagerImpl;

    @Inject
    private CreateAccountJsonParser createAccountJsonParser;

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
                  @HeaderParam("Accept-Language") @ValidLanguageCode(ValidLanguageCodes.EN_US) String languageCode) {

        log.info("Received Request for creating account");
        log.debug("Input Json Body : {}", inputString);

        try {
            AccountDTO accountDTO = createAccountJsonParser.parseAccountJson(inputString);
            accountWebServiceManagerImpl.createAccount(accountDTO);
            log.info("Account created successfully for account number : {}", accountDTO.getAccountNumber());
            return Response.status(Response.Status.CREATED).build();
        } catch (SimpleMoneyTransferValidationException e) {
            log.error("Invalid Request, Input Json : {}", e.toString());
            return CommonUtils.createWebServiceErrorResponse(e);
        } catch (SimpleMoneyTransferApplicationException e) {
            log.error("Unable Create Account, Application exception : {}", e.toString());
            return CommonUtils.createWebServiceErrorResponse(e);
        }
    }
}

//todo fix annotation validation