package simpleMoneyTransfer.parser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import simpleMoneyTransfer.webServices.dto.UpdateDTO;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class UpdateAccountJsonParserTest {

    @InjectMocks
    private UpdateAccountJsonParser updateAccountJsonParser;

    @Test
    public void testUpdateAccountJsonParserSuccess() {

        UpdateDTO updateDTO = updateAccountJsonParser.parseUpdateJson(getValidUpdateAccountString(), 1001L);
        assertEquals((Long) 1001L, updateDTO.getAccountNumber());
    }

    String getValidUpdateAccountString() {
        return "{\n" +
                "\t\"emailId\": \"xxx\",\n" +
                "\t\"mobileNo\": \"1111\"\n" +
                "}";
    }
}
