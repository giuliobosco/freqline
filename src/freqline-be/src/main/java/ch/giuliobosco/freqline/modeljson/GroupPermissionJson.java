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

package ch.giuliobosco.freqline.modeljson;

import org.json.JSONObject;
import ch.giuliobosco.freqline.dbdao.DbDao;
import ch.giuliobosco.freqline.model.Base;
import ch.giuliobosco.freqline.model.Group;
import ch.giuliobosco.freqline.model.GroupPermission;
import ch.giuliobosco.freqline.model.Permission;

/**
 * Transform group object to JSON.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0.2 (2019-10-10 - 2019-11-05)
 */
public class GroupPermissionJson extends BaseJson {

    // ------------------------------------------------------------------------------------ Costants

    /**
     * Group string.
     */
    public static final String GROUP = "group";

    /**
     * Permission string.
     */
    public static final String PERMISSION = "permission";

    // -------------------------------------------------------------------------------- Constructors

    /**
     * Create group permission JSON from group permission object.
     *
     * @param groupPermission Group Permission object.
     */
    public GroupPermissionJson(GroupPermission groupPermission) {
        super(groupPermission);
    }

    /**
     * Create group permission JSON from base object parsed to Group Permission.
     *
     * @param base Base object (with also group permission attributes).
     */
    public GroupPermissionJson(Base base) {
        this((GroupPermission) base);
    }

    /**
     * Create group permission from JSON object.
     *
     * @param json JSON object.
     */
    public GroupPermissionJson(JSONObject json) {
        super(json);
    }

    /**
     * Create group permission from JSON string.
     *
     * @param string JSON string.
     */
    public GroupPermissionJson(String string) {
        super(string);
    }

    // ----------------------------------------------------------------------------- General Methods

    /**
     * Get the permission object.
     *
     * @param dao Dao, execute queries.
     * @return Group permission (as Base object).
     * @throws Exception SQL or DAO Exception.
     */
    @Override
    public Base get(DbDao dao) throws Exception {
        int groupId = getInt(GROUP);
        int permissionId = getInt(PERMISSION);

        return new GroupPermission(
                getBase(),
                (Permission) getBase(dao, permissionId),
                (Group) getBase(dao, groupId)
        );
    }

    /**
     * Set the group permission object.
     *
     * @param base Object.
     */
    @Override
    public void set(Base base) {
        GroupPermission groupPermission = (GroupPermission) base;

        getJson().put(GROUP, groupPermission.getGroup().getId());
        getJson().put(PERMISSION, groupPermission.getPermission().getId());
    }
}
