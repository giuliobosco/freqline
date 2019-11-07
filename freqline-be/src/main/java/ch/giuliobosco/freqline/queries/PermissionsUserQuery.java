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

package ch.giuliobosco.freqline.queries;

import ch.giuliobosco.freqline.help.StringArrayHelper;
import ch.giuliobosco.freqline.jdbc.JdbcConnector;
import ch.giuliobosco.freqline.model.User;
import ch.giuliobosco.freqline.modeljson.PermissionJson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Permissions of user.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0.1 (2019-10-26 - 2019-11-05)
 */
public class PermissionsUserQuery {
    // ------------------------------------------------------------------------------------ Costants

    /**
     * Permission of user query.
     */
    private static final String QUERY = "SELECT p.string FROM dbe.permission p JOIN dbe.group_permission gp ON p.id = gp.permission JOIN `group` g ON gp.`group` = g.id JOIN user_group ug ON g.id = ug.`group` WHERE ug.user = ?;";

    /**
     * Permission of user id index.
     */
    private static final int ID_INDEX = 1;

    // --------------------------------------------------------------------------- Static Components

    /**
     * Get permissions from user id.
     *
     * @param connection Connection to MySQL Database.
     * @param id         Id of the user.
     * @return Permissions of the user.
     * @throws SQLException Error with mysql.
     */
    public static String[] getPermissions(Connection connection, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(QUERY);
        statement.setInt(ID_INDEX, id);
        ResultSet resultSet = statement.executeQuery();

        List<String> permissions = new ArrayList<>();

        while (resultSet.next()) {
            permissions.add(resultSet.getString(PermissionJson.STRING));
        }
        resultSet.close();
        statement.close();

        return StringArrayHelper.toStringArray(permissions.toArray());
    }

    /**
     * Get permissions from user.
     *
     * @param connection Connection to MySQL Database.
     * @param user       User.
     * @return Permissions of the user.
     * @throws SQLException Error with mysql.
     */
    public static String[] getPermissions(Connection connection, User user) throws SQLException {
        return getPermissions(connection, user.getId());
    }

    /**
     * Get the permissions from id of the user.
     *
     * @param connector Connector for MySQL Server.
     * @param id        Id of the user.
     * @return Permissions of the user.
     * @throws SQLException Error with MySQL server.
     */
    public static String[] getPermissions(JdbcConnector connector, int id) throws SQLException {
        return getPermissions(connector.getConnection(), id);
    }
}
