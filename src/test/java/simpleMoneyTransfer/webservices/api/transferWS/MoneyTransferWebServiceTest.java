package simpleMoneyTransfer.webservices.api.transferWS;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import simpleMoneyTransfer.constants.Errors;
import simpleMoneyTransfer.exceptions.SimpleMoneyTransferApplicationException;
import simpleMoneyTransfer.exceptions.SimpleMoneyTransferValidationException;
import simpleMoneyTransfer.manager.impl.TransferWebServiceManagerImpl;
import simpleMoneyTransfer.parser.MoneyTransferJsonParser;
import simpleMoneyTransfer.webServices.api.transferWS.MoneyTransferWebService;
import simpleMoneyTransfer.webServices.dto.TransferDTO;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class MoneyTransferWebServiceTest {

    @Mock
    private TransferWebServiceManagerImpl transferWebServiceManagerImpl;

    @Mock
    private MoneyTransferJsonParser moneyTransferJsonParser;

    @InjectMocks
    private MoneyTransferWebService moneyTransferWebService;

    private static final String LANGUAGE_CODE = "en-US";

    @Test
    public void testMoneyTransferWSSuccessResponse() {

        TransferDTO transferDTO = TransferDTO.builder().amount(100.00)
                .sourceAccountNumber(1001L).destinationAccountNumber(1002L).build();

        Mockito.when((moneyTransferJsonParser).parseTransferJson(Mockito.anyString())).thenReturn(transferDTO);
        Mockito.doNothing().when(transferWebServiceManagerImpl).transfer(Mockito.any());

        Response response = moneyTransferWebService.doTransfer(getValidTransferJson(), LANGUAGE_CODE);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void testMoneyTransferWSApplicationException() {

        TransferDTO transferDTO = TransferDTO.builder().amount(100.00)
                .sourceAccountNumber(1001L).destinationAccountNumber(1002L).build();

        Mockito.when((moneyTransferJsonParser).parseTransferJson(Mockito.anyString())).thenReturn(transferDTO);
        Mockito.doThrow(new SimpleMoneyTransferApplicationException(Errors.INVALID_TRANSFER_REQUEST))
                .when(transferWebServiceManagerImpl).transfer(Mockito.any());

        Response response = moneyTransferWebService.doTransfer(getValidTransferJson(), LANGUAGE_CODE);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    @Test
    public void testMoneyTransferWSValidationException() {

        Mockito.doThrow(new SimpleMoneyTransferValidationException(Errors.INVALID_ACCOUNT_TRANSFER_JSON_ERR))
                .when(moneyTransferJsonParser).parseTransferJson(Mockito.anyString());

        Response response = moneyTransferWebService.doTransfer(getInValidTransferJson(), LANGUAGE_CODE);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    String getValidTransferJson() {
        return "{\n" +
                "\t\"sourceAccountNumber\": 1001,\n" +
                "\t\"destinationAccountNumber\": 1002,\n" +
                "\t\"amount\": 200.00\n" +
                "}";
    }

    String getInValidTransferJson() {
        return "{\n" +
                "\t\"sourceAccountNumber\": 1001\n" +
                "\t\"destinationAccountNumber\": 1002,\n" +
                "\t\"amount\": 200.00\n" +
                "}";
    }
}
