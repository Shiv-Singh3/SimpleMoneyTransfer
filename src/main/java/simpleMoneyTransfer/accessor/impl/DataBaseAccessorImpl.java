package simpleMoneyTransfer.accessor.impl;

import simpleMoneyTransfer.accessor.spi.DataBaseAccessor;
import simpleMoneyTransfer.webServices.dto.AccountDTO;
import java.util.concurrent.ConcurrentHashMap;

public class DataBaseAccessorImpl implements DataBaseAccessor{

    private static ConcurrentHashMap<Long, AccountDTO> db = new ConcurrentHashMap<>();

    @Override
    public void save(Long key, AccountDTO value) {
        db.put(key, value);
    }

    @Override
    public AccountDTO get(Long key) {
        return db.get(key);
    }

    @Override
    public void remove(Long key) {
        db.remove(key);
    }

    @Override
    public boolean hasKey(Long key) {
        return db.containsKey(key);
    }
}
