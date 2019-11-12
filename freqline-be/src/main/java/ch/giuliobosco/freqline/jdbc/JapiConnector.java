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

package ch.giuliobosco.freqline.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Initialize a base connection.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0.4 (2019-09-16 - 2019-11-12)
 */
public class JapiConnector extends JdbcConnector {

    /**
     * Properties file path.
     */
    private static final String PROPERTIES_FILE_PATH = "dbe.properties";

    /**
     * Use ssl for connection.
     */
    private static final boolean SSL = false;

    /**
     * Create the japi connector, with properties file path.
     *
     * @throws IOException Error while reading properties file path.
     */
    public JapiConnector() throws IOException {
        super(PROPERTIES_FILE_PATH);
    }

    /**
     * Initialize jdbc connector.
     *
     * @return Jdbc connector.
     */
    public static JdbcConnector initialize() {
        try {
            return new JdbcConnector(PROPERTIES_FILE_PATH);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe.getMessage());
        }
    }

    /**
     * Get an open connection to the database.
     *
     * @return Open connection to MySQL database.
     * @throws SQLException           Error while connecting to MySQL database.
     * @throws ClassNotFoundException MySQL Driver not found.
     */
    public static Connection get() throws SQLException, ClassNotFoundException {
        return initialize().getOpenConnection();
    }
}
