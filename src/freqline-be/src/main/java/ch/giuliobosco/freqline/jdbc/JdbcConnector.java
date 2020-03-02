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

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Manage connection to the databases with JDBC Driver.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.1.4 (2019-04-05 - 2019-03-02)
 */
public class JdbcConnector {
    // ------------------------------------------------------------------------------------ Costants

    /**
     * String for username.
     */
    private static final String S_USERNAME = "db.username";

    /**
     * String for password.
     */
    private static final String S_PASSWORD = "db.password";

    /**
     * String for host.
     */
    private static final String S_HOST = "db.host";

    /**
     * String for port.
     */
    private static final String S_PORT = "db.port";

    /**
     * String for database.
     */
    private static final String S_DATABASE = "db.database";

    /**
     * String for useSsl.
     */
    private static final String S_USE_SSL = "db.useSsl";

    /**
     * String for useUnicode.
     */
    private static final String S_USE_UNICODE = "db.useUnicode";

    /**
     * String for jdbcTimezoneShift.
     */
    private static final String S_JDBC_TIMEZONE_SHIFT = "db.jdbcTimezoneShift";

    /**
     * String for legacyDatetime.
     */
    private static final String S_LEGACY_DATETIME = "db.legacyDatetime";

    /**
     * String for serverTimezone.
     */
    private static final String S_SERVER_TIMEZONE = "db.serverTimezone";

    /**
     * String for zeroDateTimeBehavoir.
     */
    private static final String S_ZERO_TIME_BEHAVOIR = "db.zeroDateTimeBehavoir";

    /**
     * MySQL Default connection port.
     */
    public static final int PORT = 3306;

    /**
     * Use SSL Encryption default.
     */
    public static final boolean USE_SSL = true;

    /**
     * Use Unicode default.
     */
    public static final boolean USE_UNICODE = true;

    /**
     * Use JDBC Compliant Timezone Shift default.
     */
    public static final boolean JDBC_TIMEZONE_SHIFT = true;

    /**
     * Use Legacy Datetime Code default
     */
    public static final boolean LEGACY_DATETIME = false;

    /**
     * Default server timezone.
     */
    public static final String SERVER_TIMEZONE = "UTC";

    /**
     * Default Transform zero data.
     */
    public static final String ZERO_DATE_TIME_BEHAVOIR = "CONVERT_TO_NULL";

    // ---------------------------------------------------------------------------------- Attributes

    /**
     * Database username.
     */
    private String username;

    /**
     * Database password.
     */
    private String password;

    /**
     * Database server host name.
     */
    private String host;

    /**
     * Database server port.
     */
    private int port;

    /**
     * Database.
     */
    private String database;

    /**
     * Use SSL Encryption.
     */
    private boolean useSsl;

    /**
     * Use Unicode.
     */
    private boolean useUnicode;

    /**
     * Use JDBC Compliant Timezone Shift.
     */
    private boolean jdbcTimezoneShift;

    /**
     * Use Legacy Datetime Code.
     */
    private boolean legacyDatetime;

    /**
     * Server timezone.
     */
    private String serverTimezone;

    /**
     * Transform zero data.
     */
    private String zeroDateTimeBehavior;

    /**
     * Connection to the database.
     */
    private Connection connection;

    // --------------------------------------------------------------------------- Getters & Setters

    /**
     * Get the connection to the database.
     *
     * @return Connection to the database.
     */
    public Connection getConnection() {
        return this.connection;
    }

    /**
     * Get an open connection to the database.
     * If the connection is closed, it will be opened an other one.
     *
     * @return Open connection to the database.
     * @throws SQLException           Error while connecting to the database.
     * @throws ClassNotFoundException Mysql Driver class not found.
     */
    public Connection getOpenConnection() throws SQLException, ClassNotFoundException {
        if (this.connection == null || this.connection.isClosed()) {
            openConnection();
        }

        return getConnection();
    }

    /**
     * Get the query builder from the class.
     *
     * @param clazz Class for create the query builder.
     * @return Query builder, database and class (for table name and attributes).
     */
    public DaoQueryBuilder getQueryBuilder(Class clazz) {
        return new DaoQueryBuilder(this.database, clazz);
    }

    // -------------------------------------------------------------------------------- Constructors

    /**
     * Create the jdbc connector with username, password, database hostname server, server port and
     * database.
     *
     * @param username             Database username.
     * @param password             Database password.
     * @param host                 Database server host name.
     * @param port                 Database server port.
     * @param database             Database.
     * @param useSsl               Use SSL Encryption for the connection to database.
     * @param useUnicode           Use Unicode for the connection to database.
     * @param jdbcTimezoneShift    Use JDBC Compliant Timezone Shift for the connection to database.
     * @param legacyDatetime       Use Legacy Datetime Code for the connection to database.
     * @param serverTimezone       Server Timezone.
     * @param zeroDateTimeBehavior Transform zero data.
     */
    public JdbcConnector(String username, String password, String host, int port, String database, boolean useSsl, boolean useUnicode, boolean jdbcTimezoneShift, boolean legacyDatetime, String serverTimezone, String zeroDateTimeBehavior) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
        this.database = database;
        this.useSsl = useSsl;
        this.useUnicode = useUnicode;
        this.jdbcTimezoneShift = jdbcTimezoneShift;
        this.legacyDatetime = legacyDatetime;
        this.serverTimezone = serverTimezone;
        this.zeroDateTimeBehavior = zeroDateTimeBehavior;

        this.connection = null;
    }

    /**
     * Create the jdbc connector with username, password, database hostname server, server port and
     * database.
     *
     * @param username          Database username.
     * @param password          Database password.
     * @param host              Database server host name.
     * @param port              Database server port.
     * @param database          Database.
     * @param useSsl            Use SSL Encryption for the connection to database.
     * @param useUnicode        Use Unicode for the connection to database.
     * @param jdbcTimezoneShift Use JDBC Compliant Timezone Shift for the connection to database.
     * @param legacyDatetime    Use Legacy Datetime Code for the connection to database.
     * @param serverTimezone    Server Timezone.
     */
    public JdbcConnector(String username, String password, String host, int port, String database, boolean useSsl, boolean useUnicode, boolean jdbcTimezoneShift, boolean legacyDatetime, String serverTimezone) {
        this(username, password, host, port, database, useSsl, useUnicode, jdbcTimezoneShift, legacyDatetime, serverTimezone, ZERO_DATE_TIME_BEHAVOIR);
    }

    /**
     * Create the jdbc connector with username, password, database hostname server, server port and
     * database.
     *
     * @param username       Database username.
     * @param password       Database password.
     * @param host           Database server host name.
     * @param port           Database server port.
     * @param database       Database.
     * @param useSsl         Use SSL Encryption for the connection to database.
     * @param serverTimezone Server Timezone.
     */
    public JdbcConnector(String username, String password, String host, int port, String database, boolean useSsl, String serverTimezone) {
        this(username, password, host, port, database, useSsl, USE_UNICODE, JDBC_TIMEZONE_SHIFT, LEGACY_DATETIME, serverTimezone);
    }

    /**
     * Create the jdbc connector with username, password, database hostname server, server port and
     * database.
     *
     * @param username Database username.
     * @param password Database password.
     * @param host     Database server host name.
     * @param port     Database server port.
     * @param database Database.
     * @param useSsl   Use SSL Encryption for the connection to database.
     */
    public JdbcConnector(String username, String password, String host, int port, String database, boolean useSsl) {
        this(username, password, host, port, database, useSsl, SERVER_TIMEZONE);
    }

    /**
     * Create the jdbc connector with username, password, database hostname server, server port and
     * database.
     *
     * @param username       Database username.
     * @param password       Database password.
     * @param host           Database server host name.
     * @param port           Database server port.
     * @param database       Database.
     * @param serverTimezone Server Timezone.
     */
    public JdbcConnector(String username, String password, String host, int port, String database, String serverTimezone) {
        this(username, password, host, port, database, USE_SSL, serverTimezone);
    }

    /**
     * Create the jdbc connector with username, password, database hostname server, server port and
     * database.
     *
     * @param username Database username.
     * @param password Database password.
     * @param host     Database server host name.
     * @param port     Database server port.
     * @param database Database.
     */
    public JdbcConnector(String username, String password, String host, int port, String database) {
        this(username, password, host, port, database, USE_SSL);
    }

    /**
     * Create the jdbc connector with username, password, database hostname server, server port and
     * database.
     *
     * @param username Database username.
     * @param password Database password.
     * @param host     Database server host name.
     * @param database Database.
     * @param useSsl   Use SSL Encryption for the connection to database.
     */
    public JdbcConnector(String username, String password, String host, String database, boolean useSsl) {
        this(username, password, host, PORT, database, useSsl);
    }

    /**
     * Create the jdbc connector with username, password, database hostname server and database.
     * Using the MySQL default port (3306).
     *
     * @param username Database username.
     * @param password Database password.
     * @param host     Database server host name.
     * @param database Database.
     */
    public JdbcConnector(String username, String password, String host, String database) {
        this(username, password, host, PORT, database);
    }

    /**
     * Create the jdbc connector with properties file.
     *
     * @param propertiesFilePath Properties file path.
     * @throws IOException Error while reading the the properties file.
     */
    public JdbcConnector(String propertiesFilePath) throws IOException {

        InputStream propertiesFile = new FileInputStream(propertiesFilePath);
        Properties properties = new Properties();
        properties.load(propertiesFile);

        this.username = properties.getProperty(S_USERNAME);
        this.password = properties.getProperty(S_PASSWORD);
        this.host = properties.getProperty(S_HOST);
        try {
            this.port = Integer.parseInt(properties.getProperty(S_PORT));
        } catch (NumberFormatException nfe) {
            this.port = PORT;
        }
        this.database = properties.getProperty(S_DATABASE);
        this.useSsl = Boolean.parseBoolean(properties.getProperty(S_USE_SSL));
        this.useUnicode = Boolean.parseBoolean(properties.getProperty(S_USE_UNICODE));
        this.jdbcTimezoneShift = Boolean.parseBoolean(properties.getProperty(S_JDBC_TIMEZONE_SHIFT));
        this.legacyDatetime = Boolean.parseBoolean(properties.getProperty(S_LEGACY_DATETIME));
        this.serverTimezone = properties.getProperty(S_SERVER_TIMEZONE);
        this.zeroDateTimeBehavior = properties.getProperty(S_ZERO_TIME_BEHAVOIR);

        this.connection = null;
    }

    // -------------------------------------------------------------------------------- Help Methods

    /**
     * Prepare the connection string.
     * Create a string like <code>jdbc:mysql://host:port/database</code>
     *
     * @return Connection string.
     */
    private String getConnectionString() {
        return "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database;
    }

    /**
     * Prepare the properties for the connection to the database.
     * Properties with:
     * <ul>
     * <li>user</li>
     * <li>password</li>
     * <li>useSSL</li>
     * <li>useUnicode</li>
     * <li>useJDBCCompliantTimezoneShift</li>
     * <li>useLegacyCode</li>
     * <li>serverTimezone</li>
     * <li>zeroDateTimeBehavior</li>
     * </ul>
     *
     * @return Connection properties.
     */
    private Properties getConnectionProperties() {
        Properties properties = new Properties();

        properties.setProperty("user", this.username);
        properties.setProperty("password", this.password);
        properties.setProperty("useSSL", String.valueOf(this.useSsl));
        properties.setProperty("useUnicode", String.valueOf(this.useUnicode));
        properties.setProperty("useJDBCCompliantTimezoneShift", String.valueOf(this.jdbcTimezoneShift));
        properties.setProperty("useLegacyDatetimeCode", String.valueOf(this.legacyDatetime));
        properties.setProperty("serverTimezone", this.serverTimezone);
        properties.setProperty("zeroDateTimeBehavior", this.zeroDateTimeBehavior);

        return properties;
    }

    // ----------------------------------------------------------------------------- General Methods

    /**
     * Close the connection to the database.
     * Close the connection only if is open.
     */
    public void close() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException ignored) {

            }
            this.connection = null;
        }
    }

    /**
     * Open the connection to the MySQL database.
     *
     * @throws SQLException           Error while connecting to the database.
     * @throws ClassNotFoundException Mysql Driver class not found.
     */
    public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        this.connection = DriverManager.getConnection(
                getConnectionString(),
                getConnectionProperties()
        );
    }

    /**
     * Write the properties to the properties file.
     *
     * @param propertiesFilePath Properties file path.
     * @throws IOException Error while writing to the file.
     */
    public void writeProperties(String propertiesFilePath) throws IOException {
        OutputStream propertiesFile = new FileOutputStream(propertiesFilePath);
        Properties properties = new Properties();

        properties.setProperty(S_USERNAME, this.username);
        properties.setProperty(S_PASSWORD, this.password);
        properties.setProperty(S_HOST, this.host);
        properties.setProperty(S_PORT, String.valueOf(this.port));
        properties.setProperty(S_DATABASE, this.database);
        properties.setProperty(S_USE_SSL, String.valueOf(this.useSsl));
        properties.setProperty(S_USE_UNICODE, String.valueOf(this.useUnicode));
        properties.setProperty(S_JDBC_TIMEZONE_SHIFT, String.valueOf(this.jdbcTimezoneShift));
        properties.setProperty(S_LEGACY_DATETIME, String.valueOf(this.legacyDatetime));
        properties.setProperty(S_SERVER_TIMEZONE, this.serverTimezone);
        properties.setProperty(S_ZERO_TIME_BEHAVOIR, this.zeroDateTimeBehavior);

        properties.store(propertiesFile, null);

    }

}
