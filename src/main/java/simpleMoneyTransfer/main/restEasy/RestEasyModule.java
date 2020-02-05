package simpleMoneyTransfer.main.restEasy;

import java.util.Map;
import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;
import simpleMoneyTransfer.constants.ConfigConstants;
import simpleMoneyTransfer.utils.ConfigUtils;

public class RestEasyModule extends ServletModule {

    private final String path;

    public RestEasyModule() {
        this.path = null;
    }

    public RestEasyModule(final String path) {
        Preconditions.checkArgument(
                ConfigUtils.preConditionStartCheck(path), "Path must begin with '/'");
        Preconditions.checkArgument(
                ConfigUtils.preConditionEndCheck(path), "Path must not have a trailing '/'");
        this.path = path;
    }

    @Override
    protected void configureServlets() {

        bind(GuiceResteasyBootstrapServletContextListener.class);
        bind(HttpServletDispatcher.class).in(Singleton.class);

        if (path == null) {
            serve(ConfigConstants.URL_PATTERN).with(HttpServletDispatcher.class);
        } else {
            final Map<String, String> initParams = ImmutableMap.of(
                    ConfigConstants.REST_EASY_SERVLET_MAPPING_PREFIX, path);
            serve(path + ConfigConstants.URL_PATTERN).with(HttpServletDispatcher.class, initParams);
        }
    }
}

