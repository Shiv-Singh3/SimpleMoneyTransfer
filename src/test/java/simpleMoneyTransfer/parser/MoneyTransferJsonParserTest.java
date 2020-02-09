package simpleMoneyTransfer.parser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import simpleMoneyTransfer.webServices.dto.TransferDTO;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class MoneyTransferJsonParserTest {

    @InjectMocks
    private MoneyTransferJsonParser parser;

    @Test
    public void testMoneyTransferJsonParserSuccess() {
        TransferDTO transferDTO = parser.parseTransferJson(getValidTransferString());
        assertEquals((Integer)1001, transferDTO.getSourceAccountNumber());
    }

    String getValidTransferString() {
        return "{\n" +
                "\t\"sourceAccountNumber\": 1001,\n" +
                "\t\"destinationAccountNumber\": 1002,\n" +
                "\t\"amount\": 200.00\n" +
                "}";
    }
}
