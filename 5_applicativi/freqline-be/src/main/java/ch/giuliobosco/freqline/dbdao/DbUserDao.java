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
import ch.giuliobosco.freqline.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * User to MySQL in DAO Pattern.
 *
 * @author giuliobosco
 * @version 1.0.1 (2019-10-09 - 2019-10-17)
 */
public class DbUserDao extends DbDao {

    /**
     * Create the DbUserDao with the connection to MySQL Database.
     * Use User for create the query builder.
     *
     * @param connector Connection to MySQL database.
     */
    public DbUserDao(JdbcConnector connector) {
        super(connector, User.class);
    }

    /**
     * Create the DbUserDao with the connection to MySQL Database and action by.
     * Use User for create the query builder.
     *
     * @param connector Connection to MySQL database.
     * @param actionBy  Action by.
     */
    public DbUserDao(JdbcConnector connector, int actionBy) {
        super(connector, User.class, actionBy);
    }

    /**
     * Create the permission object from the result set and base entity.
     *
     * @param resultSet Result set of the query.
     * @param base      Base.
     * @return Permission object created from result set.
     * @throws Exception SQL Exception.
     */
    protected Base create(ResultSet resultSet, Base base) throws Exception {

        // username password salt firstname lastname email
        String username = resultSet.getString("username");
        String password = resultSet.getString("password");
        String salt = resultSet.getString("salt");
        String firstname = resultSet.getString("firstname");
        String lastname = resultSet.getString("lastname");
        String email = resultSet.getString("email");

        return new User(
                base,
                username,
                password,
                salt,
                firstname,
                lastname,
                email
        );
    }

    /**
     * Fill the prepared statement from the base (have to be a base with user attributes)
     *
     * @param base      Base element.
     * @param statement Statement to fill.
     * @throws Exception SQL Exception.
     */
    protected void fillStatement(Base base, PreparedStatement statement) throws Exception {
        User user = (User) base;

        statement.setString(7, user.getUsername());
        statement.setString(8, user.getPassword());
        statement.setString(9, user.getSalt());
        statement.setString(10, user.getFirstname());
        statement.setString(11, user.getLastname());
        statement.setString(12, user.getEmail());
    }
}
