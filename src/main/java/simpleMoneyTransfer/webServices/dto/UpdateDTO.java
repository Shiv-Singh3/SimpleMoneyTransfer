package simpleMoneyTransfer.webServices.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateDTO {

    private Long accountNumber;

    private String emailId;

    private String mobileNo;
}
