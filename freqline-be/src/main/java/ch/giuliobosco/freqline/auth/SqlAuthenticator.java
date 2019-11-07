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

package ch.giuliobosco.freqline.auth;

import ch.giuliobosco.freqline.dbdao.DbDao;
import ch.giuliobosco.freqline.dbdao.DbUserDao;
import ch.giuliobosco.freqline.help.Hasher;
import ch.giuliobosco.freqline.help.StringHelper;
import ch.giuliobosco.freqline.model.Base;
import ch.giuliobosco.freqline.model.User;
import ch.giuliobosco.freqline.queries.UserIdQuery;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Authentication module, for authenticate on MySQL database.
 *
 * @author giuliobosco
 * @version 1.0.3 (2019-10-15 - 2019-10-29)
 */
public class SqlAuthenticator implements Authenticator {

    /**
     * Salt default length.
     */
    private static final int SALT_LENGTH = 32;

    /**
     * User dao, with the connection to the database.
     */
    private DbUserDao dao;

    /**
     * Password hash in the database.
     */
    private String dbHash;

    /**
     * Salt for the password in database.
     */
    private String dbSalt;

    /**
     * Create MySQL Authenticator with UserDao.
     *
     * @param dao User dao, with the connection to the database.
     */
    public SqlAuthenticator(DbUserDao dao) {
        this.dao = dao;
    }

    /**
     * Authenticate the user.
     *
     * @param username Username for authentication.
     * @param password Password for authentication.
     * @return True if the username and password are right.
     * @throws Exception Error with MySQL database.
     */
    @Override
    public boolean autenticate(String username, String password) throws Exception {
        return authenticateUser(username, password) != null;
    }

    /**
     * Authenticate user.
     *
     * @param username Username for authentication.
     * @param password Password for authentication.
     * @return The user if authenticated, other wise null.
     * @throws Exception
     */
    public User authenticateUser(String username, String password) throws Exception {
        User user = findUser(username);

        if (findUser(username) == null) {
            return null;
        }

        password = Hasher.sha256Hex(password, this.dbSalt);

        if (!password.equals(this.dbHash)) {
            return null;
        }

        return user;
    }

    /**
     * Find user in database.
     * If it does found the user, it set the attributes dbHash and dbSalt.
     *
     * @param username Username of the user
     * @return True if user exists.
     * @throws Exception Error with MySQL.
     */
    private User findUser(String username) throws Exception {
        Stream<Base> stream = this.dao.getAll();

        int id = UserIdQuery.getUserId(this.dao.getConnection(), username);

        if (id == -1) {
            return null;
        }

        Optional<Base> optional = this.dao.getById(id);

        if (!optional.isPresent()) {
            return null;
        }

        User user = (User) optional.get();

        this.dbHash = user.getPassword();
        this.dbSalt = user.getSalt();

        return user;
    }

    /**
     * Get user to insert.
     * Create salt, execute hash of the password.
     *
     * @param user User to add salt and execute password hash.
     * @return User ready for insert in database.
     */
    public static User getUserToInsert(User user) {
        user.setSalt(StringHelper.randomString(SALT_LENGTH));
        String hash = Hasher.sha256Hex(user.getPassword(), user.getSalt());
        user.setPassword(hash);

        return user;
    }

    /**
     * Get the user to update with password update.
     *
     * @param user User to update.
     * @param dao  Dao for load user salt.
     * @return User to update with password update.
     * @throws Exception Error with MySQL.
     */
    public static User getUserToUpdatePassword(User user, DbDao dao) throws Exception {
        Optional<Base> optional = dao.getById(user.getId());

        if (!optional.isPresent()) {
            return null;
        }

        User dbUser = (User) optional.get();
        user.setSalt(dbUser.getSalt());
        String hash = Hasher.sha256Hex(user.getPassword(), user.getSalt());
        user.setPassword(hash);

        return user;
    }

    /**
     * Get the user to update.
     *
     * @param user User to update.
     * @param dao  Dao for load user salt and password.
     * @return User to update.
     * @throws Exception Error with MySQL.
     */
    public static User getUserToUpdate(User user, DbDao dao) throws Exception {
        Optional<Base> optional = dao.getById(user.getId());

        if (!optional.isPresent()) {
            return null;
        }

        User dbUser = (User) optional.get();
        user.setSalt(dbUser.getSalt());
        user.setPassword(dbUser.getPassword());

        return user;
    }
}
