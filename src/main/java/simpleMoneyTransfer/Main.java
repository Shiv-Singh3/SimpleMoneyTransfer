package simpleMoneyTransfer;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

public class Main {

    static final String APPLICATION_PATH = "/api";
    static final String CONTEXT_ROOT = "/";

    public Main() {
    }

    public static void main(String[] args) throws Exception {
        try {
            new Main().run();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void run() throws Exception {

        System.getProperty("java.classpath");

        final int port = 8080;
        final Server server = new Server(port);
        final ServletContextHandler context = new ServletContextHandler(server, CONTEXT_ROOT);
        final ServletHolder restEasyServlet = new ServletHolder(new HttpServletDispatcher());
        restEasyServlet.setInitParameter("resteasy.servlet.mapping.prefix", APPLICATION_PATH);
        restEasyServlet.setInitParameter("javax.ws.rs.Application", "simpleMoneyTransfer.App");
        context.addServlet(restEasyServlet, APPLICATION_PATH + "/*");

        final ServletHolder defaultServlet = new ServletHolder(new DefaultServlet());
        context.addServlet(defaultServlet, CONTEXT_ROOT);

        server.start();
        server.join();
    }
}