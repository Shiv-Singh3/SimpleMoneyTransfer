package simpleMoneyTransfer.injector;

import com.google.inject.AbstractModule;
import simpleMoneyTransfer.webServices.api.accountWS.CreateAccountWebService;

public class CreateAccountWebServiceModule extends AbstractModule{
    @Override
    protected void configure() {
        bind(CreateAccountWebService.class);
    }
}
