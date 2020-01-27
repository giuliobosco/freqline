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

package ch.giuliobosco.freqline.acc;

import ch.giuliobosco.freqline.help.HttpRequestor;
import ch.giuliobosco.freqline.queries.GeneratorQuery;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Send message to Generator (ACC Protocol).
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0.2 (2019-11-18 - 2019-11-30)
 */
public class AccGenerator {

    // --------------------------------------------------------------------------- Static Components

    /**
     * Turn generator on (also update database).
     *
     * @param connection Connection to MySQL database.
     * @param keyC       Key of communication of the generator.
     * @throws SQLException Error with MySQL.
     * @throws IOException  Error while sending message to generator.
     */
    public static void turnGeneratorOn(Connection connection, String keyC) throws SQLException, IOException {
        String ip = GeneratorQuery.getIp(connection, keyC);

        GeneratorQuery.setStatus(connection, keyC, true);

        String url = buildUrl(connection, ip, keyC, true);

        request(url);
    }

    /**
     * Turn generator off (also update database).
     *
     * @param connection Connection to MySQL database.
     * @param keyC       Key of communication of the generator.
     * @throws SQLException Error with MySQL.
     * @throws IOException  Error while sending message to generator.
     */
    public static void turnGeneratorOff(Connection connection, String keyC) throws SQLException, IOException {
        GeneratorQuery.setStatus(connection, keyC, false);

        String ip = GeneratorQuery.getIp(connection, keyC);

        String url = buildUrl(connection, ip, keyC, false);

        request(url);
    }

    /**
     * Turn generator on and sends the timer length (also update database).
     *
     * @param connection Connection to MySQL database.
     * @param keyC       Key of communication of the generator.
     * @param timer      Timer of the generator to turn off.
     * @throws SQLException Error with MySQL.
     * @throws IOException  Error while sending message to generator.
     */
    public static void turnGeneratorOn(Connection connection, String keyC, long timer) throws SQLException, IOException {
        GeneratorQuery.setStatus(connection, keyC, true);

        String ip = GeneratorQuery.getIp(connection, keyC);

        String url = buildUrl(connection, ip, keyC, timer);

        request(url);
    }

    /**
     * Update decibel (in the database) and sends the decibel value to the ACC.
     *
     * @param connection Connection to MySQL database.
     * @param userId     User id.
     * @param decibel    Decibel value.
     * @throws SQLException Error with MySQL.
     * @throws IOException  Error while sending message to generator.
     */
    public static void updateDecibel(Connection connection, int userId, int decibel) throws SQLException, IOException {
        GeneratorQuery.setDecibel(connection, userId, decibel);

        String ip = GeneratorQuery.getIp(connection, userId);
        String keyC = GeneratorQuery.getKeyByUserId(connection, userId);

        String url = "http://" + ip + "/acc?key_c=" + keyC + "&decibel=" + decibel;

        request(url);
    }

    /**
     * Execute request.
     *
     * @param url Url to request.
     * @throws IOException Error while executing request.
     */
    private static void request(String url) throws IOException {
        HttpRequestor requestor = new HttpRequestor(url);
        requestor.executeRequest();
    }

    /**
     * Build url for request.
     *
     * @param address Address of the generator.
     * @param keyC    Key of communication of the generator.
     * @param on      True for turn on  the generator, false for turn it off.
     * @return Url for request.
     */
    private static String buildUrl(Connection connection, String address, String keyC, boolean on) throws SQLException {
        int frequence = getFrequence(connection, keyC);
        return "http://" + address + "/acc?key_c=" + keyC + "&generator=" + (on ? "1" : "0") + "&frequence=" + frequence;
    }

    /**
     * Turn on the request with timer.
     *
     * @param address Address of the generator.
     * @param keyC    Key of communication of the generator.
     * @param timer   Timer to shutdown the generator.
     * @return Url for request.
     */
    private static String buildUrl(Connection connection, String address, String keyC, long timer) throws SQLException {
        return buildUrl(connection, address, keyC, true) + "&timer=" + timer;
    }

    /**
     * Ge the frequence of the generator.
     *
     * @param connection Connection to MySQL database.
     * @param keyC       Key of communication.
     * @return Frequence of the genrator.
     */
    private static int getFrequence(Connection connection, String keyC) throws SQLException {
        return GeneratorQuery.getGeneratorFrequence(connection, keyC);
    }
}