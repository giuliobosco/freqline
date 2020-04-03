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
 * freqline, Remote model.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0 (2019-11-08 - 2019-11-08)
 */
public class Remote extends Base {
    // ---------------------------------------------------------------------------------- Attributes

    /**
     * Generator of the remote.
     */
    private Generator generator;

    /**
     * Command of the remote.
     */
    private String command;

    /**
     * Ip of the remote controller.
     */
    private String ip;

    /**
     * Key of communication of the remote controller.
     */
    private String keyC;

    // -------------------------------------------------------------------------------- Constructors

    /**
     * Create the remote with all parameters.
     *
     * @param id          Id of the user.
     * @param createdBy   User created by.
     * @param createdDate User created date.
     * @param updatedBy   User updated by.
     * @param updatedDate User updated date.
     * @param deletedBy   User deleted by.
     * @param deletedDate user deleted date.
     * @param generator   Generator of the remote.
     * @param command     Command of the remote.
     * @param ip          Ip of the remote controller.
     * @param keyC        Key of communication of the remote controller.
     */
    public Remote(int id, int createdBy, Date createdDate, int updatedBy, Date updatedDate, int deletedBy, Date deletedDate, Generator generator, String command, String ip, String keyC) {
        super(id, createdBy, createdDate, updatedBy, updatedDate, deletedBy, deletedDate);

        this.generator = generator;
        this.command = command;
        this.ip = ip;
        this.keyC = keyC;
    }

    /**
     * Create the remote with all parameters, without the deleted by and deleted date.
     * If they are not instanced in the database, will not be instanced in the object.
     *
     * @param id          Id of the user.
     * @param createdBy   User created by.
     * @param createdDate User created date.
     * @param updatedBy   User updated by.
     * @param updatedDate User updated date.
     * @param generator   Generator of the remote.
     * @param command     Command of the remote.
     * @param ip          Ip of the remote controller.
     * @param keyC        Key of communication of the remote controller.
     */
    public Remote(int id, int createdBy, Date createdDate, int updatedBy, Date updatedDate, Generator generator, String command, String ip, String keyC) {
        this(id, createdBy, createdDate, updatedBy, updatedDate, SQL_INT_NULL, null, generator, command, ip, keyC);
    }

    /**
     * Create the remote, create model to write on DB.
     * Create an instance of User, with createdBy (same ad updatedBy) actual date for
     * createdDate and updatedDate. The id is 0, but it will be assigned by the DBMS.
     *
     * @param createdBy User created by.
     * @param generator Generator of the remote.
     * @param command   Command of the remote.
     * @param ip        Ip of the remote controller.
     * @param keyC      Key of communication of the remote controller.
     */
    public Remote(int createdBy, Generator generator, String command, String ip, String keyC) {
        this(SQL_INT_NULL, createdBy, now(), createdBy, now(), generator, command, ip, keyC);
    }

    /**
     * Create the remote from a Base object and the generator, command, ip, keyC.
     *
     * @param base      Base data for create the user.
     * @param generator Generator of the remote.
     * @param command   Command of the remote.
     * @param ip        Ip of the remote controller.
     * @param keyC      Key of communication of the remote controller.
     */
    public Remote(Base base, Generator generator, String command, String ip, String keyC) {
        super(base);

        this.generator = generator;
        this.command = command;
        this.ip = ip;
        this.keyC = keyC;
    }

    // --------------------------------------------------------------------------- Getters & Setters

    /**
     * Get the generator of the remote.
     *
     * @return Generator of the remote.
     */
    public Generator getGenerator() {
        return generator;
    }

    /**
     * Set the generator of the remote.
     *
     * @param generator Generator of the remote.
     */
    public void setGenerator(Generator generator) {
        this.generator = generator;
    }

    /**
     * Get the command of the remote.
     *
     * @return Command of the remote.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Set the command of the remote.
     *
     * @param command Command of the remote.
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * Get the ip of the remote controller.
     *
     * @return Ip of the remote controller.
     */
    public String getIp() {
        return ip;
    }

    /**
     * Set the ip of the remote controller.
     *
     * @param ip Ip of the remote controller.
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Get the key of communication of the remote controller.
     *
     * @return Key of communication of the remote controller.
     */
    public String getKeyC() {
        return keyC;
    }

    /**
     * Set the key of communication of the remote controller.
     *
     * @param keyC Key of communication of the remote controller.
     */
    public void setKeyC(String keyC) {
        this.keyC = keyC;
    }
}
