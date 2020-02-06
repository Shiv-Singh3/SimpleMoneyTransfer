package simpleMoneyTransfer.injector;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import simpleMoneyTransfer.manager.AccountWebServiceManager;
import simpleMoneyTransfer.webServices.AccountWebServices;

import javax.inject.Named;

public class AccountWebServicesModule extends AbstractModule{
    @Override
    protected void configure() {
        bind(AccountWebServices.class);
    }

    @Provides
    @Singleton
    @Named("accountWebServiceManager")
    public AccountWebServiceManager provideAccountWebServiceManager(AccountWebServiceManager accountWebServiceManager) {
        return accountWebServiceManager;
    }
}
