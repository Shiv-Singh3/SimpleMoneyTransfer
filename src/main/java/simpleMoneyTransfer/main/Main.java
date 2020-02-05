package simpleMoneyTransfer.main;

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
import simpleMoneyTransfer.constants.ConfigConstants;
import simpleMoneyTransfer.main.guice.EventListenerScanner;
import simpleMoneyTransfer.main.guice.HandlerScanner;
import simpleMoneyTransfer.main.jetty.JettyModule;
import simpleMoneyTransfer.main.resource.ResourceModule;
import simpleMoneyTransfer.main.restEasy.RestEasyModule;
import simpleMoneyTransfer.main.swagger.SwaggerModule;
import javax.inject.Inject;

public class Main {

    private static final String APPLICATION_PATH = ConfigConstants.APPLICATION_PATH;
    private static final String CONTEXT_ROOT = ConfigConstants.CONTEXT_ROOT;

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

    private void run() throws Exception {

        final int port = ConfigConstants.PORT;
        final Server server = new Server(port);

        final ServletContextHandler context = new ServletContextHandler(server, CONTEXT_ROOT);

        FilterHolder filterHolder = new FilterHolder(filter);
        context.addFilter(filterHolder, APPLICATION_PATH + ConfigConstants.URL_PATTERN, null);

        final ServletHolder defaultServlet = new ServletHolder(new DefaultServlet());
        context.addServlet(defaultServlet, CONTEXT_ROOT);

        String resourceBasePath = Main.class.getResource("/swagger-ui").toExternalForm();
        context.setResourceBase(resourceBasePath);
        context.setWelcomeFiles(new String[] { "index.html" });

        eventListenerScanner.accept(context::addEventListener);

        final HandlerCollection handlers = new HandlerCollection();
        handlers.addHandler(server.getHandler());

        handlerScanner.accept(handlers::addHandler);

        server.setHandler(handlers);
        server.start();
        server.join();
    }
}