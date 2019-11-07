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

import ch.giuliobosco.freqline.auth.SqlAuthenticator;
import ch.giuliobosco.freqline.dbdao.DbDao;
import ch.giuliobosco.freqline.dbdao.DbUserDao;
import ch.giuliobosco.freqline.help.StringArrayHelper;
import ch.giuliobosco.freqline.help.StringHelper;
import ch.giuliobosco.freqline.jdbc.JdbcConnector;
import ch.giuliobosco.freqline.model.Base;
import ch.giuliobosco.freqline.model.User;
import ch.giuliobosco.freqline.modeljson.BaseJson;
import ch.giuliobosco.freqline.modeljson.UserJson;

import javax.servlet.annotation.WebServlet;
import java.util.Map;

/**
 * User RestfulAPI Servlet.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0 (2019-10-29 - 2019-10-29)
 */
@WebServlet(name = "UserServlet", urlPatterns = {"data/user/*"}, loadOnStartup = 1)
public class UserServlet extends BaseDataServlet {

    /**
     * Get the dao
     *
     * @param connector Jdbc Connector.
     * @param actionBy  Action by.
     * @return Initialized user dao.
     */
    @Override
    protected DbDao getDao(JdbcConnector connector, int actionBy) {
        return new DbUserDao(connector, actionBy);
    }

    /**
     * Get json parser.
     *
     * @param base Base of the json.
     * @return User json parser.
     */
    @Override
    protected BaseJson getJson(Base base) {
        return new UserJson(base);
    }

    /**
     * Get model class.
     *
     * @return User model class.
     */
    @Override
    protected Class<? extends Base> getModel() {
        return User.class;
    }

    /**
     * Map to model.
     *
     * @param params   Parameters.
     * @param base     Base element.
     * @param actionBy Action by.
     * @param dao      Dao.
     * @return User model.
     * @throws Exception Error with mysql.
     */
    @Override
    protected Base mapToBase(Map<String, String[]> params, Base base, int actionBy, DbDao dao) throws Exception {
        String username = firstValue(params, "username");
        String password = firstValue(params, "password");
        String salt = "";
        String firstname = firstValue(params, "firstname");
        String lastname = firstValue(params, "lastname");
        String email = firstValue(params, "email");

        if (!StringHelper.is(username) ||
                !StringHelper.is(firstname) ||
                !StringHelper.is(lastname) ||
                !StringHelper.is(email)) {
            return null;
        }

        if (base != null) {
            User user = new User(base, username, password, salt, firstname, lastname, email);
            if (StringHelper.is(password)) {
                return SqlAuthenticator.getUserToUpdatePassword(user, dao);
            } else {
                return SqlAuthenticator.getUserToUpdate(user, dao);
            }
        }

        if (!StringHelper.is(password)) {
            return null;
        }

        User user = new User(actionBy, username, password, salt, firstname, lastname, email);
        return SqlAuthenticator.getUserToInsert(user);
    }

    /**
     * Get the servlet path.
     *
     * @return Servlet path.
     */
    @Override
    protected String getPath() {
        return "data/user";
    }

    /**
     * Get the required put method parameters.
     *
     * @return Required put method parameters.
     */
    @Override
    protected String[] getRequiredPutParameters() {
        String[] a = super.getRequiredPutParameters();

        return StringArrayHelper.removeFromArray(a, "password");
    }

    /**
     * Required get permission string.
     *
     * @return Permission string.
     */
    @Override
    protected String requiredDeletePermission() {
        return "admin";
    }

    /**
     * Required post permission string.
     *
     * @return Permission string.
     */
    @Override
    protected String requiredGetPermission() {
        return "user";
    }

    /**
     * Required put permission string.
     *
     * @return Permission string.
     */
    @Override
    protected String requiredPostPermission() {
        return "admin";
    }

    /**
     * Required delete permission string.
     *
     * @return Permission string.
     */
    @Override
    protected String requiredPutPermission() {
        return "user";
    }
}
