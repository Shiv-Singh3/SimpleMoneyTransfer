package simpleMoneyTransfer.manager.impl;

import simpleMoneyTransfer.manager.spi.MoneyTransfer;
import simpleMoneyTransfer.webServices.dto.AccountDTO;

public class MoneyTransferImpl implements MoneyTransfer{

    @Override
    public AccountDTO withdraw(AccountDTO account, Double amount) {
        Double currentBalance = account.getBalance();
        Double newBalance = currentBalance - amount;
        account.setBalance(newBalance);
        return account;
    }

    @Override
    public AccountDTO credit(AccountDTO account, Double amount) {
        Double currentBalance = account.getBalance();
        Double newBalance = currentBalance + amount;
        account.setBalance(newBalance);
        return account;
    }
}
