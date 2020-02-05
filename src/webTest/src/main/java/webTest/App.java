package webTest;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import webTest.servlets.MainServlet;
import webTest.servlets.SecondServlet;

/**
 * Base Test Web Server.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0 (2020-02-05 - 2020-02-05)
 */
public class App {
    /**
     * Start web server.
     */
    private void start() {
        try {
            // create server
            Server server = new Server(8080);

            // create servlet handler
            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

            // set server requests handler
            server.setHandler(context);

            // attach main servlet handler
            context.addServlet(new ServletHolder(new MainServlet()), "/");
            // attach second servlet handler
            context.addServlet(new ServletHolder(new SecondServlet()), "/second");

            // start server
            server.start();
        } catch (Exception e) {
            System.out.println("ERROR");
        }
    }

    /**
     * Execute class.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        // start app
        new App().start();
    }
}
