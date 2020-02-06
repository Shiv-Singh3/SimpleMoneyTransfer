package simpleMoneyTransfer.accessor.impl;

import simpleMoneyTransfer.accessor.spi.DataBaseAccessor;
import simpleMoneyTransfer.webServices.dto.AccountDTO;
import java.util.concurrent.ConcurrentHashMap;

public class DataBaseAccessorImpl implements DataBaseAccessor{

    private static ConcurrentHashMap<Integer, AccountDTO> db = new ConcurrentHashMap<>();

    @Override
    public void save(Integer key, AccountDTO value) {
        db.put(key, value);
    }

    @Override
    public AccountDTO get(Integer key) {
        return db.get(key);
    }

}
