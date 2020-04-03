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

package ch.giuliobosco.freqline.dbdao;

import ch.giuliobosco.freqline.jdbc.JapiConnector;
import ch.giuliobosco.freqline.jdbc.JdbcConnector;
import ch.giuliobosco.freqline.model.Base;
import ch.giuliobosco.freqline.model.Group;
import ch.giuliobosco.freqline.model.GroupPermission;
import ch.giuliobosco.freqline.model.Permission;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Group Permission to MySQL in DAO Pattern.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0.2 (2019-10-09 - 2019-10-17)
 */
public class DbGroupPermissionDao extends DbDao {

    /**
     * Create the DbGroupPermissionDao with the connection to MySQL Database.
     * Use UserGroup for the Query Builder.
     *
     * @param connector Connection to MySQL database.
     */
    public DbGroupPermissionDao(JdbcConnector connector) {
        super(connector, GroupPermission.class);
    }

    /**
     * Create the DbGroupPermissionDao with the connection to MySQL Database and action by.
     * Use UserGroup for the Query Builder.
     *
     * @param connector Connection to MySQL database.
     * @param actionBy  Action by.
     */
    public DbGroupPermissionDao(JdbcConnector connector, int actionBy) {
        super(connector, GroupPermission.class, actionBy);
    }

    /**
     * Get the permission from the result set.
     *
     * @param resultSet Result set.
     * @param connector New connection opened to MySQL Database.
     * @return Permission from the result set.
     * @throws Exception SQL Exception.
     */
    private Permission getPermission(ResultSet resultSet, JdbcConnector connector) throws Exception {

        DbPermissionDao dao = new DbPermissionDao(connector);
        String resultSetColumn = "permission";

        return (Permission) getBase(resultSet, resultSetColumn, dao);
    }

    /**
     * Get the Group Permission from the result set.
     *
     * @param resultSet Result set.
     * @param connector New connection opened to MySQL Database.
     * @return Group Permission from the result set.
     * @throws Exception SQL Exception.
     */
    private Group getGroup(ResultSet resultSet, JdbcConnector connector) throws Exception {

        DbGroupDao dao = new DbGroupDao(connector);
        String resultSetColumn = "group";

        return (Group) getBase(resultSet, resultSetColumn, dao);
    }

    /**
     * Create the Group Permission from the result set and the base entity.
     *
     * @param resultSet Result set of the query.
     * @param base      Base.
     * @return Group Permission object created from the result set.
     * @throws Exception SQL Exception.
     */
    @Override
    protected Base create(ResultSet resultSet, Base base) throws Exception {
        JdbcConnector connector = JapiConnector.initialize();
        connector.openConnection();

        Group group = getGroup(resultSet, connector);
        Permission permission = getPermission(resultSet, connector);

        connector.close();

        return new GroupPermission(base, permission, group);
    }

    /**
     * Fill prepared statement with group permission object.
     *
     * @param base      Base element.
     * @param statement Statement to fill.
     * @throws Exception Error in MySQL.
     */
    @Override
    protected void fillStatement(Base base, PreparedStatement statement) throws Exception {
        GroupPermission groupPermission = (GroupPermission) base;

        Group group = groupPermission.getGroup();
        Permission permission = groupPermission.getPermission();

        if (permission != null) {
            statement.setInt(7, permission.getId());
        } else {
            statement.setObject(7, null);
        }

        if (group != null) {
            statement.setInt(8, group.getId());
        } else {
            statement.setObject(8, null);
        }
    }
}
