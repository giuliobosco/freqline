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
import ch.giuliobosco.freqline.help.validators.StringValidator;
import ch.giuliobosco.freqline.jdbc.JapiConnector;
import ch.giuliobosco.freqline.jdbc.JdbcConnector;
import ch.giuliobosco.freqline.queries.GeneratorStatusQuery;
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
 * Change generator status API.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0 (2019-11-19 - 2019-11-19)
 */
@WebServlet(name = "GeneratorStatusServlet", urlPatterns = {"action/generatorStatus"}, loadOnStartup = 1)
public class GeneratorStatusServlet extends BaseServlet {
    // ------------------------------------------------------------------------------------ Costants

    /**
     * Change generator status permission.
     */
    private final String CHANGE_GENERATOR_STATUS_PERM = "user";

    /**
     * Status string.
     */
    private final String STATUS = "status";

    /**
     * Required post parameters.
     */
    private final String[] REQUIRED_POST_PARAMETERS = {STATUS};

    // ---------------------------------------------------------------------------------- Attributes
    // -------------------------------------------------------------------------------- Constructors
    // --------------------------------------------------------------------------- Getters & Setters
    // -------------------------------------------------------------------------------- Help Methods
    // ----------------------------------------------------------------------------- General Methods

    /**
     * Do post request, change generator status.
     *
     * @param request  Http request.
     * @param response Http response.
     * @throws ServletException Error in servlet.
     * @throws IOException      I/O Error.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionManager sm = new SessionManager(request.getSession());
        try {
            JdbcConnector connector = new JapiConnector();
            connector.openConnection();

            String[] perms = PermissionsUserQuery.getPermissions(connector, sm.getUserId());

            boolean hasPermission = ArrayHelper.isInArray(perms, CHANGE_GENERATOR_STATUS_PERM);

            if (sm.isValidSession() && hasPermission) {

                ServletRequestAnalyser sra = new ServletRequestAnalyser(
                        REQUIRED_POST_PARAMETERS,
                        request.getParameterMap(),
                        new StringValidator()
                );

                switch (sra.getStatus()) {
                    case ServletRequestAnalyser.NOT_VALID_PARAMETERS:
                        notValidParameters(response, sra.getNotValidParameters());
                        break;
                    case ServletRequestAnalyser.MISSING_PARAMETERS:
                        missingParameters(response, sra.getMissingParameters());
                        break;
                    case ServletRequestAnalyser.OK:
                        executePost(response, connector.getConnection(), sm.getUserId(), sra);
                        break;
                    default:
                        notAcceptable(request, response);
                        break;
                }
            } else {
                unauthorized(request, response);
            }
        } catch (Exception e) {
            internalServerError(response, e.getMessage());
        }
    }

    /**
     * Execute post request.
     *
     * @param response   Post request.
     * @param connection Connection to MySQL.
     * @param userId     User id of the request.
     * @param sra        Servlet request analyser.
     * @throws SQLException Error with MySQL.
     * @throws IOException  Error while sending request to Generator.
     */
    private void executePost(HttpServletResponse response, Connection connection, int userId, ServletRequestAnalyser sra) throws SQLException, IOException {
        String keyC = GeneratorStatusQuery.getKeyByUserId(connection, userId);

        if (keyC == null) {
            ok(response, "NO ACTION");
            return;
        }

        String status = sra.getParameter(STATUS);

        if (status.equals("1")) {
            AccGenerator.turnGeneratorOn(connection, keyC);
        } else if (status.equals("0")) {
            AccGenerator.turnGeneratorOff(connection, keyC);
        }

        ok(response, "");
    }

    /**
     * Get the path of the servlet.
     *
     * @return Path of the servlet.
     */
    @Override
    protected String getPath() {
        return "action/generatorStatus";
    }
}
