package simpleMoneyTransfer.webServices.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.Currency;

@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountDTO {

    private String name;

    private Long accountNumber;

    private Double balance;

    private Currency currency;

    private String emailId;

    private String mobileNo;
}
