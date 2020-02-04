package simpleMoneyTransfer;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;

public class App extends Application {

    public App() {
    }

    @Override
    public Set<Object> getSingletons() {
        HashSet<Object> set = new HashSet<>();
        set.add(new MessageResource());
        return set;
    }
}