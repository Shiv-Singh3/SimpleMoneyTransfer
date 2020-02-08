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
import simpleMoneyTransfer.webServices.api.accountWS.GetAccountWebService;
import simpleMoneyTransfer.webServices.dto.AccountDTO;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class GetAccountWebServiceTest {

    @Mock
    private AccountWebServiceManagerImpl accountWebServiceManagerImpl;

    @InjectMocks
    private GetAccountWebService getAccountWebService;

    private static final String LANGUAGE_CODE = "en-US";

    @Test
    public void testGetAccountWSSuccessResponse() {

        AccountDTO accountDTO = AccountDTO.builder().name("shiv").accountNumber(1001)
                .balance(100.00).emailId("shivendra.singh3333@gmail.com").build();

        Mockito.when((accountWebServiceManagerImpl).getAccount(Mockito.anyInt())).thenReturn(accountDTO);

        Response response = getAccountWebService.getAccount(1001, LANGUAGE_CODE);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetAccountWSApplicationException() {

        Mockito.doThrow(new SimpleMoneyTransferApplicationException(Errors.ACCOUNT_NUMBER_NOT_FOUND_ERR))
                .when(accountWebServiceManagerImpl).getAccount(Mockito.anyInt());

        Response response = getAccountWebService.getAccount(1001, LANGUAGE_CODE);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }
}
