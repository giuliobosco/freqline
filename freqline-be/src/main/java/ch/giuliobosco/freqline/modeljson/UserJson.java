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
import ch.giuliobosco.freqline.model.User;

/**
 * Trasform the user object to JSON.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0.1 (2019-10-10 - 2019-10-26)
 */
public class UserJson extends BaseJson {

    // ------------------------------------------------------------------------------------ Costants

    /**
     * Username string.
     */
    public static final String USERNAME = "username";

    /**
     * Password string.
     */
    public static final String PASSWORD = "password";

    /**
     * Salt string.
     */
    public static final String SALT = "salt";

    /**
     * Firstname string.
     */
    public static final String FIRSTNAME = "firstname";

    /**
     * Lastname string.
     */
    public static final String LASTNAME = "lastname";

    /**
     * Email string
     */
    public static final String EMAIL = "email";

    // -------------------------------------------------------------------------------- Constructors

    /**
     * Create the user JSON from user object.
     *
     * @param user User object.
     */
    public UserJson(User user) {
        super(user);
    }

    /**
     * Create the user JSON form the base object parsed to User.
     *
     * @param base Base object (with also user attributes).
     */
    public UserJson(Base base) {
        super(base);
    }

    /**
     * Create the user json from JSON object.
     *
     * @param json Json object.
     */
    public UserJson(JSONObject json) {
        super(json);
    }

    /**
     * Create user json from json string.
     *
     * @param string Json string.
     */
    public UserJson(String string) {
        super(string);
    }

    // ----------------------------------------------------------------------------- General Methods

    /**
     * Get the user object.
     *
     * @param dao Not used.
     * @return User (as Base object)
     * @throws Exception SQL Exception.
     */
    @Override
    public Base get(DbDao dao) throws Exception {
        String username = getString(USERNAME);
        String password = getString(PASSWORD);
        String salt = getString(SALT);
        String firstname = getString(FIRSTNAME);
        String lastname = getString(LASTNAME);
        String email = getString(EMAIL);

        return new User(
                getBase(),
                username,
                password,
                salt,
                firstname,
                lastname,
                email
        );
    }


    /**
     * Set the user object.
     *
     * @param base Object.
     */
    @Override
    public void set(Base base) {
        User user = (User) base;

        getJson().put(USERNAME, user.getUsername());
        getJson().put(PASSWORD, user.getPassword());
        getJson().put(SALT, user.getSalt());
        getJson().put(FIRSTNAME, user.getFirstname());
        getJson().put(LASTNAME, user.getLastname());
        getJson().put(EMAIL, user.getEmail());
    }
}
