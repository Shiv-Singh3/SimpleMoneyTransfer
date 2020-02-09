package simpleMoneyTransfer.webServices.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TransferDTO {

    private Long sourceAccountNumber;

    private Long destinationAccountNumber;

    private Double amount;
}
