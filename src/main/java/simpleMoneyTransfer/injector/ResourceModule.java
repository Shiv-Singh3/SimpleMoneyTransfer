package simpleMoneyTransfer.injector;

import com.google.inject.AbstractModule;
import simpleMoneyTransfer.webServices.MessageResource;

public class ResourceModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(MessageResource.class);
    }
}
