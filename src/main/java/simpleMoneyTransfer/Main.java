package simpleMoneyTransfer;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Slf4jLog;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;
import simpleMoneyTransfer.guice.EventListenerScanner;
import simpleMoneyTransfer.guice.HandlerScanner;
import simpleMoneyTransfer.jetty.JettyModule;
import simpleMoneyTransfer.resource.ResourceModule;
import simpleMoneyTransfer.restEasy.RestEasyModule;
import simpleMoneyTransfer.swagger.SwaggerModule;

import javax.inject.Inject;

public class Main {

    static final String APPLICATION_PATH = "/api";
    static final String CONTEXT_ROOT = "/";

    private final GuiceFilter filter;
    private final EventListenerScanner eventListenerScanner;
    private final HandlerScanner handlerScanner;

    @Inject
    public Main(GuiceFilter filter, EventListenerScanner eventListenerScanner, HandlerScanner handlerScanner) {
        this.filter = filter;
        this.eventListenerScanner = eventListenerScanner;
        this.handlerScanner = handlerScanner;
    }

    public static void main(String[] args) throws Exception {
        try {
            Log.setLog(new Slf4jLog());
            final Injector injector = Guice.createInjector(new JettyModule(),
                    new RestEasyModule(APPLICATION_PATH),
                    new ResourceModule(), new SwaggerModule(APPLICATION_PATH));

            injector.getInstance(Main.class).run();

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void run() throws Exception {

        final int port = 8080;
        final Server server = new Server(port);

        final ServletContextHandler context = new ServletContextHandler(server, CONTEXT_ROOT);

        FilterHolder filterHolder = new FilterHolder(filter);
        context.addFilter(filterHolder, APPLICATION_PATH + "/*", null);

        final ServletHolder defaultServlet = new ServletHolder(new DefaultServlet());
        context.addServlet(defaultServlet, CONTEXT_ROOT);

        String resourceBasePath = Main.class.getResource("/swagger-ui").toExternalForm();
        context.setResourceBase(resourceBasePath);
        context.setWelcomeFiles(new String[] { "index.html" });

        eventListenerScanner.accept((listener) -> {
            context.addEventListener(listener);
        });

        final HandlerCollection handlers = new HandlerCollection();
        handlers.addHandler(server.getHandler());

        handlerScanner.accept((handler) -> {
            handlers.addHandler(handler);
        });

        server.setHandler(handlers);
        server.start();
        server.join();
    }
}