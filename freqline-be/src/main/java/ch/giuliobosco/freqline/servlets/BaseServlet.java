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

package ch.giuliobosco.freqline.servlets;

import org.json.JSONObject;
import ch.giuliobosco.freqline.help.StringArrayHelper;
import ch.giuliobosco.freqline.help.StringHelper;
import ch.giuliobosco.freqline.jdbc.JdbcConnector;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0 (2019-10-27 - 2019-10-27)
 */
@WebServlet(name = "BaseServlet")
public abstract class BaseServlet extends HttpServlet {

    /**
     * Http status code string 200 ok.
     */
    public static final String S_200_OK = "200 OK";

    /**
     * Http status code string 200 created.
     */
    public static final String S_201_CREATED = "201 CREATED";

    /**
     * Http status code string 204 no content..
     */
    public static final String S_204_NO_CONTENT = "204 NO CONTENT";

    /**
     * Http status code string 304 not modified.
     */
    public static final String S_304_NOT_MODIFIED = "304 NOT MODIFIED";

    /**
     * Http status code string 400 bad request.
     */
    public static final String S_400_BAD_REQUEST = "400 BAD REQUEST";

    /**
     * Http status code string 401 unauthorized.
     */
    public static final String S_401_UNAUTHORIZED = "401 UNAUTHORIZED";

    /**
     * Http status code string 403 forbidden.
     */
    public static final String S_403_FORBIDDEN = "403 FORBIDDEN";

    /**
     * Http status code string 404 not found.
     */
    public static final String S_404_NOT_FOUND = "404 NOT FOUND";

    /**
     * Http status code string 405 not acceptable.
     */
    public static final String S_406_NOT_ACCEPTABLE = "405 NOT ACCEPTABLE";

    /**
     * Http status code string 409 conflict.
     */
    public static final String S_409_CONFLICT = "409 CONFLICT";

    /**
     * Http status code string 500 internal server error.
     */
    public static final String S_500_INTERNAL_SERVER_ERROR = "500 INTERNAL SERVER ERROR";

    /**
     * Http status code string 501 not implemented.
     */
    public static final String S_501_NOT_IMPLEMENTED = "501 NOT IMPLEMENTED";

    /**
     * Statusstring.
     */
    public static final String S_STATUS = "status";

    /**
     * Status code string.
     */
    public static final String S_STATUS_CODE = "statusCode";

    /**
     * Error string.
     */
    public static final String S_STATUS_ERROR = "ERROR";

    /**
     * Ok string.
     */
    public static final String S_STATUS_OK = "OK";

    /**
     * Not capable string.
     */
    public static final String S_STATUS_NOT_CAPABLE = "NOT CAPABLE";

    /**
     * Not valid parameters string.
     */
    public static final String S_NOT_VALID_PARAMETERS = "notValidParameters";

    /**
     * Missing parameter string.
     */
    public static final String S_MISSING_PARAMETERS = "missingParameters";

    /**
     * Path string.
     */
    public static final String S_PATH = "path";

    /**
     * Action string.
     */
    public static final String S_ACTION = "action";

    /**
     * Message string.
     */
    public static final String S_MESSAGE = "message";

    /**
     * Request URI string.
     */
    public static final String S_REQUEST_URI = "requestUri";

    /**
     * Request URL string.
     */
    public static final String S_REQUEST_URL = "requestUrl";

    /**
     * Method string.
     */
    public static final String S_METHOD = "method";

    /**
     * Request string.
     */
    public static final String S_REQUEST = "request";

    /**
     * Jdbc Connector.
     */
    private JdbcConnector connector;

    /**
     * Get the Jdbc Connector.
     *
     * @return Jdbc Connector.
     */
    protected JdbcConnector getConnector() {
        return this.connector;
    }

    /**
     * Set the Jdbc Connector.
     *
     * @param connector Jdbc Connector.
     */
    protected void setConnector(JdbcConnector connector) {
        this.connector = connector;
    }

    /**
     * Return http status code 200 ok.
     *
     * @param response Http response.
     * @param str      String to write in JSON message parameter.
     * @throws IOException Error while returning http status code.
     */
    protected void ok(HttpServletResponse response, String str) throws IOException {
        writeStatus(response, response.SC_OK, S_200_OK, str);
    }

    /**
     * Return http status code 200.
     *
     * @param response Http response.
     * @param jo       JSON to return.
     * @throws IOException Error while returning http status code.
     */
    protected void ok(HttpServletResponse response, JSONObject jo) throws IOException {
        writeStatus(response, response.SC_OK, S_200_OK, jo);
    }

    /**
     * Return http status code 201 created.
     *
     * @param response Http response.
     * @param str      String to write.
     * @throws IOException Error while returning http status code.
     */
    protected void created(HttpServletResponse response, String str) throws IOException {
        writeStatus(response, response.SC_CREATED, S_201_CREATED, str);
    }

    /**
     * Return http status code 2001 created.
     *
     * @param response Http response.
     * @param jo       JSON to return.
     * @throws IOException Error while returning http status code.
     */
    protected void created(HttpServletResponse response, JSONObject jo) throws IOException {
        writeStatus(response, response.SC_CREATED, S_201_CREATED, jo);
    }

    /**
     * Return http status code 204 no content.
     *
     * @param response Http response.
     * @throws IOException Error while returning http status code.
     */
    protected void noContent(HttpServletResponse response) throws IOException {
        writeStatus(response, response.SC_NO_CONTENT, S_204_NO_CONTENT);
    }

    /**
     * Return http status code 304 not modified.
     *
     * @param response Http response.
     * @throws IOException Error while returning http status code.
     */
    protected void notModified(HttpServletResponse response) throws IOException {
        writeStatus(response, response.SC_NOT_MODIFIED, S_304_NOT_MODIFIED);
    }

    /**
     * Return http status code 400 bad request.
     *
     * @param request  Http Request.
     * @param response Http response.
     * @throws IOException Error while returning http status code.
     */
    protected void badRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jo = requestToJson(request);

        writeStatus(response, response.SC_BAD_REQUEST, S_400_BAD_REQUEST, jo);
    }

    /**
     * Return http status code 401 unauthorized.
     *
     * @param request  Http request.
     * @param response Http response.
     * @throws IOException Error while returning http status code.
     */
    protected void unauthorized(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jo = new JSONObject();
        jo.put(S_PATH, getPath());
        jo.put(S_METHOD, request.getMethod());

        writeStatus(response, response.SC_UNAUTHORIZED, S_401_UNAUTHORIZED, jo);
    }

    /**
     * Return http status code 403 forbidden.
     *
     * @param response Http response.
     * @param action   Forbidden action.
     * @throws IOException Error while returning http status code.
     */
    protected void forbidden(HttpServletResponse response, String action) throws IOException {
        JSONObject jo = new JSONObject();
        jo.put(S_ACTION, action);

        writeStatus(response, response.SC_FORBIDDEN, S_403_FORBIDDEN, jo);
    }

    /**
     * Return http status code 404 not found.
     *
     * @param request  Http request.
     * @param response Http response.
     * @throws IOException Error while returning http status code.
     */
    protected void notFound(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jo = new JSONObject();
        jo.put(S_PATH, request.getRequestURI());

        writeStatus(response, response.SC_NOT_FOUND, S_404_NOT_FOUND, jo);
    }

    /**
     * Return http status code 406 not acceptable.
     *
     * @param request  Http request.
     * @param response Http response.
     * @throws IOException Error while returning http status code.
     */
    protected void notAcceptable(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jo = requestToJson(request);

        writeStatus(response, response.SC_NOT_ACCEPTABLE, S_406_NOT_ACCEPTABLE, jo);
    }

    /**
     * Return http status code 409 conflict.
     *
     * @param response Http response.
     * @param message  Conflict message.
     * @throws IOException Error while returning http status code.
     */
    protected void conflict(HttpServletResponse response, String message) throws IOException {
        writeStatus(response, response.SC_CONFLICT, S_409_CONFLICT, message);
    }

    /**
     * Return http status code 500 internal server error.
     *
     * @param response Http response.
     * @param message  Message string.
     * @throws IOException Error while returning http status code.
     */
    protected void internalServerError(HttpServletResponse response, String message) throws IOException {
        writeStatus(response, response.SC_INTERNAL_SERVER_ERROR, S_500_INTERNAL_SERVER_ERROR, message);
    }

    /**
     * Return http status code 500 internal server error.
     *
     * @param response Http response.
     * @param jo       Json to send.
     * @throws IOException Error while returning http status code.
     */
    protected void internalServerError(HttpServletResponse response, JSONObject jo) throws IOException {
        writeStatus(response, response.SC_INTERNAL_SERVER_ERROR, S_500_INTERNAL_SERVER_ERROR, jo);
    }

    /**
     * Return http status code 501 not implemented.
     *
     * @param response Http response.
     * @throws IOException Error while returning http status code.
     */
    protected void notImplemented(HttpServletResponse response) throws IOException {
        writeStatus(response, response.SC_NOT_IMPLEMENTED, S_501_NOT_IMPLEMENTED);
    }

    /**
     * Write to client.
     *
     * @param response Http response.
     * @param string   String to write to client.
     * @throws IOException Error while writing to client.
     */
    protected void write(HttpServletResponse response, String string) throws IOException {
        response.getOutputStream().println(string);
    }

    /**
     * Write status and content to client.
     *
     * @param response     Http Response.
     * @param statusCode   Http status code.
     * @param statusString Http status string.
     * @throws IOException Error while writing to client.
     */
    private void writeStatus(HttpServletResponse response, int statusCode, String statusString) throws IOException {
        writeStatus(response, statusCode, statusString, new JSONObject());
    }

    /**
     * Write status and content to client.
     *
     * @param response     Http Response.
     * @param statusCode   Http status code.
     * @param statusString Http status string.
     * @param message      Message to write in JSON message attribute.
     * @throws IOException Error while writing to client.
     */
    private void writeStatus(HttpServletResponse response, int statusCode, String statusString, String message) throws IOException {
        JSONObject jo = new JSONObject();
        jo.put(S_MESSAGE, message);

        writeStatus(response, statusCode, statusString, jo);
    }

    /**
     * Write status and content to client.
     *
     * @param response     Http Response.
     * @param statusCode   Http status code.
     * @param statusString Http status string.
     * @param jo           Json to write to client (http status code and string will be added).
     * @throws IOException Error while writing to client.
     */
    private void writeStatus(HttpServletResponse response, int statusCode, String statusString, JSONObject jo) throws IOException {
        response.setStatus(statusCode);
        jo.put(S_STATUS_CODE, statusString);
        write(response, jo.toString());
    }

    /**
     * Http request to json.
     *
     * @param request Request to transform in JSON.
     * @return JSON Request.
     */
    private JSONObject requestToJson(HttpServletRequest request) {
        return requestToJson(request, new JSONObject());
    }

    /**
     * Http request to json.
     *
     * @param request Request to transform to json.
     * @param jo      JSON to add request.
     * @return JSON Request.
     */
    private JSONObject requestToJson(HttpServletRequest request, JSONObject jo) {
        JSONObject requestJson = new JSONObject();
        requestJson.put(S_REQUEST_URI, request.getRequestURI());
        requestJson.put(S_REQUEST_URL, request.getRequestURL().toString());
        requestJson.put(S_METHOD, request.getMethod());

        jo.put(S_REQUEST, request);

        return jo;
    }

    /**
     * Write to client not valid parameters.
     *
     * @param response           Response to client.
     * @param notValidParameters Not valid parameters.
     * @throws IOException Error while writing to client.
     */
    protected void notValidParameters(HttpServletResponse response, String[] notValidParameters) throws IOException {
        JSONObject jo = new JSONObject();

        jo.put(S_NOT_VALID_PARAMETERS, StringArrayHelper.toJsonArray(notValidParameters));

        writeStatus(response, response.SC_BAD_REQUEST, S_400_BAD_REQUEST, jo);
    }

    /**
     * Write to client missing parameters.
     *
     * @param response          Response to client.
     * @param missingParameters Missing parameters.
     * @throws IOException Error while writing to client.
     */
    protected void missingParameters(HttpServletResponse response, String[] missingParameters) throws IOException {
        JSONObject jo = new JSONObject();

        jo.put(S_MISSING_PARAMETERS, StringArrayHelper.toJsonArray(missingParameters));

        writeStatus(response, response.SC_BAD_REQUEST, S_400_BAD_REQUEST, jo);
    }

    /**
     * Get ok response.
     *
     * @return Ok response as json.
     */
    public JSONObject getOkResponse() {
        JSONObject jo = new JSONObject();
        jo.put(S_STATUS, S_STATUS_OK);

        return jo;
    }

    /**
     * Get not capable response.
     *
     * @return Not capable response as json.
     */
    public JSONObject getNotCapableResponse() {
        JSONObject jo = new JSONObject();
        jo.put(S_STATUS, S_STATUS_NOT_CAPABLE);

        return jo;
    }


    /**
     * Close MySQL Connector.
     *
     * @throws SQLException Error with MySQL connection.
     */
    public void closeConnector() throws SQLException {
        if (connector != null) {
            this.connector.close();
        }
    }

    /**
     * Get the first value in the array of the map.
     *
     * @param map Map.
     * @param key Key of the array
     * @return First value of array if exists.
     */
    protected String firstValue(Map<String, String[]> map, String key) {
        String[] parameterValues = map.get(key);

        if (StringArrayHelper.arrayEmpty(parameterValues) || !StringHelper.is(parameterValues[0])) {
            return null;
        }

        return parameterValues[0];
    }

    /**
     * Get the request path.
     *
     * @return Request path.
     */
    protected abstract String getPath();
}
