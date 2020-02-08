package simpleMoneyTransfer.webServices.api.transferWS;

import com.google.inject.Inject;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simpleMoneyTransfer.constants.CommonConstants;
import simpleMoneyTransfer.exceptions.SimpleMoneyTransferApplicationException;
import simpleMoneyTransfer.manager.impl.TransferWebServiceManagerImpl;
import simpleMoneyTransfer.utils.CommonUtils;
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
    private TransferWebServiceManagerImpl transferWebServiceManagerImpl;

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

        TransferDTO transferDTO = null;

        try {
            transferDTO = transferWebServiceManagerImpl.parseTransferJson(inputString);
            transferWebServiceManagerImpl.transfer(transferDTO);
            LOGGER.info("Successfully Transferred amount : {}, source account number : {}, " +
                    "destination account number : {}", transferDTO.getAmount(),
                    transferDTO.getSourceAccountNumber(), transferDTO.getDestinationAccountNumber());
            return Response.status(Response.Status.CREATED).build();
        } catch (SimpleMoneyTransferApplicationException e) {
            LOGGER.error("Exception occurred while transferring money for source account number : {}, " +
                    "destination account number : {} : {}",
                    transferDTO != null ? transferDTO.getSourceAccountNumber() : null,
                    transferDTO != null ? transferDTO.getDestinationAccountNumber() : null,
                    e.toString());
            return CommonUtils.createWebServiceErrorResponse(e);
        }
    }
}
