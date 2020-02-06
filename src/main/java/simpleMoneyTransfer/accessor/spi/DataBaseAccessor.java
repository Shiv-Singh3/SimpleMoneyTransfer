package simpleMoneyTransfer.accessor.spi;

import simpleMoneyTransfer.webServices.dto.AccountDTO;

public interface DataBaseAccessor {

    void save(Integer key, AccountDTO value);

    AccountDTO get(Integer key);
}
