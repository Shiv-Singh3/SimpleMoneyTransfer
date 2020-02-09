package simpleMoneyTransfer.parser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import simpleMoneyTransfer.constants.Errors;
import simpleMoneyTransfer.exceptions.SimpleMoneyTransferValidationException;
import simpleMoneyTransfer.webServices.dto.AccountDTO;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CreateAccountJsonParserTest {

    @InjectMocks
    private CreateAccountJsonParser createAccountJsonParser;

    @Test
    public void testParseAccountJsonSuccess() {

        AccountDTO accountDTO = createAccountJsonParser.parseAccountJson(getValidAccountString());
        assertEquals((Long) 1002L, accountDTO.getAccountNumber());
    }

    @Test
    public void testParseAccountJsonNewAccountSuccess() {

        AccountDTO accountDTO = createAccountJsonParser.parseAccountJson(getNewAccountString());
        assertEquals((Double) 0.00, accountDTO.getBalance());
    }

    String getValidAccountString() {
        return "{\n" +
                "\t\"name\": \"raj\",\n" +
                "\t\"accountNumber\": 1002,\n" +
                "\t\"balance\": 100.00,\n" +
                "\t\"currency\": \"INR\",\n" +
                "\t\"emailId\": \"xyz.singh3333@gmail.com\",\n" +
                "\t\"mobileNo\": \"1234533\"\n" +
                "}";
    }

    String getNewAccountString() {
        return "{\n" +
                "\t\"name\": \"shiv\",\n" +
                "\t\"emailId\": \"shivendra.singh3333@gmail.com\"\n" +
                "}";
    }
}
