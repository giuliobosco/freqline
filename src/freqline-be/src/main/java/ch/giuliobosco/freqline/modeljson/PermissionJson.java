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
import ch.giuliobosco.freqline.model.Permission;

/**
 * Transform Permission object to JSON and reverse.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0.3 (2019-09-18 - 2019-10-26)
 */
public class PermissionJson extends BaseJson {
    // ------------------------------------------------------------------------------------ Costants

    /**
     * Name string.
     */
    public static final String NAME = "name";

    /**
     * String string.
     */
    public static final String STRING = "string";

    /**
     * Description string.
     */
    public static final String DESCRIPTION = "description";

    // -------------------------------------------------------------------------------- Constructors

    /**
     * Create permission JSON from permission object.
     *
     * @param permission Permission object.
     */
    public PermissionJson(Permission permission) {
        super(permission);
    }

    /**
     * Create permission JSON from base object parsed to permission.
     *
     * @param base Base object (with also permission attributes).
     */
    public PermissionJson(Base base) {
        this((Permission) base);
    }

    /**
     * Create permission from JSON object.
     *
     * @param json JSON object.
     */
    public PermissionJson(JSONObject json) {
        super(json);
    }

    /**
     * Create permission from JSON string.
     *
     * @param string JSON String.
     */
    public PermissionJson(String string) {
        super(string);
    }

    // -------------------------------------------------------------------------------- Help Methods// ----------------------------------------------------------------------------- General Methods

    /**
     * Get Permission object.
     *
     * @param dao Dao connection to database (not used).
     * @return Permission Object.
     */
    public Base get(DbDao dao) {
        return new Permission(
                getBase(),
                getString(NAME),
                getString(STRING),
                getString(DESCRIPTION)
        );
    }

    /**
     * Set the base object.
     *
     * @param base Object.
     */
    @Override
    public void set(Base base) {
        Permission permission = (Permission) base;

        getJson().put(NAME, permission.getName());
        getJson().put(STRING, permission.getString());
        getJson().put(DESCRIPTION, permission.getDescription());
    }
}
