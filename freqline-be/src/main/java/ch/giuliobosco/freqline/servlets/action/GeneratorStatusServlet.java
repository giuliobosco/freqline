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
import ch.giuliobosco.freqline.help.validators.StringValidator;
import ch.giuliobosco.freqline.jdbc.JapiConnector;
import ch.giuliobosco.freqline.jdbc.JdbcConnector;
import ch.giuliobosco.freqline.queries.AccCheckQueries;
import ch.giuliobosco.freqline.queries.GeneratorStatusQuery;
import ch.giuliobosco.freqline.servlets.BaseServlet;
import ch.giuliobosco.freqline.servlets.help.ServletRequestAnalyser;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Generator ACC requests.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0 (2019-11-15 - 2019-11-15)
 */
@WebServlet(name = "GeneratorStatusServlet", urlPatterns = {"action/generatorStatus"}, loadOnStartup = 1)
public class GeneratorStatusServlet extends BaseServlet {

    /**
     * Keyc string.
     */
    private final String KEY_C = "key_c";

    /**
     * Action string.
     */
    private final String ACTION = "action";

    /**
     * Content string.
     */
    private final String CONTENT = "content";

    /**
     * Mic string
     */
    private final String ACTION_MIC = "mic";

    /**
     * Remote string.
     */
    private final String ACTION_REMOTE = "remote";

    /**
     * T string.
     */
    private final String ACTION_REMOTE_TOGGLE = "t";

    /**
     * 1 string
     */
    private final String ACTION_REMOTE_ON = "1";

    /**
     * 0 string.
     */
    private final String ACTION_REMOTE_OFF = "0";

    /**
     * Conf string.
     */
    private final String ACTION_CONF = "conf";

    /**
     * Required parameters for post request.
     */
    private final String[] POST_REQUIRED_PARAMETERS = {KEY_C, ACTION, CONTENT};

    /**
     * Connection to mysql.
     */
    private Connection connection;

    /**
     * Servlet Request Analyser.
     */
    private ServletRequestAnalyser sra;

    /**
     * Get the connection to MySQL database.
     * If not initialized throws runtime exception.
     *
     * @return Connection to MySQL database.
     */
    private Connection getConnection() {
        if (this.connection == null) {
            throw new RuntimeException("Connection to MySQL not initialized.");
        }

        return this.connection;
    }

    /**
     * Get the Servlet Request Analyser.
     * If not initialized throws runtime exception.
     *
     * @return Servlet Request Analyser.
     */
    private ServletRequestAnalyser getSra() {
        if (this.sra == null) {
            throw new RuntimeException("ServletRequestAnalyser Not initialized");
        }

        return this.sra;
    }

    /**
     * Get the key of communication.
     *
     * @return Key of communication.
     */
    private String getKeyC() {
        return getSra().getParameter(KEY_C);
    }

    /**
     * Get the action.
     *
     * @return Action.
     */
    private String getAction() {
        return getSra().getParameter(ACTION);
    }

    /**
     * Get the content.
     *
     * @return Content.
     */
    private String getContent() {
        return getSra().getParameter(CONTENT);
    }

    /**
     * Do post, execute generator status request.
     *
     * @param request  Http request.
     * @param response Http response.
     * @throws ServletException Error in the servlet.
     * @throws IOException      I/O Error.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            JdbcConnector connector = new JapiConnector();
            connector.openConnection();
            this.connection = connector.getConnection();

            this.sra = new ServletRequestAnalyser(
                    POST_REQUIRED_PARAMETERS,
                    request.getParameterMap(),
                    new StringValidator()
            );

            switch (getSra().getStatus()) {
                case ServletRequestAnalyser.NOT_VALID_PARAMETERS:
                    notValidParameters(response, sra.getNotValidParameters());
                    break;
                case ServletRequestAnalyser.MISSING_PARAMETERS:
                    missingParameters(response, sra.getNotValidParameters());
                    break;
                case ServletRequestAnalyser.OK:
                    if (isValidKey()) {
                        executePost(response);
                    }
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
     * Execute post request.
     *
     * @param response Http response.
     * @throws SQLException SQL Error.
     * @throws IOException  I/O Error.
     */
    private void executePost(HttpServletResponse response) throws SQLException, IOException {
        String action = getAction();

        if (action.equals(ACTION_CONF)) {
            conf(response);
        } else if (action.equals(ACTION_MIC)) {
            mic(response);
        } else if (action.equals(ACTION_REMOTE)) {
            remote(response);
        }
    }

    /**
     * Execute conf action.
     *
     * @param response Http response.
     * @throws SQLException SQL Error.
     * @throws IOException  I/O Error.
     */
    private void conf(HttpServletResponse response) throws SQLException, IOException {
        JSONObject jo = AccCheckQueries.getJsonConf(getConnection(), getKeyC());

        ok(response, jo);
    }

    /**
     * Execute mic action.
     *
     * @param response Http response.
     * @throws SQLException SQL Error.
     * @throws IOException  I/O Error.
     */
    private void mic(HttpServletResponse response) throws SQLException, IOException {
        boolean status = GeneratorStatusQuery.getGeneratorStatus(getConnection(), getKeyC());

        if (status) {
            AccGenerator.turnGeneratorOff(getConnection(), getKeyC());
        } else {
            long timer = GeneratorStatusQuery.getMicTimer(getConnection(), getKeyC());
            AccGenerator.turnGeneratorOn(getConnection(), getKeyC(), timer);
        }
    }

    /**
     * Execute remote action.
     *
     * @param response Http response.
     * @throws SQLException SQL Error.
     * @throws IOException  I/O Error.
     */
    private void remote(HttpServletResponse response) throws SQLException, IOException {
        if (getContent().equals(ACTION_REMOTE_TOGGLE)) {
            remoteToggle(response);
        } else if (getContent().equals(ACTION_REMOTE_ON)) {
            AccGenerator.turnGeneratorOn(getConnection(), getKeyC());
        } else if (getContent().equals(ACTION_REMOTE_OFF)) {
            AccGenerator.turnGeneratorOff(getConnection(), getKeyC());
        }
    }

    /**
     * Execute toggle remote action.
     *
     * @param response Http response.
     * @throws SQLException SQL Error.
     * @throws IOException  I/O Error.
     */
    private void remoteToggle(HttpServletResponse response) throws SQLException, IOException {
        boolean status = GeneratorStatusQuery.getGeneratorStatus(getConnection(), getKeyC());

        if (status) {
            AccGenerator.turnGeneratorOff(getConnection(), getKeyC());
        } else {
            AccGenerator.turnGeneratorOn(getConnection(), getKeyC());
        }
    }

    /**
     * Check if the key is valid.
     *
     * @return True if key is valid.
     * @throws SQLException Error with MySQL.
     */
    private boolean isValidKey() throws SQLException {
        String key = getKeyC();

        return AccCheckQueries.isValidGeneratorKey(getConnection(), key);
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
