package simpleMoneyTransfer.restEasy;

import java.util.Map;

import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;

public class RestEasyModule extends ServletModule {

    private final String path;

    public RestEasyModule() {
        this.path = null;
    }

    public RestEasyModule(final String path) {
//        Preconditions.checkArgument(path.length() == 0 || path.startsWith("/"), "Path must begin with '/'");
//        Preconditions.checkArgument(!path.endsWith("/"), "Path must not have a trailing '/'");
        this.path = path;  // e.g., "/api"
    }

    @Override
    protected void configureServlets() {

        bind(GuiceResteasyBootstrapServletContextListener.class);
        bind(HttpServletDispatcher.class).in(Singleton.class);

        if (path == null) {
            serve("/*").with(HttpServletDispatcher.class);
        } else {
            final Map<String, String> initParams = ImmutableMap.of("resteasy.servlet.mapping.prefix", path);
            serve(path + "/*").with(HttpServletDispatcher.class, initParams);
        }
    }
}

