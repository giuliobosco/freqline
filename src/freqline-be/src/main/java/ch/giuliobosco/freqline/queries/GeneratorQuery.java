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

import java.sql.*;

/**
 * Queries for status of generator.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0.5 (2019-11-14 - 2019-11-30)
 */
public class GeneratorQuery {

    // ------------------------------------------------------------------------------------ Costants

    /**
     * Select the status of the generator from keyc.
     */
    private static final String GET_STATUS_KEY_QUERY = "SELECT status FROM generator WHERE key_c = ?";

    /**
     * Select the status of the generator from user favorite generator.
     */
    private static final String GET_STATUS_USER_ID_QUERY = "SELECT status FROM user u JOIN generator g ON u.favorite_generator = g.id WHERE u.id = ?";

    /**
     * Select the status of the generator from keyc.
     */
    private static final String GET_FREQUENCE_KEY_QUERY = "SELECT frequence FROM generator WHERE key_c = ?";

    /**
     * Select the status of the generator from user favorite generator.
     */
    private static final String GET_FREQUENCE_USER_ID_QUERY = "SELECT frequence FROM user u JOIN generator g ON u.favorite_generator = g.id WHERE u.id = ?";

    /**
     * Select the key of communication from the user id.
     */
    private static final String GET_KEY_USER_ID_QUERY = "SELECT g.key_c FROM user u JOIN generator g ON u.favorite_generator = g.id WHERE u.id = ?";

    /**
     * Select timer of the mic from the keyc.
     */
    private static final String GET_MIC_TIMER_KEY_QUERY = "SELECT timer FROM mic WHERE key_c=?";

    /**
     * Set the status of the generator from the keyc.
     */
    private static final String SET_STATUS_KEY_QUERY = "UPDATE generator SET status=? WHERE key_c=?";

    /**
     * Set the status of the generator from the user id.
     */
    private static final String SET_STATUS_USER_ID_QUERY = "UPDATE generator g JOIN user u ON u.favorite_generator=g.id SET g.status=? WHERE u.id = ?";

    /**
     * Set the frequence from the user id.
     */
    private static final String SET_FREQUENCE_USER_ID_QUERY = "UPDATE generator g JOIN user u ON u.favorite_generator=g.id SET g.frequence=? WHERE u.id = ?";

    /**
     * Set the mic timer from the user id.
     */
    private static final String SET_MIC_TIMER_USER_ID_QUERY = "UPDATE mic m JOIN generator g on m.generator = g.id JOIN user u on g.id = u.favorite_generator SET m.timer=? WHERE u.id=?";

    /**
     * Get the ip of the generator from the favorite generator of the user.
     */
    private static final String GET_IP_USER_ID_QUERY = "SELECT ip FROM user u JOIN generator g ON u.favorite_generator = g.id WHERE u.id = ?";

    /**
     * Get the ip of the generator from the keyc.
     */
    private static final String GET_IP_KEY_QUERY = "SELECT ip FROM generator WHERE key_c=?";

    /**
     * Set the decibels from the user id.
     */
    private static final String SET_DECIBEL = "UPDATE freqline.mic m JOIN generator g on m.generator = g.id JOIN user u on g.id = u.favorite_generator SET m.decibel=? WHERE u.id=?";

    /**
     * Get he decibels from the user.
     */
    private static final String GET_DECIBEL_USER_ID = "SELECT mic.decibel FROM mic JOIN generator g on mic.generator = g.id JOIN user u on g.id = u.favorite_generator WHERE u.id=?";

    /**
     * Status string.
     */
    private static final String STATUS = "status";

    /**
     * Frequence string.
     */
    private static final String FREQUENCE = "frequence";

    /**
     * Timer string.
     */
    private static final String TIMER = "timer";

    /**
     * Ip string.
     */
    private static final String IP = "ip";

    /**
     * Key of communication string.
     */
    private static final String KEY_C = "key_c";

    // --------------------------------------------------------------------------- Static Components

    /**
     * Get the status of the generator from the key of communication.
     *
     * @param connection Connection to MySQL database.
     * @param keyC       Key of communication.
     * @return Status of the generator.
     * @throws SQLException Error in MySQL.
     */
    public static boolean getGeneratorStatus(Connection connection, String keyC) throws SQLException {
        return getStatus(connection, GET_STATUS_KEY_QUERY, keyC, 0);
    }

    /**
     * Get the status of the generator from the favorite generator of the user.
     *
     * @param connection Connection to MySQL database.
     * @param userId     Id of the user.
     * @return Status of the generator.
     * @throws SQLException Error in MySQL.
     */
    public static boolean getGeneratorStatus(Connection connection, int userId) throws SQLException {
        return getStatus(connection, GET_STATUS_USER_ID_QUERY, null, userId);
    }

    /**
     * Get the frequence of the generator from the key of communication.
     *
     * @param connection Connection to MySQL database.
     * @param keyC       Key of communication.
     * @return Frequence of the generator.
     * @throws SQLException Error in MySQL.
     */
    public static int getGeneratorFrequence(Connection connection, String keyC) throws SQLException {
        return getFrequence(connection, GET_FREQUENCE_KEY_QUERY, keyC, 0);
    }

    /**
     * Get the frequence of the generator from the favorite generator of the user.
     *
     * @param connection Connection to MySQL database.
     * @param userId     Id of the user.
     * @return Frequence of the generator.
     * @throws SQLException Error in MySQL.
     */
    public static int getGeneratorFrequence(Connection connection, int userId) throws SQLException {
        return getFrequence(connection, GET_FREQUENCE_USER_ID_QUERY, null, userId);
    }

    /**
     * Get the status, general method.
     *
     * @param connection  Connection to MySQL.
     * @param query       Query to execute.
     * @param stringValue String value (will use string).
     * @param intValue    In value (used if string null).
     * @return Status of the generator.
     * @throws SQLException Error with MySQL.
     */
    private static boolean getStatus(Connection connection, String query, String stringValue, int intValue) throws SQLException {
        ResultSet resultSet = get(connection, query, stringValue, intValue);

        if (!resultSet.next()) {
            return false;
        }

        return resultSet.getBoolean(STATUS);
    }

    /**
     * Get the frequence, general method.
     *
     * @param connection  Connection to MySQL.
     * @param query       Query to execute.
     * @param stringValue String value (will use string).
     * @param intValue    In value (used if string null).
     * @return Frequence of the generator.
     * @throws SQLException Error with MySQL.
     */
    private static int getFrequence(Connection connection, String query, String stringValue, int intValue) throws SQLException {
        ResultSet resultSet = get(connection, query, stringValue, intValue);

        if (!resultSet.next()) {
            return 0;
        }

        return resultSet.getInt(FREQUENCE);
    }

    /**
     * Execute general query.
     *
     * @param connection  Connection to MySQL.
     * @param query       Query to execute.
     * @param stringValue String value (will use string).
     * @param intValue    In value (used if string null).
     * @return Result of the query.
     * @throws SQLException Error with MySQL.
     */
    private static ResultSet get(Connection connection, String query, String stringValue, int intValue) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);

        if (stringValue != null) {
            statement.setString(1, stringValue);
        } else {
            statement.setInt(1, intValue);
        }

        return statement.executeQuery();
    }

    /**
     * Update the status of the generator from the favorite generator of the users.
     *
     * @param connection Connection to MySQL database.
     * @param userId     User id.
     * @param status     Status of the generator.
     * @return True if query executed well.
     * @throws SQLException Error with MySQL.
     */
    public static boolean setStatus(Connection connection, int userId, boolean status) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SET_STATUS_USER_ID_QUERY);
        statement.setBoolean(1, status);
        statement.setInt(2, userId);

        return statement.executeUpdate() > 0;
    }

    /**
     * Set the status of the generator form the key of communication.
     *
     * @param connection Connection to MySQL database.
     * @param keyC       Key of communication.
     * @param status     Status of the generator.
     * @return True if query executed well.
     * @throws SQLException Error with MySQL.
     */
    public static boolean setStatus(Connection connection, String keyC, boolean status) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SET_STATUS_KEY_QUERY);
        statement.setBoolean(1, status);
        statement.setString(2, keyC);

        return statement.executeUpdate() > 0;
    }

    /**
     * Get the timer of hte mic from the keyC.
     *
     * @param connection Connection to MySQL.
     * @param keyC       Key of communication.
     * @return Timer of the mic.
     * @throws SQLException Error with MySQL.
     */
    public static int getMicTimer(Connection connection, String keyC) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(GET_MIC_TIMER_KEY_QUERY);
        statement.setString(1, keyC);

        ResultSet resultSet = statement.executeQuery();

        if (!resultSet.next()) {
            return -1;
        }

        return resultSet.getInt(TIMER);
    }

    /**
     * Update the frequence of the generator from the favorite generator of the user.
     *
     * @param connection Connection to MySQL database.
     * @param userId     User id.
     * @param frequence  Frequence of the generator.
     * @return True if query executed well.
     * @throws SQLException Error with MySQL.
     */
    public static boolean setFrequence(Connection connection, int userId, int frequence) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SET_FREQUENCE_USER_ID_QUERY);
        statement.setInt(1, frequence);
        statement.setInt(2, userId);

        return statement.executeUpdate() > 0;
    }

    /**
     * Get the ip of the generator from the favorite generator of the user.
     *
     * @param connection Connection to MySQL database.
     * @param userId     User id.
     * @return Ip of the generator.
     * @throws SQLException Error with MySQL.
     */
    public static String getIp(Connection connection, int userId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(GET_IP_USER_ID_QUERY);
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();

        if (!resultSet.next()) {
            return null;
        }

        return resultSet.getString(IP);
    }

    /**
     * Get the ip of the generator from the key of communication.
     *
     * @param connection Connection to MySQL.
     * @param keyC       Key of communication.
     * @return Ip of the generator.
     * @throws SQLException Error with MySQL.
     */
    public static String getIp(Connection connection, String keyC) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(GET_IP_KEY_QUERY);
        statement.setString(1, keyC);
        ResultSet resultSet = statement.executeQuery();

        if (!resultSet.next()) {
            return null;
        }

        return resultSet.getString(IP);
    }

    /**
     * Get the key of communication of the generator from the user id.
     *
     * @param connection Connection to MySQL database.
     * @param userId     User id of the user.
     * @return Key of communication of the generator.
     * @throws SQLException Error with MySQL.
     */
    public static String getKeyByUserId(Connection connection, int userId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(GET_KEY_USER_ID_QUERY);
        statement.setInt(1, userId);

        ResultSet resultSet = statement.executeQuery();

        if (!resultSet.next()) {
            return null;
        }

        return resultSet.getString(KEY_C);
    }

    /**
     * Set the mic timer from the user id.
     *
     * @param connection Connection to MySQL.
     * @param userId User id.
     * @param timer Length of the timer.
     * @return True if executed query correctly.
     * @throws SQLException Error with MysQL.
     */
    public static boolean setMicTimer(Connection connection, int userId, int timer) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SET_MIC_TIMER_USER_ID_QUERY);
        statement.setInt(1, timer);
        statement.setInt(2, userId);

        return statement.executeUpdate() > 0;
    }

    /**
     * Set the decibels of the favorite generator of the user.
     * @param connection Connection to MySQL database.
     * @param userId User id.
     * @param decibel Decibels.
     * @return True if query executed correctly.
     * @throws SQLException Error with MySQL database.
     */
    public static boolean setDecibel(Connection connection, int userId, int decibel) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SET_DECIBEL);
        statement.setInt(1, decibel);
        statement.setInt(2, userId);

        return statement.executeUpdate() > 0;
    }

    /**
     * Get the decibels of the favorite generator of the user.
     *
     * @param connection Connection to MySQL database.
     * @param userId User id.
     * @return Decibels.
     * @throws SQLException Error with MySQL database.
     */
    public static int getDecibel(Connection connection, int userId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(GET_DECIBEL_USER_ID);
        statement.setInt(1, userId);

        ResultSet resultSet = statement.executeQuery();

        if (!resultSet.next()) {
            return -1;
        }

        return resultSet.getInt(1);
    }
}
