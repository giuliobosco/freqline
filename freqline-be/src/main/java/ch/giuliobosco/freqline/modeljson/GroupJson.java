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


/**
 * Transform groups object to JSON and reverse.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.1.3 (2019-09-26 - 2019-10-26)
 */
public class GroupJson extends BaseJson {

    // ------------------------------------------------------------------------------------ Costants

    /**
     * Name string.
     */
    public static final String NAME = "name";

    /**
     * Parent group id name.
     */
    public static final String PARENT_GROUP = "parentGroup";

    // -------------------------------------------------------------------------------- Constructors

    /**
     * Create group JSON from group object.
     *
     * @param group Group object.
     */
    public GroupJson(Group group) {
        super(group);
    }

    /**
     * Create group JSON from base object parsed to group.
     *
     * @param base Base object (with also group attributes).
     */
    public GroupJson(Base base) {
        this((Group) base);
    }

    /**
     * Create group from JSON object.
     *
     * @param json JSON object.
     */
    public GroupJson(JSONObject json) {
        super(json);
    }

    /**
     * Create group from JSON string.
     *
     * @param string JSON string.
     */
    public GroupJson(String string) {
        super(string);
    }

    // ----------------------------------------------------------------------------- General Methods

    /**
     * Get group object.
     * If the id of the parent group is 0, it will be set at null.
     *
     * @param dao Dao connection to database.
     * @return Group Object.
     * @throws Exception Error with mysql, dao or not existing parent group.
     */
    @Override
    public Base get(DbDao dao) throws Exception {
        String name = getString(NAME);
        int parentGroupId = getInt(PARENT_GROUP);

        return new Group(
                getBase(),
                name,
                (Group) getBase(dao, parentGroupId)
        );
    }

    /**
     * Set the group object.
     *
     * @param base Object.
     */
    @Override
    public void set(Base base) {
        Group group = (Group) base;

        getJson().put(NAME, group.getName());

        if (group.getParentGroup() != null) {
            getJson().put(PARENT_GROUP, group.getParentGroup().getId());
        }
    }
}
