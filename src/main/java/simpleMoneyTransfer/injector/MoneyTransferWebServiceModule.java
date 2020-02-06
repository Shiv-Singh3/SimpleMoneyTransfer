package simpleMoneyTransfer.injector;

import com.google.inject.AbstractModule;
import simpleMoneyTransfer.webServices.api.transferWS.MoneyTransferWebService;

public class MoneyTransferWebServiceModule extends AbstractModule{

    @Override
    protected void configure() {
       bind(MoneyTransferWebService.class);
    }
}
