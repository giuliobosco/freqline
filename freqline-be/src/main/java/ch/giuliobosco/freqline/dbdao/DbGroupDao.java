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
import ch.giuliobosco.freqline.model.Group;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

/**
 * Group to MySQL in DAO pattern.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.1.1 (2019-09-27 - 2019-10-17)
 */
public class DbGroupDao extends DbDao {

    /**
     * Create the DbGroupDao with the connection to MySQL Database.
     * Use Group query builder.
     *
     * @param connection Connection to MySQL database.
     */
    public DbGroupDao(JdbcConnector connection) {
        super(connection, Group.class);
    }

    /**
     * Create the DbGroupDao with the connection to MySQL Database.
     * Use Group query builder.
     *
     * @param connector Connection to MySQL database.
     * @param actionBy  Action By.
     */
    public DbGroupDao(JdbcConnector connector, int actionBy) {
        super(connector, Group.class, actionBy);
    }

    /**
     * Create the groups object from the result set and base entity.
     *
     * @param resultSet Result set of the query.
     * @param base      Base.
     * @return Group object created from result set.
     * @throws Exception SQL Exception.
     */
    protected Base create(ResultSet resultSet, Base base) throws Exception {
        String name = resultSet.getString("name");
        Group parentGroup = null;

        Optional<Base> optionalBase = this.getById(resultSet.getInt("parent_group"));

        if (optionalBase.isPresent()) {
            parentGroup = (Group) optionalBase.get();
        }

        return new Group(base, name, parentGroup);

    }

    /**
     * Fill the prepared statement from the base (have to be a base with groups attributes).
     *
     * @param base      Base element.
     * @param statement Statement to fill.
     * @throws Exception SQL Exception.
     */
    protected void fillStatement(Base base, PreparedStatement statement) throws Exception {
        Group group = (Group) base;
        Group parentGroup = group.getParentGroup();

        statement.setString(7, group.getName());
        if (parentGroup != null) {
            statement.setInt(8, parentGroup.getId());
        } else {
            statement.setObject(8, null);
        }
    }
}
