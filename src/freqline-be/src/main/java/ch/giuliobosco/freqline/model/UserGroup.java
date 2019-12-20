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
 * JAPI, user group model.
 *
 * @author giuliobosco
 * @version 1.0 (2019-10-09 - 2019-10-09)
 */
public class UserGroup extends Base {

    // ---------------------------------------------------------------------------------- Attributes

    /**
     * User of the user group.
     */
    private User user;

    /**
     * Group of the user group.
     */
    private Group group;

    // -------------------------------------------------------------------------------- Constructors

    /**
     * Create the user group with all parameters.
     *
     * @param id          Id of the permission.
     * @param createdBy   User group created by.
     * @param createdDate User group created date.
     * @param updatedBy   User group updated by.
     * @param updatedDate User group updated date.
     * @param deletedBy   User group deleted by.
     * @param deletedDate User group deleted date.
     * @param user        User of the user group.
     * @param group       Group of the user group.
     */
    public UserGroup(int id, int createdBy, Date createdDate, int updatedBy, Date updatedDate, int deletedBy, Date deletedDate, User user, Group group) {
        super(id, createdBy, createdDate, updatedBy, updatedDate, deletedBy, deletedDate);

        this.user = user;
        this.group = group;
    }

    /**
     * Create the user group with all parameters with out the deletedBy and deletedDate.
     * If they are not instanced in the database, will not be instanced in the object.
     *
     * @param id          Id of the permission.
     * @param createdBy   User group created by.
     * @param createdDate User group created date.
     * @param updatedBy   User group updated by.
     * @param updatedDate User group updated date.
     * @param user        User of the user group.
     * @param group       Group of the user group.
     */
    public UserGroup(int id, int createdBy, Date createdDate, int updatedBy, Date updatedDate, User user, Group group) {
        this(id, createdBy, createdDate, updatedBy, updatedDate, SQL_INT_NULL, null, user, group);
    }

    /**
     * Create the user group, create the model to write on DB.
     * Create an instance of the permission group, with created By (same as updatedBy) actual date for
     * createdDate and updatedDate. The id is 0, but it will be assigned by the DBMS.
     *
     * @param createdBy Id of the user who created.
     * @param user      User of the user group.
     * @param group     Group of the user group.
     */
    public UserGroup(int createdBy, User user, Group group) {
        this(SQL_INT_NULL, createdBy, now(), createdBy, now(), user, group);
    }

    /**
     * Create the user group from a Base class the user and the group.
     *
     * @param base  Base data for create the user group.
     * @param user  User of the user group.
     * @param group Group of the user group.
     */
    public UserGroup(Base base, User user, Group group) {
        super(base);

        this.user = user;
        this.group = group;
    }

    /**
     * Get the user of the user group.
     *
     * @return User of the user group.
     */
    public User getUser() {
        return this.user;
    }

    /**
     * Set the user of the user group.
     *
     * @param user User of the user group.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Get the group of the user group.
     *
     * @return Group of the user group.
     */
    public Group getGroup() {
        return this.group;
    }

    /**
     * Set the group of the user group.
     *
     * @param group Group of the user group.
     */
    public void setGroup(Group group) {
        this.group = group;
    }
}
