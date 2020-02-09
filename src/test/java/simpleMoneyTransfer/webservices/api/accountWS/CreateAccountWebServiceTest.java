package simpleMoneyTransfer.webservices.api.accountWS;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import simpleMoneyTransfer.manager.impl.AccountWebServiceManagerImpl;
import simpleMoneyTransfer.parser.CreateAccountJsonParser;
import simpleMoneyTransfer.webServices.api.accountWS.CreateAccountWebService;
import simpleMoneyTransfer.webServices.dto.AccountDTO;
import javax.ws.rs.core.Response;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CreateAccountWebServiceTest {

    @Mock
    private AccountWebServiceManagerImpl accountWebServiceManagerImpl;

    @Mock
    private CreateAccountJsonParser createAccountJsonParser;

    @InjectMocks
    CreateAccountWebService webService;

    private static final String LANGUAGE_CODE = "en-US";

    @Test
    public void testCreateAccountWSSuccessResponse() {

        AccountDTO accountDTO = AccountDTO.builder().name("shiv").accountNumber(1001L)
                .balance(100.00).emailId("shivendra.singh3333@gmail.com").build();

        Mockito.when((createAccountJsonParser).parseAccountJson(Mockito.anyString())).thenReturn(accountDTO);
        Mockito.doNothing().when(accountWebServiceManagerImpl).createAccount(Mockito.any());

        Response response = webService.createAccount(getValidTestInput(), LANGUAGE_CODE);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void testCreateAccountWSValidationException() {

        Mockito.when((createAccountJsonParser).parseAccountJson(Mockito.anyString())).thenCallRealMethod();
        Mockito.doNothing().when(accountWebServiceManagerImpl).createAccount(Mockito.any());

        Response response = webService.createAccount(getInvalidTestInput(), LANGUAGE_CODE);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    String getValidTestInput() {
        return "{\n" +
                "\t\"name\": \"shiv\",\n" +
                "\t\"accountNumber\": 1001,\n" +
                "\t\"balance\": 100.00,\n" +
                "\t\"emailId\": \"shivendra.singh3333@gmail.com\"\n" +
                "}";
    }

    String getInvalidTestInput() {
        return "{\n" +
                "\t\"name\": \"shiv\",\n" +
                "\t\"accountNumber\": 1001\n" +
                "\t\"balance\": 100.00,\n" +
                "\t\"emailId\": \"shivendra.singh3333@gmail.com\"\n" +
                "}";
    }
}
