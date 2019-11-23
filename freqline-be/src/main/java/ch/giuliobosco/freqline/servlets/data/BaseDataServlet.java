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

package ch.giuliobosco.freqline.servlets.data;

import org.json.JSONArray;
import ch.giuliobosco.freqline.auth.SessionManager;
import ch.giuliobosco.freqline.dbdao.DbDao;
import ch.giuliobosco.freqline.help.ArrayHelper;
import ch.giuliobosco.freqline.help.ClassStringHelper;
import ch.giuliobosco.freqline.help.StringArrayHelper;
import ch.giuliobosco.freqline.help.StringHelper;
import ch.giuliobosco.freqline.help.validators.StringValidator;
import ch.giuliobosco.freqline.jdbc.JapiConnector;
import ch.giuliobosco.freqline.jdbc.JdbcConnector;
import ch.giuliobosco.freqline.model.Base;
import ch.giuliobosco.freqline.model.User;
import ch.giuliobosco.freqline.modeljson.BaseJson;
import ch.giuliobosco.freqline.queries.PermissionsUserQuery;
import ch.giuliobosco.freqline.servlets.BaseServlet;
import ch.giuliobosco.freqline.servlets.help.ServletNfe;
import ch.giuliobosco.freqline.servlets.help.ServletRequestAnalyser;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Base servlet for japi.
 *
 * @author giuliobosco
 * @version 1.1.1 (2019-10-18 - 2019-11-23)
 */
@WebServlet(name = "BaseDataServlet")
public abstract class BaseDataServlet extends BaseServlet {

    /**
     * Get id by http request.
     *
     * @param request Http request.
     * @return Id of the request.
     * @throws NumberFormatException No id.
     */
    private int getId(HttpServletRequest request) throws ServletNfe {
        int idStartIndex = request.getRequestURI().lastIndexOf('/') + 1;
        String stringId = request.getRequestURI().substring(idStartIndex);

        try {
            return Integer.parseInt(stringId);
        } catch (NumberFormatException nfe) {
            throw new ServletNfe(nfe.getMessage());
        }
    }

    /**
     * Do get.
     *
     * @param request  Http request.
     * @param response Http response.
     * @throws ServletException Error in servlet.
     * @throws IOException      Input Output Error.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionManager sm = new SessionManager(request.getSession());
        try {
            initConnector();
            boolean hasP = hasRequiredPermission(sm.getUserId(), requiredGetPermission());
            if (sm.isValidSession() && hasP) {
                DbDao dao = getDao(sm.getUserId());

                try {
                    doGetById(request, response, dao);
                } catch (ServletNfe snfe) {
                    doGetAll(response, dao);
                }

                closeConnector();
            } else {
                unauthorized(request, response);
            }
        } catch (Exception e) {
            internalServerError(response, e.getMessage());
        }
    }

    /**
     * Do get get by id.
     *
     * @param request  Http request.
     * @param response Http response.
     * @throws ServletException Error in servlet.
     * @throws IOException      Input Output Error.
     */
    private void doGetById(HttpServletRequest request, HttpServletResponse response, DbDao dao) throws Exception {
        int id = getId(request);

        Optional<Base> optional = dao.getById(id);
        if (optional.isPresent()) {
            BaseJson bj = getJson(optional.get());
            ok(response, bj.getJson());
        } else {
            notFound(request, response);
        }
    }

    /**
     * Do get all.
     *
     * @param response Http response.
     * @throws ServletException Error in servlet.
     * @throws IOException      Input Output Error.
     */
    private void doGetAll(HttpServletResponse response, DbDao dao) throws Exception {
        Stream<Base> baseStream = dao.getAll();
        Object[] objects = baseStream.toArray();
        JSONArray ja = new JSONArray();

        for (Object o : objects) {
            BaseJson bj = getJson((Base) o);
            ja.put(bj.getJson());
        }

        ok(response, new JSONObject().put("data", ja));
    }

    /**
     * Do delete.
     *
     * @param request  Http request.
     * @param response Http response.
     * @throws ServletException Error in servlet.
     * @throws IOException      Input Output Error.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionManager sm = new SessionManager(request.getSession());
        try {
            initConnector();
            boolean hasP = hasRequiredPermission(sm.getUserId(), requiredDeletePermission());
            if (sm.isValidSession() && hasP) {
                DbDao dao = getDao(sm.getUserId());

                try {
                    int id = getId(request);

                    Optional<Base> optional = dao.getById(id);
                    if (optional.isPresent()) {
                        if (dao.delete(optional.get())) {
                            ok(response, getOkResponse(id));
                        } else {
                            internalServerError(response, getNotCapableResponse());
                        }
                    } else {
                        notFound(request, response);
                    }
                } catch (ServletNfe snfe) {
                    notFound(request, response);
                }

                closeConnector();
            } else {
                unauthorized(request, response);
            }
        } catch (Exception e) {
            internalServerError(response, e.getMessage());
        }
    }

    /**
     * Do post, create new element.
     *
     * @param request  Client request.
     * @param response Client response.
     * @throws ServletException Error in the servlet.
     * @throws IOException      Error writing to client.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionManager sm = new SessionManager(request.getSession());
        try {
            initConnector();
            boolean hasP = hasRequiredPermission(sm.getUserId(), requiredPostPermission());
            if (sm.isValidSession() && hasP) {
                String[] requiredAttributes = getRequiredPostParameters();

                ServletRequestAnalyser sra = new ServletRequestAnalyser(
                        requiredAttributes,
                        request.getParameterMap(),
                        new StringValidator());

                switch (sra.getStatus()) {
                    case ServletRequestAnalyser.NOT_VALID_PARAMETERS:
                        notValidParameters(response, sra.getNotValidParameters());
                        break;
                    case ServletRequestAnalyser.MISSING_PARAMETERS:
                        missingParameters(response, sra.getMissingParameters());
                        break;
                    case ServletRequestAnalyser.OK:
                        executePost(response, sra, sm.getUserId());
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
     * @param response Http Response.
     * @param sra      Servlet Request Analyser, with analysed request.
     * @param actionBy Action by.
     * @throws Exception While executing query.
     */
    private void executePost(HttpServletResponse response, ServletRequestAnalyser sra, int actionBy) throws Exception {
        DbDao dao = getDao(actionBy);

        if (dao.add(mapToBase(sra.getParameters(), null, dao.getActionBy(), dao))) {
            created(response, getOkResponse(dao.getLastGeneratedKey()));

        } else {
            internalServerError(response, getNotCapableResponse());
        }
    }

    /**
     * Do put, update element.
     *
     * @param request  Http request.
     * @param response Http response.
     * @throws ServletException Error in the servlet.
     * @throws IOException      Error while writing to client.
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionManager sm = new SessionManager(request.getSession());
        try {
            initConnector();
            boolean hasP = hasRequiredPermission(sm.getUserId(), requiredPutPermission());
            if (sm.isValidSession() && hasP) {
                String[] requiredParameter = getRequiredPutParameters();

                ServletRequestAnalyser sra = new ServletRequestAnalyser(
                        requiredParameter,
                        request.getParameterMap(),
                        new StringValidator());

                switch (sra.getStatus()) {
                    case ServletRequestAnalyser.NOT_VALID_PARAMETERS:
                        notValidParameters(response, sra.getNotValidParameters());
                        break;
                    case ServletRequestAnalyser.MISSING_PARAMETERS:
                        missingParameters(response, sra.getMissingParameters());
                        break;
                    case ServletRequestAnalyser.OK:
                        executePut(request, response, sra, sm.getUserId());
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
     * Execute put.
     *
     * @param request  Http request.
     * @param response Http response.
     * @param sra      Servlet Request analyser.
     * @param actionBy Action by.
     * @throws Exception Error.
     */
    private void executePut(HttpServletRequest request, HttpServletResponse response, ServletRequestAnalyser sra, int actionBy) throws Exception {

        Map<String, String[]> params = sra.getParameters();

        try {
            int id = Integer.parseInt(firstValue(params, Base.ID));

            DbDao dao = getDao(actionBy);
            Optional<Base> optional = dao.getById(id);

            if (optional.isPresent()) {
                Base b = optional.get();
                b = mapToBase(params, b, 1, dao);

                if (dao.update(b)) {
                    ok(response, getOkResponse(id));
                } else {
                    internalServerError(response, getNotCapableResponse());
                }
            } else {
                notFound(request, response);
            }
        } catch (NumberFormatException e) {
            notValidParameters(response, new String[]{Base.ID});
        }
    }

    /**
     * Get the required attributes for put method.
     *
     * @return Required attributes for put method.
     */
    protected String[] getRequiredPutParameters() {
        String[] baseParameters = new String[]{Base.ID};
        String[] modelParameters = ClassStringHelper.classFieldsNameToStringArray(getModel());

        return StringArrayHelper.concatenateArray(baseParameters, modelParameters);
    }

    /**
     * Get the required post attributes.
     *
     * @return Required post attributes.
     */
    protected String[] getRequiredPostParameters() {
        return ClassStringHelper.classFieldsNameToStringArray(getModel());
    }

    /**
     * Get the dao with the action by.
     *
     * @param actionBy Action by.
     * @return Dao from action by and JapiConnector.
     * @throws IOException            Error with Input/Output.
     * @throws ClassNotFoundException MySQL Driver Not found.
     * @throws SQLException           Error with MySQL.
     */
    private DbDao getDao(int actionBy) throws IOException, ClassNotFoundException, SQLException {
        this.initConnector();

        return getDao(this.getConnector(), actionBy);
    }

    /**
     * Initialize the connection in connector.
     *
     * @throws ClassNotFoundException Error with MySQL Driver class.
     * @throws SQLException           Error in MySQL server or query.
     * @throws IOException            Error with IO.
     */
    private void initConnector() throws ClassNotFoundException, SQLException, IOException {
        if (this.getConnector() == null || this.getConnector().getConnection() == null) {
            this.setConnector(new JapiConnector());
            this.getConnector().openConnection();
        }
    }

    /**
     * Get base from the id in the params at key.
     *
     * @param params Parameters.
     * @param dao    Dao, for load the base.
     * @param key    Key in parameters.
     * @return The base if exists, other wise null.
     * @throws Exception Error with MySQL.
     */
    protected Base getBase(Map<String, String[]> params, DbDao dao, String key) throws Exception {
        Base base = null;

        String baseIdString = firstValue(params, key);

        if (StringHelper.is(baseIdString)) {
            try {
                int baseId = Integer.parseInt(baseIdString);
                Optional<Base> optional = dao.getById(baseId);

                if (optional.isPresent()) {
                    base = optional.get();
                } else {
                    return null;
                }
            } catch (NumberFormatException nfe) {
                return null;
            }
        }

        return base;
    }

    /**
     * Check if the user has the required permission.
     *
     * @param userId             User id.
     * @param requiredPermission Require Permission.
     * @return True if the user has the required permission.
     * @throws SQLException Error with MySQL.
     */
    private boolean hasRequiredPermission(int userId, String requiredPermission) throws SQLException {
        String[] perms = PermissionsUserQuery.getPermissions(getConnector(), userId);

        return ArrayHelper.isInArray(perms, requiredPermission);
    }

    /**
     * Check if the user has the required permission.
     *
     * @param user               User.
     * @param requiredPermission Required permission.
     * @return True if the user has the required permission.
     * @throws SQLException Error with MySQL.
     */
    private boolean hasRequiredPermission(User user, String requiredPermission) throws SQLException {
        return hasRequiredPermission(user.getId(), requiredPermission);
    }

    /**
     * Transform Servlet Request Analyzer to base element (update).
     *
     * @param params   Parameters.
     * @param base     Base element.
     * @param actionBy Action by.
     * @param dao      Dao.
     * @return Base transformed from servlet request analyser.
     */
    protected abstract Base mapToBase(Map<String, String[]> params, Base base, int actionBy, DbDao dao) throws Exception;

    /**
     * Get the model class.
     *
     * @return Model class.
     */
    protected abstract Class<? extends Base> getModel();

    /**
     * Get the DbDao.
     *
     * @param connector Jdbc Connector.
     * @param actionBy  Action by.
     * @return DbDao of the model.
     */
    protected abstract DbDao getDao(JdbcConnector connector, int actionBy);

    /**
     * Get the json element of the model.
     *
     * @param base Base of the json.
     * @return Json element of the model.
     */
    protected abstract BaseJson getJson(Base base);

    /**
     * Required get permission string.
     *
     * @return Permission string.
     */
    protected abstract String requiredGetPermission();

    /**
     * Required post permission string.
     *
     * @return Permission string.
     */
    protected abstract String requiredPostPermission();

    /**
     * Required put permission string.
     *
     * @return Permission string.
     */
    protected abstract String requiredPutPermission();

    /**
     * Required delete permission string.
     *
     * @return Permission string.
     */
    protected abstract String requiredDeletePermission();
}
