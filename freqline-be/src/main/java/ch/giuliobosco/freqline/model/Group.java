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
 * JAPI, groups model.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.1 (2019-09-26 - 2019-10-08)
 */
public class Group extends Base {

    // ---------------------------------------------------------------------------------- Attributes

    /**
     * Name of the group.
     */
    private String name;

    /**
     * Parent group of the group.
     */
    private Group parentGroup;

    // -------------------------------------------------------------------------------- Constructors

    /**
     * Create the group with all parameters.
     *
     * @param id          Id of the group.
     * @param createdBy   Id of the user who created.
     * @param createdDate Date of the creation.
     * @param updatedBy   Id of the user who did last update.
     * @param updatedDate Date of the last update.
     * @param deletedBy   Id of the user who deleted.
     * @param deletedDate Delete date.
     * @param name        Name of the group.
     * @param parentGroup Parent group of the group.
     */
    public Group(int id, int createdBy, Date createdDate, int updatedBy, Date updatedDate, int deletedBy, Date deletedDate, String name, Group parentGroup) {
        super(id, createdBy, createdDate, updatedBy, updatedDate, deletedBy, deletedDate);
        this.name = name;
        this.parentGroup = parentGroup;
    }

    /**
     * Create the group with all parameters with out the deletedBy and deletedDate.
     * If they are not instanced in the database, will not be instanced in the object.
     *
     * @param id          Id of the group.
     * @param createdBy   Id of the user who created.
     * @param createdDate Date of the creation.
     * @param updatedBy   Id of the user who did last update.
     * @param updatedDate Date of the last update.
     * @param name        Name of the group.
     * @param parentGroup Parent group of the group.
     */
    public Group(int id, int createdBy, Date createdDate, int updatedBy, Date updatedDate, String name, Group parentGroup) {
        this(id, createdBy, createdDate, updatedBy, updatedDate, SQL_INT_NULL, null, name, parentGroup);
    }

    /**
     * Create the group, create model to write on DB.
     * Create an instance of group, with createdBy (same ad updatedBy) actual date for
     * createdDate and updatedDate. The id is 0, but it will be assigned by the DBMS.
     *
     * @param createdBy   Id of the user who created.
     * @param name        Name of the group.
     * @param parentGroup Parent group of the group.
     */
    public Group(int createdBy, String name, Group parentGroup) {
        this(0, createdBy, new Date(System.currentTimeMillis()), createdBy, new Date(System.currentTimeMillis()), name, parentGroup);
    }

    /**
     * Create the group, create model to write on DB.
     * Create an instance of group, with createdBy (same ad updatedBy) actual date for
     * createdDate and updatedDate. The id is 0, but it will be assigned by the DBMS.
     *
     * @param createdBy Id of the user who created.
     * @param name      Name of the group.
     */
    public Group(int createdBy, String name) {
        this(createdBy, name, null);
    }

    /**
     * Create the group from a Base class, the name and the parent group of the group.
     *
     * @param base        Id of the user who created.
     * @param name        Name of the group.
     * @param parentGroup Parent group of the group.
     */
    public Group(Base base, String name, Group parentGroup) {
        super(base);
        this.name = name;
        this.parentGroup = parentGroup;
    }

    /**
     * Create the group from a Base class and the name of the group.
     *
     * @param base Id of the user who created.
     * @param name Name of the group.
     */
    public Group(Base base, String name) {
        this(base, name, null);
    }

    // --------------------------------------------------------------------------- Getters & Setters

    /**
     * Get the name of the group.
     *
     * @return Name of the group.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name of the group.
     *
     * @param name Name of the group.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the parent group of the group.
     *
     * @return Parent group of the group.
     */
    public Group getParentGroup() {
        return this.parentGroup;
    }

    /**
     * Set the parent group of the group.
     *
     * @param parentGroup Parent group of the group.
     */
    public void setParentGroup(Group parentGroup) {
        this.parentGroup = parentGroup;
    }
}
