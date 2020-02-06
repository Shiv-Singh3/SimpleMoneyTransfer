package simpleMoneyTransfer.accessor;

import simpleMoneyTransfer.webServices.dto.AccountDTO;

import java.util.concurrent.ConcurrentHashMap;

public class DatabaseAccessor {

    private static ConcurrentHashMap<Integer, AccountDTO> db = new ConcurrentHashMap<>();

    public void save(Integer key, AccountDTO value) {
        db.put(key, value);
    }

    public AccountDTO get(Integer key) {
        return db.get(key);
    }

}
