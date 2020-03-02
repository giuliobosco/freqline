/*
 * The MIT License
 *
 * Copyright 2019 giuliobosco.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package ch.giuliobosco.freqline.servlets.action;

import ch.giuliobosco.freqline.auth.SessionManager;
import ch.giuliobosco.freqline.auth.SqlAuthenticator;
import ch.giuliobosco.freqline.dbdao.DbUserDao;
import ch.giuliobosco.freqline.jdbc.JapiConnector;
import ch.giuliobosco.freqline.jdbc.JdbcConnector;
import ch.giuliobosco.freqline.model.User;
import ch.giuliobosco.freqline.modeljson.UserJson;
import ch.giuliobosco.freqline.queries.PermissionsUserQuery;
import ch.giuliobosco.freqline.servlets.BaseServlet;
import ch.giuliobosco.freqline.servlets.help.ServletRequestAnalyser;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Login action servlet.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0.3 (2019-10-29 - 2020-02-03)
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"action/login/*"}, loadOnStartup = 1)
public class LoginServlet extends BaseServlet {

    /**
     * Logged in response message.
     */
    private final String LOGGED_IN = "ok";

    /**
     * Wrong username or password response message;
     */
    private final String WRONG_USERNAME_PASSWORD = "wrong username or password";

    /**
     * Do login servlet post request.
     *
     * @param request  Http request.
     * @param response Http response.
     * @throws ServletException Error in servlet.
     * @throws IOException      Input Output Error.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] requiredParameter = {UserJson.USERNAME, UserJson.PASSWORD};
        ServletRequestAnalyser sra = new ServletRequestAnalyser(requiredParameter, request.getParameterMap());

        try {
            switch (sra.getStatus()) {
                case ServletRequestAnalyser.NOT_VALID_PARAMETERS:
                    notValidParameters(response, sra.getNotValidParameters());
                    break;
                case ServletRequestAnalyser.MISSING_PARAMETERS:
                    missingParameters(response, sra.getMissingParameters());
                    break;
                case ServletRequestAnalyser.OK:
                    executePost(request, response, sra);

                    break;
                default:
                    notAcceptable(request, response);
                    break;
            }
        } catch (Exception e) {
            internalServerError(response, e.getMessage());
        }
    }

    /**
     * Check if exists old session, if exists destroy it.
     *
     * @param request Http request.
     */
    public static void checkOldSession(HttpServletRequest request) {
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }
    }

    /**
     * Execute login servlet post request.
     *
     * @param request  Http request.
     * @param response Http response.
     * @param sra      Servlet request Analyser.
     * @throws Exception Error.
     */
    private void executePost(HttpServletRequest request, HttpServletResponse response, ServletRequestAnalyser sra) throws Exception {
        JdbcConnector connector = null;

        try {
            connector = new JapiConnector();
            connector.openConnection();

            DbUserDao dao = new DbUserDao(connector);
            SqlAuthenticator sqla = new SqlAuthenticator(dao);

            String username = firstValue(sra.getParameters(), UserJson.USERNAME);
            String password = firstValue(sra.getParameters(), UserJson.PASSWORD);

            User user = sqla.authenticateUser(username, password);

            checkOldSession(request);
            SessionManager sm = new SessionManager(request.getSession(true));

            if (user != null) {
                sm.initSession(user);
                ok(response, LOGGED_IN);
            } else {
                sm.destroySession();

                unauthorized(response, WRONG_USERNAME_PASSWORD);
            }
        } catch (SQLException sqle) {
            internalServerError(response, sqle.getMessage());
        } finally {
            if (connector != null) {
                connector.close();
            }
        }
    }

    /**
     * Do get, execute http get request.
     *
     * @param req  HTTP request.
     * @param resp HTTP response.
     * @throws ServletException Error with servlet.
     * @throws IOException      I/O Exception.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JdbcConnector connector = null;

        try {
            SessionManager sm = new SessionManager(req.getSession());

            if (sm.isValidSession()) {
                int startIndex = req.getRequestURI().lastIndexOf('/') + 1;
                String requestType = req.getRequestURI().substring(startIndex);

                JSONObject jo = new JSONObject();
                jo.put("isLoggedIn", true);

                if (requestType.equals("permissions")) {
                    connector = new JapiConnector();
                    connector.openConnection();
                    String[] perms = PermissionsUserQuery.getPermissions(connector.getConnection(), sm.getUserId());

                    jo.put("permissions", perms);
                    connector.getConnection().close();
                }

                ok(resp, jo);
            } else {
                unauthorized(req, resp);
            }
        } catch (Exception e) {
            internalServerError(resp, e.getMessage());
        } finally {
            if (connector != null) {
                connector.close();
            }
        }
    }

    /**
     * Get the servlet path.
     *
     * @return Servlet path.
     */
    @Override
    public String getPath() {
        return "action/login";
    }
}
