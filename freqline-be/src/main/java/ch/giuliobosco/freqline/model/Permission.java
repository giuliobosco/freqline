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

import java.util.Date;

/**
 * JAPI, permission model.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0.5 (2019-09-15 - 2019-10-03)
 */
public class Permission extends Base {

    // ---------------------------------------------------------------------------------- Attributes

    /**
     * Name of the permission.
     */
    private String name;

    /**
     * String of the permission.
     * Used in the software, for a better user management, and permission management.
     */
    private String string;

    /**
     * Description of the permission.
     */
    private String description;

    // -------------------------------------------------------------------------------- Constructors

    /**
     * Create the permission with all parameters.
     *
     * @param id          Id of the permission.
     * @param createdBy   Id of the user who created.
     * @param createdDate Date of the creation.
     * @param updatedBy   Id of the user who did last update.
     * @param updatedDate Date of the last update.
     * @param deletedBy   Id of the user who deleted.
     * @param deletedDate Delete date.
     * @param name        Name of the permission.
     * @param string      String of the permission.
     * @param description Description of the permission.
     */
    public Permission(int id, int createdBy, Date createdDate, int updatedBy, Date updatedDate, int deletedBy, Date deletedDate, String name, String string, String description) {
        super(id, createdBy, createdDate, updatedBy, updatedDate, deletedBy, deletedDate);
        this.name = name;
        this.string = string;
        this.description = description;
    }

    /**
     * Create the permission with all parameters with out the deletedBy and deletedDate.
     * If they are not instanced in the database, will not be instanced in the object.
     *
     * @param id          Id of the permission.
     * @param createdBy   Id of the user who created.
     * @param createdDate Date of the creation.
     * @param updatedBy   Id of the user who did last update.
     * @param updatedDate Date of the last update.
     * @param name        Name of the permission.
     * @param string      String of the permission.
     * @param description Description of the permission.
     */
    public Permission(int id, int createdBy, Date createdDate, int updatedBy, Date updatedDate, String name, String string, String description) {
        this(id, createdBy, createdDate, updatedBy, updatedDate, SQL_INT_NULL, null, name, string, description);
    }

    /**
     * Create the permission, create model to write on DB.
     * Create an instance of Permission, with createdBy (same ad updatedBy) actual date for
     * createdDate and updatedDate. The id is 0, but it will be assigned by the DBMS.
     *
     * @param createdBy   Id of the user who created.
     * @param name        Name of the permission.
     * @param string      String of the permission.
     * @param description Description of the permission.
     */
    public Permission(int createdBy, String name, String string, String description) {
        this(0, createdBy, now(), createdBy, now(), name, string, description);
    }

    /**
     * Create the permission from a Base class and the name, string and description of permission.
     *
     * @param base        Base.
     * @param name        Name of the permission.
     * @param string      String of the permission.
     * @param description Description of the permission.
     */
    public Permission(Base base, String name, String string, String description) {
        super(base);
        this.name = name;
        this.string = string;
        this.description = description;
    }

    // --------------------------------------------------------------------------- Getters & Setters

    /**
     * Get the name of the permission.
     *
     * @return Name of the permission.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name of the permission.
     *
     * @param name Name of the permission.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the string of the permission.
     *
     * @return String of the permission.
     */
    public String getString() {
        return this.string;
    }

    /**
     * Set the string of the permission.
     *
     * @param string String of the permission.
     */
    public void setString(String string) {
        this.string = string;
    }

    /**
     * Get the description of the permission.
     *
     * @return Description of the permission.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Set the description of the permission.
     *
     * @param description Description of the permission.
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
