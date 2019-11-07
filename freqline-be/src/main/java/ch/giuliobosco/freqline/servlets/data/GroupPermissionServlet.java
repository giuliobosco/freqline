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
import ch.giuliobosco.freqline.dbdao.DbGroupDao;
import ch.giuliobosco.freqline.dbdao.DbGroupPermissionDao;
import ch.giuliobosco.freqline.dbdao.DbPermissionDao;
import ch.giuliobosco.freqline.jdbc.JdbcConnector;
import ch.giuliobosco.freqline.model.Base;
import ch.giuliobosco.freqline.model.Group;
import ch.giuliobosco.freqline.model.GroupPermission;
import ch.giuliobosco.freqline.model.Permission;
import ch.giuliobosco.freqline.modeljson.BaseJson;
import ch.giuliobosco.freqline.modeljson.GroupPermissionJson;

import javax.servlet.annotation.WebServlet;
import java.util.Map;

/**
 * Group Permission Restful API Servlet.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0.1 (2019-10-29 - 2019-11-05)
 */
@WebServlet(name = "GroupPermissionServlet", urlPatterns = {"data/groupPermission/*"}, loadOnStartup = 1)
public class GroupPermissionServlet extends BaseDataServlet {

    /**
     * Get the dao
     *
     * @param connector Jdbc Connector.
     * @param actionBy  Action by.
     * @return Initialized group permission dao.
     */
    @Override
    protected DbDao getDao(JdbcConnector connector, int actionBy) {
        return new DbGroupPermissionDao(connector, actionBy);
    }

    /**
     * Get json parser.
     *
     * @param base Base of the json.
     * @return Group permission json parser.
     */
    @Override
    protected BaseJson getJson(Base base) {
        return new GroupPermissionJson(base);
    }

    /**
     * Get model class.
     *
     * @return Group permission model class.
     */
    @Override
    protected Class<? extends Base> getModel() {
        return GroupPermission.class;
    }

    /**
     * Map to model.
     *
     * @param params   Parameters.
     * @param base     Base element.
     * @param actionBy Action by.
     * @param dao      Dao.
     * @return Group permission model.
     * @throws Exception Error with mysql.
     */
    @Override
    protected Base mapToBase(Map<String, String[]> params, Base base, int actionBy, DbDao dao) throws Exception {
        DbGroupDao groupDao = new DbGroupDao(getConnector(), actionBy);
        Group group = (Group) getBase(params, groupDao, "group");
        DbPermissionDao permissionDao = new DbPermissionDao(getConnector(), actionBy);
        Permission permission = (Permission) getBase(params, permissionDao, "permission");

        if (group == null || permission == null) {
            return null;
        }

        if (base != null) {
            return new GroupPermission(base, permission, group);
        }

        return new GroupPermission(actionBy, permission, group);
    }

    /**
     * Get the servlet path.
     *
     * @return Servlet path.
     */
    @Override
    protected String getPath() {
        return "data/groupPermission";
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
