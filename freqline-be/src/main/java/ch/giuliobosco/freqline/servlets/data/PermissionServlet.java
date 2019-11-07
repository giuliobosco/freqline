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

import ch.giuliobosco.freqline.dbdao.DbDao;
import ch.giuliobosco.freqline.dbdao.DbPermissionDao;
import ch.giuliobosco.freqline.help.StringHelper;
import ch.giuliobosco.freqline.jdbc.JdbcConnector;
import ch.giuliobosco.freqline.model.Base;
import ch.giuliobosco.freqline.model.Permission;
import ch.giuliobosco.freqline.modeljson.BaseJson;
import ch.giuliobosco.freqline.modeljson.PermissionJson;

import javax.servlet.annotation.WebServlet;
import java.util.Map;

/**
 * Permission RestfulAPI Servlet.
 *
 * @author giuliobosco
 * @version 1.0.2 (2019-10-24 - 2019-10-26)
 */
@WebServlet(name = "PermissionServlet", urlPatterns = {"data/permission/*"}, loadOnStartup = 1)
public class PermissionServlet extends BaseDataServlet {

    /**
     * Get the dao.
     *
     * @param connector Jdbc Connector.
     * @param actionBy  Action by.
     * @return Initialized permission dao.
     */
    @Override
    protected DbDao getDao(JdbcConnector connector, int actionBy) {
        return new DbPermissionDao(connector, actionBy);
    }

    /**
     * Get json parser.
     *
     * @param base Base of the json.
     * @return Permission json parser.
     */
    @Override
    protected BaseJson getJson(Base base) {
        return new PermissionJson(base);
    }

    /**
     * Get model class.
     *
     * @return Permission model class.
     */
    @Override
    protected Class<? extends Base> getModel() {
        return Permission.class;
    }

    /**
     * Transform params map to Permission element.
     *
     * @param params   Parameters.
     * @param base     Base element.
     * @param actionBy Action by.
     * @param dao      Dao.
     * @return Permission element.
     */
    @Override
    protected Base mapToBase(Map<String, String[]> params, Base base, int actionBy, DbDao dao) {
        String name = firstValue(params, PermissionJson.NAME);
        String string = firstValue(params, PermissionJson.STRING);
        String description = firstValue(params, PermissionJson.DESCRIPTION);

        if (!StringHelper.is(name) || !StringHelper.is(string) || !StringHelper.is(description)) {
            return null;
        }

        if (base != null) {
            return new Permission(base, name, string, description);
        }

        return new Permission(actionBy, name, string, description);
    }

    /**
     * Get the path.
     *
     * @return Path of servlet.
     */
    @Override
    protected String getPath() {
        return "data/permission/";
    }

    /**
     * Required get permission string.
     *
     * @return Permission string.
     */
    @Override
    protected String requiredDeletePermission() {
        return "db";
    }

    /**
     * Required post permission string.
     *
     * @return Permission string.
     */
    @Override
    protected String requiredGetPermission() {
        return "admin";
    }

    /**
     * Required put permission string.
     *
     * @return Permission string.
     */
    @Override
    protected String requiredPostPermission() {
        return "db";
    }

    /**
     * Required delete permission string.
     *
     * @return Permission string.
     */
    @Override
    protected String requiredPutPermission() {
        return "db";
    }
}
