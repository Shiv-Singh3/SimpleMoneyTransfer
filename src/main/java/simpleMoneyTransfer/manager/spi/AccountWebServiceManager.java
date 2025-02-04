package simpleMoneyTransfer.manager.spi;

import simpleMoneyTransfer.webServices.dto.AccountDTO;
import simpleMoneyTransfer.webServices.dto.UpdateDTO;

public interface AccountWebServiceManager {

    void createAccount(AccountDTO accountDTO);

    void updateAccount(AccountDTO accountDTO);

    void updateAccount(UpdateDTO updateDTO);

    AccountDTO getAccount(Long accountNumber);

    void deleteAccount(Long accountNumber);
}
