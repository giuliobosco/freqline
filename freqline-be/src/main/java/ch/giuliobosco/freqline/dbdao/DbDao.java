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

import ch.giuliobosco.freqline.dao.Dao;
import ch.giuliobosco.freqline.dao.DaoException;
import ch.giuliobosco.freqline.jdbc.DaoQueryBuilder;
import ch.giuliobosco.freqline.jdbc.JdbcConnector;
import ch.giuliobosco.freqline.model.Base;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.logging.Logger;
import java.util.Date;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Base for MySQL Connection with DAO Pattern.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.2.5 (2019-09-17 - 2019-10-26)
 */
public abstract class DbDao extends Dao {

    // ------------------------------------------------------------------------------------ Costants

    /**
     * System logger.
     */
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(DbPermissionDao.class));

    /**
     * Null action by.
     */
    private static final int NULL_ACTION_BY = 1;

    // ---------------------------------------------------------------------------------- Attributes

    /**
     * Connection to MySQL database.
     */
    private Connection connection;

    /**
     * Dao Query Builder.
     */
    private DaoQueryBuilder daoQueryBuilder;

    /**
     * Action by.
     */
    private int actionBy;

    // -------------------------------------------------------------------------------- Constructors

    /**
     * Create Base for MySQL Connection with DAO Pattern, with connection to MySQL database and class type.
     *
     * @param jdbcConnector Connection to MySQL database.
     * @param baseClass     Class type.
     */
    public DbDao(JdbcConnector jdbcConnector, Class<? extends Base> baseClass) {
        this(jdbcConnector, baseClass, NULL_ACTION_BY);
    }

    /**
     * Create Base for MySQL Connection with DAO Pattern, with connection to MySQL database, class type and action by.
     *
     * @param jdbcConnector Connection to MySQL database.
     * @param baseClass     Class type.
     * @param actionBy      Action by.
     */
    public DbDao(JdbcConnector jdbcConnector, Class<? extends Base> baseClass, int actionBy) {
        this.connection = jdbcConnector.getConnection();
        this.daoQueryBuilder = jdbcConnector.getQueryBuilder(baseClass);
        this.actionBy = actionBy;
    }

    // --------------------------------------------------------------------------- Getters & Setters

    /**
     * Get the connection to MySQL database.
     *
     * @return Connection to MySQL database.
     */
    public Connection getConnection() {
        return this.connection;
    }

    /**
     * Get the action by.
     *
     * @return Action by.
     */
    public int getActionBy() {
        return this.actionBy;
    }

    // -------------------------------------------------------------------------------- Help Methods

    /**
     * Get a date from the result set with a column of timestamp.
     *
     * @param resultSet Result set from the query.
     * @param column    Timestamp column name.
     * @return Date if there is a value in the column, null if it's empty.
     * @throws SQLException Error in the result set.
     */
    protected Date getDate(ResultSet resultSet, String column) throws SQLException {
        if (resultSet != null) {
            Timestamp timestamp = resultSet.getTimestamp(column);
            if (timestamp != null) {
                return new Date(resultSet.getTimestamp(column).getTime());
            } else {
                return null;
            }
        } else {
            throw new SQLException("Connection closed");
        }
    }

    /**
     * Get SQL Timestamp from date.
     *
     * @param date Date to transform the Timestamp.
     * @return Date of the Timestamp (if there is, otherwise null).
     */
    protected Timestamp getTimestamp(Date date) {
        if (date != null) {
            return new Timestamp(date.getTime());
        } else {
            return null;
        }
    }

    /**
     * Muted close, write errors in logger as info.
     *
     * @param connection Connection to MySQL database to close.
     * @param statement  MySQL Prepared statement to close.
     * @param resultSet  MySQL Result Set to close.
     */
    private void mutedClose(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        try {
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException sqle) {
            LOGGER.info("Exception thrown " + sqle.getMessage());
        }
    }

    /**
     * Get the base element, from result set, result set column and dao.
     * From the id in the result set at the column get the element in dao.
     *
     * @param resultSet       Result set.
     * @param resultSetColumn Column name of the result set.
     * @param dao             Dao for execute query.
     * @return Base from result set.
     * @throws Exception Error with the MySQL.
     */
    protected Base getBase(ResultSet resultSet, String resultSetColumn, DbDao dao) throws Exception {
        Base base = null;

        Optional<Base> optionalBase = dao.getById(resultSet.getInt(resultSetColumn));

        if (optionalBase.isPresent()) {
            base = optionalBase.get();
        }

        return base;
    }

    // ---------------------------------------------------------------------------- Abstract Methods

    /**
     * Create the Entry form the result set and the base.
     *
     * @param resultSet Result set of the query.
     * @param base      Base.
     * @return Base entry.
     * @throws Exception SQL Exception.
     */
    protected abstract Base create(ResultSet resultSet, Base base) throws Exception;

    /**
     * Fill the statement with the base element.
     *
     * @param base      Base element.
     * @param statement Statement to fill.
     * @throws Exception SQL Exception.
     */
    protected abstract void fillStatement(Base base, PreparedStatement statement) throws Exception;

    // ----------------------------------------------------------------------------- General Methods

    /**
     * Create the base from the result set.
     *
     * @param resultSet Result set for create the date.
     * @return Base created from result set.
     * @throws SQLException Error while reading the statement.
     */
    private Base createBase(ResultSet resultSet) throws Exception {
        int id = resultSet.getInt(Base.ID);

        int createdBy = resultSet.getInt(Base.getCreatedByString());
        java.util.Date createdDate = getDate(resultSet, Base.getCreatedDateString());
        int updatedBy = resultSet.getInt(Base.getUpdatedByString());
        Date updatedDate = getDate(resultSet, Base.getUpdatedDateString());
        int deletedBy = resultSet.getInt(Base.getDeletedByString());
        Date deletedDate = getDate(resultSet, Base.getDeletedDateString());

        Base base = new Base(id, createdBy, createdDate, updatedBy, updatedDate, deletedBy, deletedDate) {
            @Override
            public int getId() {
                return super.getId();
            }
        };

        return create(resultSet, base);
    }

    /**
     * Fill the audit data in the statement.
     *
     * @param base      Base entry
     * @param statement Statement to fill.
     * @throws Exception SQL Exception.
     */
    private void setAuditData(Base base, PreparedStatement statement) throws Exception {
        // create
        statement.setInt(Base.CREATED_BY_INDEX, base.getCreatedBy());
        statement.setTimestamp(Base.CREATED_DATE_INDEX, getTimestamp(base.getCreatedDate()));
        // update
        statement.setInt(Base.UPDATED_BY_INDEX, base.getUpdatedBy());
        statement.setTimestamp(Base.UPDATED_DATE_INDEX, getTimestamp(base.getUpdatedDate()));
        // delete
        if (base.getDeletedBy() != 0) {
            statement.setInt(Base.DELETED_BY_INDEX, base.getDeletedBy());
        } else {
            statement.setObject(Base.DELETED_BY_INDEX, null);
        }
        statement.setTimestamp(Base.DELETED_DATE_INDEX, getTimestamp(base.getDeletedDate()));
    }

    /**
     * Get the <code>getById</code> prepared statement.
     *
     * @param id Id of the entry.
     * @return <code>getById</code> prepared statement.
     * @throws Exception SQL Exception.
     */
    protected PreparedStatement getByIdStatement(int id) throws Exception {
        String query = daoQueryBuilder.getByIdQuery();
        PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setInt(1, id);

        return statement;
    }

    /**
     * Get entry in database by ID.
     *
     * @param id Id of the entry.
     * @return Entry.
     * @throws Exception Error while executing the query.
     */
    public Optional<Base> getById(int id) throws Exception {
        ResultSet resultSet = null;

        try {
            PreparedStatement statement = getByIdStatement(id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(createBase(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException sqle) {
            throw new DaoException(sqle.getMessage(), sqle);
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }

    /**
     * Get the <code>getAll</code> prepared statement.
     *
     * @return <code>getAll</code> prepared statement.
     * @throws Exception SQL Exception.
     */
    protected PreparedStatement getAllStatement() throws Exception {
        return getConnection().prepareStatement(daoQueryBuilder.getAllQuery());
    }

    /**
     * Get all entries in database.
     *
     * @return All entries in database.
     * @throws Exception Error while executing the query.
     */
    public Stream<Base> getAll() throws Exception {
        PreparedStatement statement = getAllStatement();
        ResultSet resultSet = statement.executeQuery();

        return StreamSupport.stream(new Spliterators.AbstractSpliterator<Base>(Long.MAX_VALUE, Spliterator.ORDERED) {
            @Override
            public boolean tryAdvance(Consumer<? super Base> action) {
                try {
                    if (!resultSet.next()) {
                        return false;
                    }

                    action.accept(createBase(resultSet));
                    return true;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }, false).onClose(() -> mutedClose(getConnection(), statement, resultSet));
    }

    /**
     * Get the <code>add</code> prepared statement.
     *
     * @return <code>add</code> prepared statement.
     * @throws Exception SQL Exception.
     */
    protected PreparedStatement getAddStatement(Base base) throws Exception {
        String query = daoQueryBuilder.getAddQuery();
        PreparedStatement statement = getConnection().prepareStatement(query);

        setAuditData(base, statement);
        fillStatement(base, statement);

        return statement;
    }

    /**
     * Add entry to database.
     *
     * @param base Entry to add in the database.
     * @return True if added correctly.
     * @throws Exception Error while executing the query.
     */
    public boolean add(Base base) throws Exception {
        if (this.actionBy != NULL_ACTION_BY) {
            base.setCreatedBy(this.actionBy);
            base.setCreatedDate(Base.NOW);
            base.setUpdatedBy(this.actionBy);
            base.setUpdatedDate(Base.NOW);
        }

        if (getById(base.getId()).isPresent()) {
            return false;
        }

        PreparedStatement statement = getAddStatement(base);

        return !statement.execute();
    }

    /**
     * Get the <code>update</code> prepared statement.
     *
     * @return <code>update</code> prepared statement.
     * @throws Exception SQL Exception.
     */
    protected PreparedStatement getUpdateStatement(Base base) throws Exception {
        String query = daoQueryBuilder.getUpdateQuery();
        PreparedStatement statement = getConnection().prepareStatement(query);

        setAuditData(base, statement);
        fillStatement(base, statement);
        statement.setInt(daoQueryBuilder.getUpdateIdIndex(), base.getId());

        return statement;
    }

    /**
     * Update entry in database.
     *
     * @param base Entry to update in the database.
     * @return True if updated correctly in database.
     * @throws Exception Error while executing the query.
     */
    public boolean update(Base base) throws Exception {
        if (this.actionBy != NULL_ACTION_BY) {
            base.setUpdatedBy(this.actionBy);
            base.setUpdatedDate(Base.NOW);
        }

        try {
            PreparedStatement statement = getUpdateStatement(base);
            return statement.executeUpdate() > 0;
        } catch (SQLException sqle) {
            throw new DaoException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Get the <code>delete</code> prepared statement.
     *
     * @return <code>delete</code> prepared statement.
     * @throws Exception SQL Exception.
     */
    protected PreparedStatement getDeleteStatement(Base base) throws Exception {
        String query = daoQueryBuilder.getDeleteQuery();
        PreparedStatement statement = getConnection().prepareStatement(query);

        statement.setInt(1, base.getId());

        return statement;
    }

    /**
     * Delete entry in database.
     *
     * @param base Entry to delete in database.
     * @return True if deleted correctly in database.
     * @throws Exception Error while executing the query.
     */
    public boolean delete(Base base) throws Exception {
        try {
            if (this.actionBy != NULL_ACTION_BY) {
                base.setDeletedBy(this.actionBy);
                base.setDeletedDate(Base.NOW);
            }

            if (this.update(base)) {
                PreparedStatement statement = getDeleteStatement(base);
                return statement.executeUpdate() > 0;
            }

            return false;
        } catch (SQLException sqle) {
            throw new DaoException(sqle.getMessage(), sqle);
        }
    }
}
