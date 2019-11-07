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
 * JAPI, User model.
 *
 * @author giuliobosco
 * @version 1.0 (2019-10-04 - 2019-10-04)
 */
public class User extends Base {

    // ---------------------------------------------------------------------------------- Attributes

    /**
     * Username of the user.
     */
    private String username;

    /**
     * Hashed password of the user.
     */
    private String password;

    /**
     * Salt for the hash of the password of the user.
     */
    private String salt;

    /**
     * First name of the user.
     */
    private String firstname;

    /**
     * Last name of the user.
     */
    private String lastname;

    /**
     * Email of the user.
     */
    private String email;

    // -------------------------------------------------------------------------------- Constructors

    /**
     * Create the user with all parameters.
     *
     * @param id          Id of the user.
     * @param createdBy   User created by.
     * @param createdDate User created date.
     * @param updatedBy   User updated by.
     * @param updatedDate User updated date.
     * @param deletedBy   User deleted by.
     * @param deletedDate user deleted date.
     * @param username    Username of the user.
     * @param password    Password hash of the user.
     * @param salt        Salt for the password of the user.
     * @param firstname   First name of the user.
     * @param lastname    Last name of the user.
     * @param email       Email of the user.
     */
    public User(int id, int createdBy, Date createdDate, int updatedBy, Date updatedDate, int deletedBy, Date deletedDate, String username, String password, String salt, String firstname, String lastname, String email) {
        super(id, createdBy, createdDate, updatedBy, updatedDate, deletedBy, deletedDate);

        this.username = username;
        this.password = password;
        this.salt = salt;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    /**
     * Create the user with all parameters, without the deleted by and deleted date.
     * If they are not instanced in the database, will not be instanced in the object.
     *
     * @param id          Id of the user.
     * @param createdBy   User created by.
     * @param createdDate User created date.
     * @param updatedBy   User updated by.
     * @param updatedDate User updated date.
     * @param username    Username of the user.
     * @param password    Password hash of the user.
     * @param salt        Salt for the password of the user.
     * @param firstname   First name of the user.
     * @param lastname    Last name of the user.
     * @param email       Email of the user.
     */
    public User(int id, int createdBy, Date createdDate, int updatedBy, Date updatedDate, String username, String password, String salt, String firstname, String lastname, String email) {
        this(id, createdBy, createdDate, updatedBy, updatedDate, SQL_INT_NULL, null, username, password, salt, firstname, lastname, email);
    }

    /**
     * Create the user, create model to write on DB.
     * Create an instance of User, with createdBy (same ad updatedBy) actual date for
     * createdDate and updatedDate. The id is 0, but it will be assigned by the DBMS.
     *
     * @param createdBy User created by.
     * @param username  Username of the user.
     * @param password  Password hash of the user.
     * @param salt      Salt for the password of the user.
     * @param firstname First name of the user.
     * @param lastname  Last name of the user.
     * @param email     Email of the user.
     */
    public User(int createdBy, String username, String password, String salt, String firstname, String lastname, String email) {
        this(0, createdBy, now(), createdBy, now(), username, password, salt, firstname, lastname, email);
    }

    /**
     * Create the user from a Base class and the username, password, salt , first name, last name and email of the user.
     *
     * @param base      Base data for create the user.
     * @param username  Username of the user.
     * @param password  Password hash of the user.
     * @param salt      Salt for the password of the user.
     * @param firstname First name of the user.
     * @param lastname  Last name of the user.
     * @param email     Email of the user.
     */
    public User(Base base, String username, String password, String salt, String firstname, String lastname, String email) {
        super(base);

        this.username = username;
        this.password = password;
        this.salt = salt;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    // --------------------------------------------------------------------------- Getters & Setters

    /**
     * Get the username of the user.
     *
     * @return Username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username of the user.
     *
     * @param username Username of the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the hashed password of the user.
     *
     * @return Hashed password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the hashed password of the user.
     *
     * @param password Hashed password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the salt for the hash of the password of the user.
     *
     * @return Salt for the hash of the password of the user.
     */
    public String getSalt() {
        return salt;
    }

    /**
     * Set the salt for the hash of the password of the user.
     *
     * @param salt salt for the hash of the password of the user.
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * Get the first name of the user.
     *
     * @return First name of the user.
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Set the first name of the user.
     *
     * @param firstname First name of the user.
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Get the last name of the user.
     *
     * @return last name of the user.
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Set the last name of the user.
     *
     * @param lastname Last name of the user.
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Get the email of the user.
     *
     * @return Email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email of the user.
     *
     * @param email Email of the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
