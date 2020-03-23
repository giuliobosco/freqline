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

import ch.giuliobosco.freqline.jdbc.JapiConnector;
import ch.giuliobosco.freqline.jdbc.JdbcConnector;
import ch.giuliobosco.freqline.model.Base;
import ch.giuliobosco.freqline.model.Generator;
import ch.giuliobosco.freqline.model.Mic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0.1 (2019-11-12 - 2019-11-26)
 */
public class DbMicDao extends DbDao {

    public DbMicDao(JdbcConnector connector) {
        super(connector, Mic.class);
    }

    public DbMicDao(JdbcConnector connector, int actionBy) {
        super(connector, Mic.class, actionBy);
    }

    /**
     * Get the Generator from the result set.
     *
     * @param resultSet Result set.
     * @param connector New connection opened to MySQL Database.
     * @return Genrator from the result set.
     * @throws Exception SQL Exception.
     */
    private Generator getGenerator(ResultSet resultSet, JdbcConnector connector) throws Exception {
        DbGeneratorDao dao = new DbGeneratorDao(connector);
        String resultSetColumn = "generator";

        return (Generator) getBase(resultSet, resultSetColumn, dao);
    }

    /**
     * Create the mic object from the result set and base entity.
     *
     * @param resultSet Result set of the query.
     * @param base      Base.
     * @return Group object created from result set.
     * @throws Exception SQL Exception.
     */
    @Override
    protected Base create(ResultSet resultSet, Base base) throws Exception {
        JdbcConnector connector = JapiConnector.initialize();
        connector.openConnection();

        Generator generator = getGenerator(resultSet, connector);
        int decibel = resultSet.getInt("decibel");
        int timer = resultSet.getInt("timer");
        String ip = resultSet.getString("ip");
        String keyC = resultSet.getString("key_c");

        return new Mic(
                base,
                generator,
                decibel,
                timer,
                ip,
                keyC
        );
    }

    /**
     * Fill prepared statement with mic object.
     *
     * @param base      Base element.
     * @param statement Statement to fill.
     * @throws Exception Error in MySQL.
     */
    @Override
    protected void fillStatement(Base base, PreparedStatement statement) throws Exception {
        Mic mic = (Mic) base;
        Generator g = mic.getGenerator();

        if (g != null) {
            statement.setInt(7, g.getId());
        } else {
            statement.setObject(7, null);
        }

        statement.setInt(8, mic.getDecibel());
        statement.setInt(9, mic.getTimer());
        statement.setString(10, mic.getIp());
        statement.setString(11, mic.getKeyC());
    }
}
