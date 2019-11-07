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
 * @author giuliobosco
 * @version 1.0.2 (2019-10-03 - 2019-10-10)
 */
public class GroupPermission extends Base {

    // ---------------------------------------------------------------------------------- Attributes

    /**
     * Permission of the permission group.
     */
    private Permission permission;

    /**
     * Group of the permission group.
     */
    private Group group;

    // -------------------------------------------------------------------------------- Constructors

    /**
     * Create the permission group with all parameters.
     *
     * @param id          Id of the permission.
     * @param createdBy   Id of the user who created.
     * @param createdDate Date of the creation.
     * @param updatedBy   Id of the user who did last update.
     * @param updatedDate Date of the last update.
     * @param deletedBy   Id of the user who deleted.
     * @param deletedDate Delete date.
     * @param permission  Permission.
     * @param group       Group.
     */
    public GroupPermission(int id, int createdBy, Date createdDate, int updatedBy, Date updatedDate, int deletedBy, Date deletedDate, Permission permission, Group group) {
        super(id, createdBy, createdDate, updatedBy, updatedDate, deletedBy, deletedDate);
        this.permission = permission;
        this.group = group;
    }

    /**
     * Create the permission group with all parameters with out the deletedBy and deletedDate.
     * If they are not instanced in the database, will not be instanced in the object.
     *
     * @param id          Id of the permission.
     * @param createdBy   Id of the user who created.
     * @param createdDate Date of the creation.
     * @param updatedBy   Id of the user who did last update.
     * @param updatedDate Date of the last update.
     * @param permission  Permission.
     * @param group       Group.
     */
    public GroupPermission(int id, int createdBy, Date createdDate, int updatedBy, Date updatedDate, Permission permission, Group group) {
        this(id, createdBy, createdDate, updatedBy, updatedDate, SQL_INT_NULL, null, permission, group);
    }

    /**
     * Create the permission group, create the model to write on DB.
     * Create an instance of the permission group, with created By (same as updatedBy) actual date for
     * createdDate and updatedDate. The id is 0, but it will be assigned by the DBMS.
     *
     * @param createdBy  Id of the user who created.
     * @param permission Permission.
     * @param group      Group.
     */
    public GroupPermission(int createdBy, Permission permission, Group group) {
        this(0, createdBy, now(), createdBy, now(), permission, group);
    }

    /**
     * Create the permission group from a Base class the permission and the group.
     *
     * @param base       Base
     * @param permission Permission.
     * @param group      Group.
     */
    public GroupPermission(Base base, Permission permission, Group group) {
        super(base);
        this.permission = permission;
        this.group = group;
    }

    /**
     * Get the permission of the permission group.
     *
     * @return Permission of the permission group.
     */
    public Permission getPermission() {
        return this.permission;
    }

    /**
     * Set the permission of the permission group.
     *
     * @param permission Permission of the permission group.
     */
    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    /**
     * Get the group of the permission group.
     *
     * @return Group of the permission group.
     */
    public Group getGroup() {
        return this.group;
    }

    /**
     * Set the group of the permission group.
     *
     * @param permissionGroup Group of the permission group.
     */
    public void setGroup(Group permissionGroup) {
        this.group = permissionGroup;
    }
}
