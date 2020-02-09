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
import simpleMoneyTransfer.webServices.api.accountWS.DeleteAccountWebService;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class DeleteAccountWebServiceTest {

    @Mock
    private AccountWebServiceManagerImpl accountWebServiceManager;

    @InjectMocks
    private DeleteAccountWebService deleteAccountWebService;

    private static final String LANGUAGE_CODE = "en-US";

    @Test
    public void testDeleteAccountWSSuccessResponse() {

        Mockito.doNothing().when(accountWebServiceManager).deleteAccount(Mockito.anyLong());

        Response response = deleteAccountWebService.deleteAccount(1001L, LANGUAGE_CODE);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void testDeleteAccountWSApplicationException() {

        Mockito.doThrow(new SimpleMoneyTransferApplicationException(Errors.ACCOUNT_NUMBER_NOT_FOUND_ERR))
                .when(accountWebServiceManager).deleteAccount(Mockito.anyLong());

        Response response = deleteAccountWebService.deleteAccount(1001L, LANGUAGE_CODE);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }
}
