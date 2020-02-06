package simpleMoneyTransfer.manager.spi;

import simpleMoneyTransfer.webServices.dto.AccountDTO;

public interface MoneyTransfer {

    AccountDTO withdraw(AccountDTO account, Double amount);

    AccountDTO credit(AccountDTO account, Double amount);
}
