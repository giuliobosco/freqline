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
import ch.giuliobosco.freqline.help.StringHelper;
import ch.giuliobosco.freqline.model.Base;
import ch.giuliobosco.freqline.model.Group;
import ch.giuliobosco.freqline.model.User;
import ch.giuliobosco.freqline.model.UserGroup;

/**
 * Transform User Group object to JSON and reverse.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0.4 (2019-09-18 - 2019-11-05)
 */
public class UserGroupJson extends BaseJson {

    // ------------------------------------------------------------------------------------ Costants

    /**
     * User string.
     */
    public static final String USER = "user";

    /**
     * Group string.
     */
    public static final String GROUP = "group";

    // -------------------------------------------------------------------------------- Constructors

    /**
     * Create user group json from the user group object.
     *
     * @param userGroup User group object.
     */
    public UserGroupJson(UserGroup userGroup) {
        super(userGroup);
    }

    /**
     * Create user group json from base object parsed to user group.
     *
     * @param base Base object (with also user group attributes).
     */
    public UserGroupJson(Base base) {
        super(base);
    }

    /**
     * Create the user group from json object.
     *
     * @param json Json object.
     */
    public UserGroupJson(JSONObject json) {
        super(json);
    }

    /**
     * Create user group from JSON string.
     *
     * @param string JSON string.
     */
    public UserGroupJson(String string) {
        super(string);
    }

    // ----------------------------------------------------------------------------- General Methods

    /**
     * Get the user group object.
     *
     * @param dao Dao, execute queries.
     * @return User group (as Base object).
     * @throws Exception SQL or DAO Exception.
     */
    @Override
    public Base get(DbDao dao) throws Exception {
        int userId = getInt(USER);
        int groupId = getInt(GROUP);

        return new UserGroup(
                getBase(),
                (User) getBase(dao, userId),
                (Group) getBase(dao, groupId)
        );
    }

    /**
     * Set the user group object.
     *
     * @param base Object.
     */
    @Override
    public void set(Base base) {
        UserGroup userGroup = (UserGroup) base;

        getJson().put(USER, userGroup.getUser().getId());
        getJson().put(GROUP, userGroup.getGroup().getId());
    }
}
