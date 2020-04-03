package webTest.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet test class.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0 (2020-02-05 - 2020-02-05)
 */
public class SecondServlet extends HttpServlet {
    /**
     * Do get (just test).
     * Writes on CLI: Second servlet - Reached
     * Writes on http: Second Servlet - GET
     *
     * @param req Http request.
     * @param resp Http response.
     * @throws ServletException Servlet exception.
     * @throws IOException I/O Exception.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // write on http
        resp.getOutputStream().println("Second Servlet - GET");
        // write on CLI
        System.out.println("Second servlet - Reached");
    }
}
