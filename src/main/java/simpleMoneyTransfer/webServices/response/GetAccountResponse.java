package simpleMoneyTransfer.webServices.response;

import lombok.Getter;
import lombok.Setter;
import simpleMoneyTransfer.webServices.dto.AccountDTO;

@Getter
@Setter
public class GetAccountResponse extends BaseResponse{

    private AccountDTO accountDTO;

    private static final Document DOCTYPE =
            new Document("instance", "simpleMoneyTransfer", "getAccount", "1.0");

    public GetAccountResponse() {}

    public GetAccountResponse(AccountDTO accountDTO) {
        super(DOCTYPE);
        this.accountDTO = accountDTO;
    }
}
