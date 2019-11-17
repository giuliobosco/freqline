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

import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Acc Check queries.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0.1 (2019-11-15 - 2019-11-17)
 */
public class AccCheckQueries {
    // ------------------------------------------------------------------------------------ Costants

    /**
     * Check generator key query.
     */
    private static final String GENERATOR_KEY_CHECK = "SELECT count(id) FROM generator WHERE key_c=?";

    /**
     * Check mic key query.
     */
    private static final String MIC_KEY_CHECK = "SELECT count(id) FROM mic WHERE key_c=?";

    /**
     * Check remote key query.
     */
    private static final String REMOTE_KEY_CHECK = "SELECT count(id) FROM remote WHERE key_c=?";

    /**
     * Query for acc auto conf.
     */
    private static final String CONF_QUERY = "SELECT g.name AS gname, g.frequence as gfreqence, g.key_c AS gkey, g.ip AS gip, m.timer AS mtimer, m.key_c AS mkey, r.key_c AS rkey FROM generator g JOIN remote r ON g.id = r.generator JOIN mic m ON g.id = m.generator WHERE g.key_c = ?";

    // --------------------------------------------------------------------------- Static Components

    /**
     * Get the json configuration for acc.
     *
     * @param connection Connection to MySQL database.
     * @param keyC       Key of communication of the generator.
     * @return Json configuration of the generator.
     * @throws SQLException Error with MySQL.
     */
    public static JSONObject getJsonConf(Connection connection, String keyC) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(CONF_QUERY);
        statement.setString(1, keyC);
        ResultSet resultSet = statement.executeQuery();

        if (!resultSet.next()) {
            return null;
        }

        JSONObject jo = new JSONObject();

        jo.put("gname", resultSet.getString("gname"));
        jo.put("gfrequence", resultSet.getString("gfrequence"));
        jo.put("gkey", resultSet.getString("gkey"));
        jo.put("gip", resultSet.getString("gip"));
        jo.put("mtimer", resultSet.getString("mtimer"));
        jo.put("mkey", resultSet.getString("rkey"));
        jo.put("rkey", resultSet.getString("rkey"));

        return jo;
    }

    /**
     * Check if key is valid.
     *
     * @param query      Query.
     * @param connection Connection to MySQL database.
     * @param keyC       Key to check.
     * @return True if key is valid.
     * @throws SQLException Error with MySQL.
     */
    private static boolean isValidKey(String query, Connection connection, String keyC) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, keyC);

        ResultSet resultSet = statement.executeQuery();

        if (!resultSet.next()) {
            return false;
        }

        return resultSet.getInt(1) > 0;
    }

    /**
     * Check if generator key is valid.
     *
     * @param connection Connection to MySQL database.
     * @param keyC       Key to check.
     * @return True if key is valid.
     * @throws SQLException Error with MySQL.
     */
    public static boolean isValidGeneratorKey(Connection connection, String keyC) throws SQLException {
        return isValidKey(GENERATOR_KEY_CHECK, connection, keyC);
    }

    /**
     * Check if mic key is valid.
     *
     * @param connection Connection to MySQL database.
     * @param keyC       Key to check.
     * @return True if key is valid.
     * @throws SQLException Error with MySQL.
     */
    public static boolean isValidMicKey(Connection connection, String keyC) throws SQLException {
        return isValidKey(MIC_KEY_CHECK, connection, keyC);
    }

    /**
     * Check if remote key is valid.
     *
     * @param connection Connection to MySQL database.
     * @param keyC       Key to check.
     * @return True if key is valid.
     * @throws SQLException Error with MySQL.
     */
    public static boolean isValidRemoteKey(Connection connection, String keyC) throws SQLException {
        return isValidKey(REMOTE_KEY_CHECK, connection, keyC);
    }
}
