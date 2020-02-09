package simpleMoneyTransfer.accessor.spi;

import simpleMoneyTransfer.webServices.dto.AccountDTO;

public interface DataBaseAccessor {

    void save(Long key, AccountDTO value);

    AccountDTO get(Long key);

    void remove(Long key);

    boolean hasKey(Long key);
}
