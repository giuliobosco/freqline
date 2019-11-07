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

import ch.giuliobosco.freqline.model.Base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Id of user.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0.1 (2019-10-26 - 2019-10-26)
 */
public class UserIdQuery {
    // ------------------------------------------------------------------------------------ Costants

    /**
     * Id of user query.
     */
    private static final String QUERY = "SELECT id FROM dbe.user WHERE username=?";

    /**
     * Username of user query index.
     */
    private static final int USERNAME_INDEX = 1;

    // --------------------------------------------------------------------------- Static Components

    /**
     * Get id from username.
     *
     * @param connection Connection to MySQL database.
     * @param username   Username of the user.
     * @return Id of the user, -1 if user does not exists.
     * @throws SQLException Error with mysql.
     */
    public static int getUserId(Connection connection, String username) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(QUERY);
        statement.setString(USERNAME_INDEX, username);
        ResultSet resultSet = statement.executeQuery();

        if (!resultSet.next()) {
            return -1;
        }

        return resultSet.getInt(Base.ID);
    }

}
