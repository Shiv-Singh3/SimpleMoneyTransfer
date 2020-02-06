package simpleMoneyTransfer.injector;

import com.google.inject.AbstractModule;
import simpleMoneyTransfer.webServices.AccountWebServices;

public class AccountWebServicesModule extends AbstractModule{
    @Override
    protected void configure() {
        bind(AccountWebServices.class);
    }
}
