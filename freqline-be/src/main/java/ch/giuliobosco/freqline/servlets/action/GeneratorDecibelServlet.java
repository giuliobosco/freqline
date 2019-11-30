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

import ch.giuliobosco.freqline.acc.AccGenerator;
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
 * Generator decibel servlet.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0 (2019-11-30 - 2019-11-30)
 */
@WebServlet(name = "GeneratorDecibelServlet", urlPatterns = {"action/generatorDecibel"}, loadOnStartup = 1)
public class GeneratorDecibelServlet extends BaseServlet {
    // ------------------------------------------------------------------------------------ Costants

    /**
     * Set Generator decibel permission.
     */
    private final String SET_GENERATOR_DECIBEL_PERM = "user";

    /**
     * Get generator decibel permission.
     */
    private final String GET_GENERATOR_DECIBEL_PERM = "user";

    /**
     * Decibel string.
     */
    private final String DECIBEL = "decibel";

    /**
     * Required post parameters.
     */
    private final String[] REQUIRED_POST_PARAMETERS = {DECIBEL};

    // -------------------------------------------------------------------------------- Help Methods

    /**
     * Execute post request.
     *
     * @param response   Http response.
     * @param connection Connection to MySQL database.
     * @param userId     User id.
     * @param sra        Servlet request analyser.
     * @throws SQLException Error with MySQL server.
     * @throws IOException  I/O Exception.
     */
    private void executePost(HttpServletResponse response, Connection connection, int userId, ServletRequestAnalyser sra) throws SQLException, IOException {
        String decibelString = sra.getParameter(DECIBEL);

        int decibel = Integer.parseInt(decibelString);

        AccGenerator.updateDecibel(connection, userId, decibel);
        ok(response, decibelString);
    }

    // ----------------------------------------------------------------------------- General Methods

    /**
     * Do Post request, check auth.
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

            boolean hasPermission = ArrayHelper.isInArray(perms, SET_GENERATOR_DECIBEL_PERM);

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
     * Do get, check auth.
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

            boolean hasPermission = ArrayHelper.isInArray(perms, GET_GENERATOR_DECIBEL_PERM);

            if (sm.isValidSession() && hasPermission) {
                int timer = GeneratorQuery.getDecibel(connector.getConnection(), sm.getUserId());

                ok(resp, String.valueOf(timer));
            } else {
                unauthorized(req, resp);
            }
        } catch (Exception e) {
            internalServerError(resp, e.getMessage());
        }
    }

    /**
     * Get the servlet path.
     *
     * @return Servlet path.
     */
    @Override
    protected String getPath() {
        return "action/generatorDecibel";
    }

    // --------------------------------------------------------------------------- Static Components

}
