package simpleMoneyTransfer.injector;

import com.google.inject.AbstractModule;
import simpleMoneyTransfer.webServices.api.accountWS.UpdateAccountWebService;

public class UpdateAccountWebServiceModule extends AbstractModule {

    @Override
    protected void configure() {
       bind(UpdateAccountWebService.class);
    }
}
