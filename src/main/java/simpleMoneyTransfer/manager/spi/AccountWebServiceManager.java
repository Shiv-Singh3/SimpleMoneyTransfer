package simpleMoneyTransfer.manager.spi;

import simpleMoneyTransfer.webServices.dto.AccountDTO;

public interface AccountWebServiceManager {

    void createAccount(AccountDTO accountDTO);

    void updateAccount(AccountDTO accountDTO);

    AccountDTO getAccount(Integer accountNumber);

    void deleteAccount(Integer accountNumber);
}
