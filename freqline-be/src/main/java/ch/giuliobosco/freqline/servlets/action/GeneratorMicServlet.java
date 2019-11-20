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
import ch.giuliobosco.freqline.help.ArrayHelper;
import ch.giuliobosco.freqline.help.validators.IntValidator;
import ch.giuliobosco.freqline.jdbc.JapiConnector;
import ch.giuliobosco.freqline.jdbc.JdbcConnector;
import ch.giuliobosco.freqline.queries.GeneratorQuery;
import ch.giuliobosco.freqline.queries.PermissionsUserQuery;
import ch.giuliobosco.freqline.servlets.BaseServlet;
import ch.giuliobosco.freqline.servlets.help.ServletRequestAnalyser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Change generator mic API.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0.1 (2019-11-19 - 2019-11-20)
 */
@WebServlet(name = "GeneratorMicServlet", urlPatterns = {"action/generatorMicTimer"}, loadOnStartup = 1)
public class GeneratorMicServlet extends BaseServlet {
    // ------------------------------------------------------------------------------------ Costants

    /**
     * Set generator mic permission.
     */
    private final String SET_GENERATOR_MIC_PERM = "user";

    /**
     * Get generator mic permission.
     */
    private final String GET_GENERATOR_MIC_PERM = "user";

    /**
     * Mic string.
     */
    private final String TIMER = "timer";

    /**
     * Required post parameters.
     */
    private final String[] REQUIRED_POST_PARAMETERS = {TIMER};

    // -------------------------------------------------------------------------------- Help Methods

    /**
     * Execute post request.
     *
     * @param response   Post request.
     * @param connection Connection to MySQL.
     * @param userId     User id of the request.
     * @param sra        Servlet request analyser.
     * @throws SQLException Error with MySQL.
     */
    private void executePost(HttpServletResponse response, Connection connection, int userId, ServletRequestAnalyser sra) throws SQLException {
        String timerString = sra.getParameter(TIMER);

        long timer = Long.parseLong(timerString);

        GeneratorQuery.setMicTimer(connection, userId, timer);
    }

    // ----------------------------------------------------------------------------- General Methods

    /**
     * Do post request, set generator frequence.
     *
     * @param req  Http request.
     * @param resp Http response.
     * @throws ServletException Error in servlet.
     * @throws IOException      I/O Error.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionManager sm = new SessionManager(req.getSession());
        try {
            JdbcConnector connector = new JapiConnector();
            connector.openConnection();

            String[] perms = PermissionsUserQuery.getPermissions(connector, sm.getUserId());

            boolean hasPermission = ArrayHelper.isInArray(perms, SET_GENERATOR_MIC_PERM);

            if (sm.isValidSession() && hasPermission) {

                ServletRequestAnalyser sra = new ServletRequestAnalyser(
                        REQUIRED_POST_PARAMETERS,
                        req.getParameterMap(),
                        new IntValidator()
                );

                switch (sra.getStatus()) {
                    case ServletRequestAnalyser.NOT_VALID_PARAMETERS:
                        notValidParameters(resp, sra.getNotValidParameters());
                        break;
                    case ServletRequestAnalyser.MISSING_PARAMETERS:
                        missingParameters(resp, sra.getMissingParameters());
                        break;
                    case ServletRequestAnalyser.OK:
                        executePost(resp, connector.getConnection(), sm.getUserId(), sra);
                        break;
                    default:
                        notAcceptable(req, resp);
                        break;
                }
            } else {
                unauthorized(req, resp);
            }
        } catch (Exception e) {
            internalServerError(resp, e.getMessage());
        }
    }

    /**
     * Do post request, get generator mic timer.
     *
     * @param req  Http request.
     * @param resp Http response.
     * @throws ServletException Error in servlet.
     * @throws IOException      I/O Error.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionManager sm = new SessionManager(req.getSession());
        try {
            JdbcConnector connector = new JapiConnector();
            connector.openConnection();

            String[] perms = PermissionsUserQuery.getPermissions(connector, sm.getUserId());

            boolean hasPermission = ArrayHelper.isInArray(perms, GET_GENERATOR_MIC_PERM);

            if (sm.isValidSession() && hasPermission) {
                String keyC = GeneratorQuery.getKeyByUserId(connector.getConnection(), sm.getUserId());
                long mic = GeneratorQuery.getMicTimer(connector.getConnection(), keyC);

                ok(resp, String.valueOf(mic));
            } else {
                unauthorized(req, resp);
            }
        } catch (Exception e) {
            internalServerError(resp, e.getMessage());
        }
    }

    /**
     * Get the path of the servlet.
     *
     * @return Path of the servlet.
     */
    @Override
    protected String getPath() {
        return "action/generatorMicTimer";
    }
}
