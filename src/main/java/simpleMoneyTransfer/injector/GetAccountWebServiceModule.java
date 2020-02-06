package simpleMoneyTransfer.injector;

import com.google.inject.AbstractModule;
import simpleMoneyTransfer.webServices.api.accountWS.GetAccountWebService;

public class GetAccountWebServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(GetAccountWebService.class);
    }
}
