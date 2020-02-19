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

import ch.giuliobosco.freqline.queries.GeneratorQuery;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Send message to Generator (ACC Protocol).
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0.2 (2019-11-18 - 2019-11-30)
 */
public class AccGenerator {
    // ------------------------------------------------------------------------------------ Costants

    /**
     * Default key.
     */
    public static final String KEY_C = "AAAAA";

    // --------------------------------------------------------------------------- Static Components

    /**
     * Turn generator on (also update database).
     *
     * @param connection Connection to MySQL database.
     * @param keyC       Key of communication of the generator.
     * @throws SQLException Error with MySQL.
     */
    public static void turnGeneratorOn(Connection connection, String keyC, SerialThread serialThread) throws SQLException {
        GeneratorQuery.setStatus(connection, keyC, true);
        serialThread.addCommand(SerialCommand.GENERATOR_ON);
    }

    /**
     * Turn generator off (also update database).
     *
     * @param connection Connection to MySQL database.
     * @param keyC       Key of communication of the generator.
     * @throws SQLException Error with MySQL.
     */
    public static void turnGeneratorOff(Connection connection, String keyC, SerialThread serialThread) throws SQLException {
        GeneratorQuery.setStatus(connection, keyC, false);
        serialThread.addCommand(SerialCommand.GENERATOR_OFF);
    }

    /**
     * Turn generator on and sends the timer length (also update database).
     *
     * @param connection Connection to MySQL database.
     * @param keyC       Key of communication of the generator.
     * @param timer      Timer of the generator to turn off.
     * @throws SQLException Error with MySQL.
     */
    public static void turnGeneratorOn(Connection connection, String keyC, long timer, SerialThread serialThread) throws SQLException {
        GeneratorQuery.setStatus(connection, keyC, true);
        serialThread.addCommand(SerialCommand.GENERATOR_ON);

        new MicThread(serialThread, timer).start();
    }

    /**
     * Update decibel (in the database) and sends the decibel value to the ACC.
     *
     * @param connection Connection to MySQL database.
     * @param userId     User id.
     * @param decibel    Decibel value.
     * @throws SQLException Error with MySQL.
     */
    public static void updateDecibel(Connection connection, int userId, int decibel, SerialThread serialThread) throws SQLException {
        GeneratorQuery.setDecibel(connection, userId, decibel);

        SerialCommand sc = SerialCommand.DECIBEL;
        sc.setMessage(String.valueOf(decibel).getBytes());
        serialThread.addCommand(sc);
    }

    /**
     * Update frequence (in the database) and sends the frequence value to the ACC.
     *
     * @param connection Connection to MySQL database.
     * @param userId     User id.
     * @param frequence  Frequence value.
     * @throws SQLException Error with MySQL.
     */
    public static void updateFrequence(Connection connection, int userId, int frequence, SerialThread serialThread) throws SQLException {
        GeneratorQuery.setFrequence(connection, userId, frequence);

        SerialCommand sc = SerialCommand.FREQUENCE;
        sc.setMessage(String.valueOf(frequence).getBytes());
        serialThread.addCommand(sc);
    }
}
