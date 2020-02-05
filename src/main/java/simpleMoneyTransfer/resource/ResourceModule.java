package simpleMoneyTransfer.resource;

import com.google.inject.AbstractModule;
import simpleMoneyTransfer.MessageResource;

public class ResourceModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(MessageResource.class);
    }
}
