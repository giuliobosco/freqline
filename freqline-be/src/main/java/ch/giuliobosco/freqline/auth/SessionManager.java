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

import ch.giuliobosco.freqline.model.User;

import javax.servlet.http.HttpSession;

/**
 * Session manager.
 *
 * @author giuliobosco
 * @version 1.0.1 (2019-10-16 - 2019-10-29)
 */
public class SessionManager {

    /**
     * Default max inactive interval.
     */
    private static final int MAX_INACTIVE_INTERVAL = 3600;

    /**
     * User string.
     */
    private static final String USER = "user";

    /**
     * User id string.
     */
    private static final String USER_ID = "user_id";

    /**
     * Http session.
     */
    private HttpSession session;

    /**
     * User id in the session.
     */
    private int userId;

    /**
     * Create session manager with session.
     *
     * @param session Session.
     */
    public SessionManager(HttpSession session) {
        this.session = session;
    }

    /**
     * Get the session user if the session is valid, otherwise returns null.
     *
     * @return Session user if the session is valid, otherwise returns null.
     */
    public User getUser() {
        if (!isValidSession()) {
            return null;
        }

        return (User) this.session.getAttribute(USER);
    }

    /**
     * Get the user id if the session is valid, otherwise returns -1.
     *
     * @return User id if the session is valid, otherwise returns -1.
     */
    public int getUserId() {
        if (!isValidSession()) {
            return -1;
        }

        return this.userId;
    }

    /**
     * Check if the session is valid.
     * If the session user, the session and the user id are seated in the session (the user id have to be an integer).
     *
     * @return True if the session is valid.
     */
    public boolean isValidSession() {
        if (this.session.getAttribute(USER_ID) != null) {
            try {
                String s = this.session.getAttribute(USER_ID).toString();
                this.userId = Integer.parseInt(s);
            } catch (NumberFormatException nfe) {
                return false;
            }
        } else {
            return false;
        }

        return this.session.getAttribute(USER) != null;
    }

    /**
     * Initialize the session with user and max inactive interval.
     *
     * @param user                Session user.
     * @param maxInactiveInterval Session max inactive interval.
     */
    public void initSession(User user, int maxInactiveInterval) {
        this.session.setMaxInactiveInterval(maxInactiveInterval);
        this.session.setAttribute(USER, user.getUsername());
        this.session.setAttribute(USER_ID, user.getId());
    }

    /**
     * Initialize the session with the user.
     *
     * @param user Session user.
     */
    public void initSession(User user) {
        initSession(user, MAX_INACTIVE_INTERVAL);
    }

    /**
     * Destroy the session.
     */
    public void destroySession() {
        this.session.invalidate();
    }
}
