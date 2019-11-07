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

import ch.giuliobosco.freqline.help.ClassStringHelper;
import ch.giuliobosco.freqline.help.StringHelper;
import ch.giuliobosco.freqline.model.Base;

/**
 * Create the queries for DAO Pattern, with audit MySQL tables.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.1.3 (2019-09-27 - 2019-10-22)
 */
public class DaoQueryBuilder {

    /**
     * Database for queries.
     */
    private String database;

    /**
     * Table of the database for queries.
     */
    private String table;

    /**
     * Attributes of the table for queries.
     */
    private String[] attributes;

    /**
     * Create the Dao Query Builder with the database name and the model class.
     *
     * @param database  Database of the query builder.
     * @param baseClass Model class, for the representation of the table.
     */
    public DaoQueryBuilder(String database, Class<? extends Base> baseClass) {
        setDatabase(database);
        setTable(baseClass.getSimpleName());

        String[] attributes = ClassStringHelper.classFieldsNameToStringArray(baseClass);
        setAttributes(attributes);
    }

    /**
     * Set the database name.
     * Convert the database name in sql name.
     *
     * @param database Name of the database.
     */
    private void setDatabase(String database) {
        this.database = StringHelper.toSqlName(database);
    }

    /**
     * Set the table name.
     * Convert the table name in sql name.
     *
     * @param table Name of the table.
     */
    private void setTable(String table) {
        table = StringHelper.camelToSnake(table);
        this.table = StringHelper.toSqlName(table);
    }

    /**
     * Set hte attributes of the table.
     * Convert the attributes names in sql names.
     *
     * @param attributes Attributes of the table.
     */
    private void setAttributes(String[] attributes) {
        String[] attributesSqlName = new String[attributes.length];

        for (int i = 0; i < attributes.length; i++) {
            attributesSqlName[i] = StringHelper.toSqlName(attributes[i]);
        }

        this.attributes = attributesSqlName;
    }

    /**
     * Get the database and table in sql notation.
     * SQL notation: <code>database.table</code>.
     *
     * @return Database and table in sql notation.
     */
    private String getDatabaseTable() {
        return this.database + "." + this.table;
    }

    /**
     * Get the index of the attribute id in update query.
     *
     * @return Index of the attribute id in update query.
     */
    public int getUpdateIdIndex() {
        return Base.DEFAULT_ATTRIBUTES.length + attributes.length + 1;
    }

    /**
     * Get the <code>getAll</code> query.
     * Create the query with the database and the table.
     *
     * @return <code>getAll</code> query.
     */
    public String getAllQuery() {
        return "SELECT * FROM " + getDatabaseTable();
    }

    /**
     * Get the <code>getById</code> query.
     * Create the query with the database, the table and the id.
     *
     * @return <code>getById</code> query.
     */
    public String getByIdQuery() {
        return getAllQuery() + " WHERE `id`=?";
    }

    /**
     * Get the <code>getAdd</code> query.
     * Create the query with the database, the table, the defaults attributes and the attributes.
     *
     * @return <code>getAdd</code> query.
     */
    public String getAddQuery() {
        String query = "INSERT INTO " + getDatabaseTable() + "(";
        String values = "";

        for (String attribute : Base.DEFAULT_ATTRIBUTES) {
            query += attribute + ", ";
            values += "?, ";
        }

        for (int i = 0; i < attributes.length; i++) {
            query += attributes[i];
            values += "?";

            if (i < attributes.length - 1) {
                query += ", ";
                values += ", ";
            }
        }

        return query + ") VALUES (" + values + ")";
    }

    /**
     * Get the <code>getUpdate</code> query.
     * Create the query with the database, the table, the defaults attributes and the attributes.
     *
     * @return <code>getUpdate</code> query.
     */
    public String getUpdateQuery() {
        String query = "UPDATE " + getDatabaseTable() + " SET ";

        for (String attribute : Base.DEFAULT_ATTRIBUTES) {
            query += attribute + "=?, ";
        }

        for (int i = 0; i < attributes.length; i++) {
            query += attributes[i] + "=?";

            if (i < attributes.length - 1) {
                query += ", ";
            }
        }

        return query + " WHERE `id`=?";
    }

    /**
     * Get the <code>getDelete</code> query.
     * Create the query with the database, the table and the id.
     *
     * @return <code>getDelete</code> query.
     */
    public String getDeleteQuery() {
        return "DELETE FROM " + getDatabaseTable() + " WHERE `id`=?";
    }
}
