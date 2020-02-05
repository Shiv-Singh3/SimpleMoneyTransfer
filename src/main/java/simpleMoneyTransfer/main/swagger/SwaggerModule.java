package simpleMoneyTransfer.main.swagger;

import javax.servlet.ServletContextListener;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.servlet.ServletModule;
import com.google.common.base.Preconditions;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import simpleMoneyTransfer.constants.ConfigConstants;
import simpleMoneyTransfer.utils.ConfigUtils;

public class SwaggerModule extends ServletModule {

    private final String path;

    public SwaggerModule() {
        this.path = null;
    }

    public SwaggerModule(final String path) {
        Preconditions.checkArgument(
                ConfigUtils.preConditionStartCheck(path), "Path must begin with '/'");
        Preconditions.checkArgument(
                ConfigUtils.preConditionEndCheck(path), "Path must not have a trailing '/'");
        this.path = path;
    }

    @Override
    protected void configureServlets() {

        Multibinder<ServletContextListener> multibinder = Multibinder.newSetBinder(binder(),
                ServletContextListener.class);
        multibinder.addBinding().to(SwaggerServletContextListener.class);

        bind(ApiListingResource.class);
        bind(SwaggerSerializers.class);

        if (path == null) {
            filter(ConfigConstants.URL_PATTERN).through(ApiOriginFilter.class);
        } else {
            filter(path + ConfigConstants.URL_PATTERN).through(ApiOriginFilter.class);
        }
    }
}
