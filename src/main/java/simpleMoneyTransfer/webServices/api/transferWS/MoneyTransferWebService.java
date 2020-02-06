package simpleMoneyTransfer.webServices.api.transferWS;

import com.google.inject.Inject;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simpleMoneyTransfer.constants.CommonConstants;
import simpleMoneyTransfer.manager.TransferWebServiceManager;
import simpleMoneyTransfer.webServices.dto.TransferDTO;
import simpleMoneyTransfer.webServices.validation.ValidLanguageCode;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/transfer")
@Api(value = "Transfer Web Services")
public class MoneyTransferWebService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoneyTransferWebService.class);

    @Inject
    private TransferWebServiceManager transferWebServiceManager;

    @POST
    @ApiOperation(value = "Triggers and executes a money transfer", response = MoneyTransferWebService.class)
    @ApiResponses(value = {
            @ApiResponse(code = CommonConstants.HTTP_STATUS_CREATED,
                    message = "Susseccfully Initiated the transaction", response = MoneyTransferWebService.class),
            @ApiResponse(code = CommonConstants.HTTP_STATUS_BAD_REQUEST,
                    message = "Bad Request", response = MoneyTransferWebService.class),
            @ApiResponse(code = CommonConstants.HTTP_STATUS_INTERNAL_SERVER_ERROR,
                    message = "Internal Server Error", response = MoneyTransferWebService.class)
    })
    public Response doTransfer(final String inputString,
            @ApiParam(name = "Accept-Language", value = "The value to be passed as header parameter",
            required = true, defaultValue = "en-US")
            @HeaderParam("Accept-Language") @ValidLanguageCode String languageCode) {

        LOGGER.info("Received WebService Request for money transfer");
        LOGGER.debug("Request Body : {}", inputString);

        TransferDTO transferDTO = transferWebServiceManager.parseTransferJson(inputString);
        transferWebServiceManager.transfer(transferDTO);

        return Response.status(Response.Status.CREATED).build();
    }
}
