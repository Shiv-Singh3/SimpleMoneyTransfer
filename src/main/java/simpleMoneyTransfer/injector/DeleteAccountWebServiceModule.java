package simpleMoneyTransfer.injector;

import com.google.inject.AbstractModule;
import simpleMoneyTransfer.webServices.api.accountWS.DeleteAccountWebService;

public class DeleteAccountWebServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DeleteAccountWebService.class);
    }
}
