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

import ch.giuliobosco.freqline.jdbc.JdbcConnector;
import ch.giuliobosco.freqline.model.Base;
import ch.giuliobosco.freqline.model.Permission;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Permission to MySQL in DAO Pattern.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.2.2 (2019-09-16 - 2019-10-17)
 */
public class DbPermissionDao extends DbDao {

    /**
     * Create the DbPermissionDao with the connection to MySQL Database.
     * Use Permission query builder.
     *
     * @param connection Connection to MySQL database.
     */
    public DbPermissionDao(JdbcConnector connection) {
        super(connection, Permission.class);
    }

    /**
     * Create the DbPermissionDao with the connection to MySQL Database and action by.
     * Use Permission query builder.
     *
     * @param connector Connection to MySQL database.
     * @param actionBy  Action By.
     */
    public DbPermissionDao(JdbcConnector connector, int actionBy) {
        super(connector, Permission.class, actionBy);
    }

    /**
     * Create the permission object from the result set and base entity.
     *
     * @param resultSet Result set of the query.
     * @param base      Base.
     * @return Permission object created from result set.
     * @throws Exception SQL Exception.
     */
    @Override
    protected Base create(ResultSet resultSet, Base base) throws Exception {

        String name = resultSet.getString("name");
        String string = resultSet.getString("string");
        String description = resultSet.getString("description");

        return new Permission(
                base,
                name,
                string,
                description
        );
    }

    /**
     * Fill the prepared statement form the base (have to be a base with permissions attributes).
     *
     * @param base      Base element.
     * @param statement Statement to fill.
     * @throws Exception SQL Exception.
     */
    @Override
    protected void fillStatement(Base base, PreparedStatement statement) throws Exception {
        Permission permission = (Permission) base;

        statement.setString(7, permission.getName());
        statement.setString(8, permission.getString());
        statement.setString(9, permission.getDescription());
    }
}
