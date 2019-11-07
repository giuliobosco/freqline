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

package ch.giuliobosco.freqline.model;

import ch.giuliobosco.freqline.help.StringArrayHelper;

import java.util.Date;

/**
 * Base Entity for DAO model.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0.9 (2019-09-17 - 2019-10-22)
 */
public abstract class Base {

    // ------------------------------------------------------------------------------------ Costants

    /**
     * Id string.
     */
    public static final String ID = "id";

    /**
     * Index of <code>created_by</code> attribute (in queries).
     */
    public static final int CREATED_BY_INDEX = 1;

    /**
     * Index of <code>created_date</code> attribute (in queries).
     */
    public static final int CREATED_DATE_INDEX = 2;

    /**
     * Index of <code>updated_by</code> attribute (in queries).
     */
    public static final int UPDATED_BY_INDEX = 3;

    /**
     * Index of <code>updated_date</code> attribute (in queries).
     */
    public static final int UPDATED_DATE_INDEX = 4;

    /**
     * Index of <code>deleted_by</code> attribute (in queries).
     */
    public static final int DELETED_BY_INDEX = 5;

    /**
     * Index of <code>deleted_date</code> attribute (in queries).
     */
    public static final int DELETED_DATE_INDEX = 6;

    /**
     * Default attributes for each table.
     */
    public static final String[] DEFAULT_ATTRIBUTES = {
            "created_by",
            "created_date",
            "updated_by",
            "updated_date",
            "deleted_by",
            "deleted_date"
    };

    /**
     * Value for SQL int null.
     */
    public static final int SQL_INT_NULL = 0;

    /**
     * Default user.
     * Used for creation of new entry, will be assigned by the DBMS.
     */
    private static final int DEFAULT_ID = 0;

    /**
     * NOW, this moment in UNIX Time.
     * Used for creation of new entry.
     */
    public static final Date NOW = new Date(System.currentTimeMillis());

    // ---------------------------------------------------------------------------------- Attributes

    /**
     * Id of the entry.
     */
    private int id;

    /**
     * Id of the user who created the entry.
     */
    private int createdBy;

    /**
     * Date of the creation of the entry.
     */
    private Date createdDate;

    /**
     * Id of the user who did last update of the entry.
     */
    private int updatedBy;

    /**
     * Date of the last update of the entry.
     */
    private Date updatedDate;

    /**
     * Id of the user who deleted the entry.
     */
    private int deletedBy;

    /**
     * Delete date of the entry.
     */
    private Date deletedDate;

    // -------------------------------------------------------------------------------- Constructors

    /**
     * Create the base entry with all parameters.
     *
     * @param id          Id of the entry.
     * @param createdBy   Id of the user who created the entry.
     * @param createdDate Date of the creation of the entry.
     * @param updatedBy   Id of the user who did last update of the entry.
     * @param updatedDate Date of the last update of the entry.
     * @param deletedBy   Id of the user who deleted the entry.
     * @param deletedDate Delete date of the entry.
     */
    public Base(int id, int createdBy, Date createdDate, int updatedBy, Date updatedDate, int deletedBy, Date deletedDate) {
        this.id = id;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.deletedBy = deletedBy;
        this.deletedDate = deletedDate;
    }

    /**
     * Create the base entry with all parameters but without deletedBy and deletedDate.
     * If they are not instanced in the database, will not be instanced in the object.
     *
     * @param id          Id of the entry.
     * @param createdBy   Id of the user who created the entry.
     * @param createdDate Date of the creation of the entry.
     * @param updatedBy   Id of the user who did last update of the entry.
     * @param updatedDate Date of the last update of the entry.
     */
    public Base(int id, int createdBy, Date createdDate, int updatedBy, Date updatedDate) {
        this(id, createdBy, createdDate, updatedBy, updatedDate, SQL_INT_NULL, null);
    }

    /**
     * Create the base entry, create the entry to write on DB.
     * Create an instance of the entry, with createdBy (same as updatedBy) actual date for
     * created Date and updatedDate. The id is 0, but it will be assigned by the DBMS.
     *
     * @param createdBy Id of the user who created the entry.
     */
    public Base(int createdBy) {
        this(DEFAULT_ID, createdBy, NOW, createdBy, NOW);
    }

    /**
     * Create a copy of the base passed as argument.
     *
     * @param base Base to copy.
     */
    public Base(Base base) {
        this(
                base.getId(),
                base.getCreatedBy(),
                base.getCreatedDate(),
                base.getUpdatedBy(),
                base.getUpdatedDate(),
                base.getDeletedBy(),
                base.getDeletedDate());
    }

    // --------------------------------------------------------------------------- Getters & Setters

    /**
     * Get the id of the entry.
     *
     * @return Id of the entry.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Set the id of the user who created the entry.
     *
     * @param createdBy Id of the user who created the entry.
     */
    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Get the id of the user who created the entry.
     *
     * @return Id of the user who created the entry.
     */
    public int getCreatedBy() {
        return createdBy;
    }

    /**
     * Set the date of the creation of the entry.
     *
     * @param createdDate Date of the creation of the entry.
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Get the date of the creation of the entry.
     *
     * @return Date of the creation of the entry.
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * Set the date of the creation of the entry.
     *
     * @param updatedBy Date of the creation of the entry.
     */
    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * Get the id of the user who did last update of the entry.
     *
     * @return Id of the user who did last update of the entry.
     */
    public int getUpdatedBy() {
        return updatedBy;
    }

    /**
     * Set the date of the last update of the entry.
     *
     * @param updatedDate Date of the last update of the entry.
     */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * Get the date of the last update of the entry.
     *
     * @return Date of the last update of the entry.
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * Set the id of the user who deleted the entry.
     *
     * @param deletedBy Id of the user who deleted the entry.
     */
    public void setDeletedBy(int deletedBy) {
        this.deletedBy = deletedBy;
    }

    /**
     * Get the id of the user who deleted the entry.
     *
     * @return Id of the user who deleted the entry.
     */
    public int getDeletedBy() {
        return deletedBy;
    }

    /**
     * Set the delete date of the entry.
     *
     * @param deletedDate Delete date of the entry.
     */
    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }

    /**
     * Get the delete date of the entry.
     *
     * @return Delete date of the entry.
     */
    public Date getDeletedDate() {
        return deletedDate;
    }

    // -------------------------------------------------------------------------------- Help Methods

    /**
     * Get now date.
     *
     * @return Now date.
     */
    protected static Date now() {
        return new Date(System.currentTimeMillis());
    }

    // ------------------------------------------------------------------------------ Static Methods

    /**
     * Get default attributes.
     *
     * @return Default attributes.
     */
    public static String[] getDefaultAttributes() {
        String[] s = {ID};
        return StringArrayHelper.concatenateArray(s, DEFAULT_ATTRIBUTES);
    }


    /**
     * Get the string from Attributes array by the index.
     * Transform SQL index to array index.
     *
     * @param index SQL Attribute index.
     * @return Attribute name String.
     */
    public static String getString(int index) {
        return DEFAULT_ATTRIBUTES[index - 1];
    }

    /**
     * Get the created by attribute name as string.
     *
     * @return Created by attribute name as string.
     */
    public static String getCreatedByString() {
        return getString(CREATED_BY_INDEX);
    }

    /**
     * Get the created date attribute name as string.
     *
     * @return Created date attribute name as string.
     */
    public static String getCreatedDateString() {
        return getString(CREATED_DATE_INDEX);
    }

    /**
     * Get the updated by attribute name as string.
     *
     * @return Updated by attribute name as string.
     */
    public static String getUpdatedByString() {
        return getString(UPDATED_BY_INDEX);
    }

    /**
     * Get the updated date attribute name as string.
     *
     * @return Updated date attribute name as string.
     */
    public static String getUpdatedDateString() {
        return getString(UPDATED_DATE_INDEX);
    }

    /**
     * Get the updated by attribute name as string.
     *
     * @return Updated by attribute name as string.
     */
    public static String getDeletedByString() {
        return getString(DELETED_BY_INDEX);
    }

    /**
     * Get the deleted date attribute name as string.
     *
     * @return Deleted date attribute name as string.
     */
    public static String getDeletedDateString() {
        return getString(DELETED_DATE_INDEX);
    }

}
