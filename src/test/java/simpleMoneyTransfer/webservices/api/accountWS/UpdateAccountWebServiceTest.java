package simpleMoneyTransfer.webservices.api.accountWS;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import simpleMoneyTransfer.constants.Errors;
import simpleMoneyTransfer.exceptions.SimpleMoneyTransferApplicationException;
import simpleMoneyTransfer.manager.impl.AccountWebServiceManagerImpl;
import simpleMoneyTransfer.parser.UpdateAccountJsonParser;
import simpleMoneyTransfer.webServices.api.accountWS.UpdateAccountWebService;
import simpleMoneyTransfer.webServices.dto.UpdateDTO;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class UpdateAccountWebServiceTest {

    @Mock
    private UpdateAccountJsonParser updateAccountJsonParser;

    @Mock
    private AccountWebServiceManagerImpl accountWebServiceManager;

    @InjectMocks
    private UpdateAccountWebService updateAccountWebService;

    private static final String LANGUAGE_CODE = "en-US";

    @Test
    public void testUpdateAccountWSSuccessResponse() {

        UpdateDTO updateDTO = UpdateDTO.builder().accountNumber(1001).emailId("xxx").mobileNo("1234").build();

        Mockito.when((updateAccountJsonParser).parseUpdateJson(Mockito.any(), Mockito.anyInt())).thenReturn(updateDTO);
        Mockito.doNothing().when(accountWebServiceManager).updateAccount(updateDTO);

        Response response = updateAccountWebService
                .updateAccount(1001, LANGUAGE_CODE, getValidUpdateJson());
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void testUpdateAccountWSValidationException() {

        Mockito.when((updateAccountJsonParser)
                .parseUpdateJson(Mockito.any(), Mockito.anyInt())).thenCallRealMethod();

        Response response = updateAccountWebService
                .updateAccount(1001, LANGUAGE_CODE, getInvalidUpdateJson());
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void testUpdateAccountWSWithEmptyBody() {

        Mockito.when((updateAccountJsonParser).parseUpdateJson(Mockito.any(), Mockito.anyInt())).thenCallRealMethod();

        Response response = updateAccountWebService.updateAccount(1001, LANGUAGE_CODE, "");
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void testUpdateAccountWSApplicationException() {

        UpdateDTO updateDTO = UpdateDTO.builder().accountNumber(1001).emailId("xxx").mobileNo("1234").build();

        Mockito.when((updateAccountJsonParser).parseUpdateJson(Mockito.any(), Mockito.anyInt())).thenReturn(updateDTO);
        Mockito.doThrow(new SimpleMoneyTransferApplicationException(Errors.ACCOUNT_NUMBER_NOT_FOUND_ERR))
                .when(accountWebServiceManager).updateAccount(updateDTO);

        Response response = updateAccountWebService.updateAccount(1001, LANGUAGE_CODE, getValidUpdateJson());
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    public String getValidUpdateJson() {
        return "{\n" +
                "\t\"emailId\": \"xxx\",\n" +
                "\t\"mobileNo\": \"1111\"\n" +
                "}";
    }

    public String getInvalidUpdateJson() {
        return "{\n" +
                "\t\"emailId\": \"xxx\"\n" +
                "\t\"mobileNo\": \"1111\"\n" +
                "}";
    }
}
