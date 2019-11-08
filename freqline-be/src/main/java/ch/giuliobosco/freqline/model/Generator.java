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
 * Freqline, Generator model.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0 (2019-11-08 - 2019-11-08)
 */
public class Generator extends Base {

    // ---------------------------------------------------------------------------------- Attributes

    /**
     * Name of the generator.
     */
    private String name;

    /**
     * Frequence of the generator.
     */
    private int frequence;

    /**
     * Status of the generator.
     */
    private boolean status;

    /**
     * Ip of the generator.
     */
    private String ip;

    /**
     * Key of communication of the generator.
     */
    private String keyC;

    // -------------------------------------------------------------------------------- Constructors

    /**
     * Create the generator with all parameters.
     *
     * @param id          Id of the user.
     * @param createdBy   User created by.
     * @param createdDate User created date.
     * @param updatedBy   User updated by.
     * @param updatedDate User updated date.
     * @param deletedBy   User deleted by.
     * @param deletedDate user deleted date.
     * @param name        Name of the generator.
     * @param frequence   Frequence of the generator.
     * @param status      Status of the generator.
     * @param ip          Ip of the generator.
     * @param keyC        Key of communication of the generator.
     */
    public Generator(int id, int createdBy, Date createdDate, int updatedBy, Date updatedDate, int deletedBy, Date deletedDate, String name, int frequence, boolean status, String ip, String keyC) {
        super(id, createdBy, createdDate, updatedBy, updatedDate, deletedBy, deletedDate);

        this.name = name;
        this.frequence = frequence;
        this.status = status;
        this.ip = ip;
        this.keyC = keyC;
    }

    /**
     * Create the generator with all parameters, without the deleted by and deleted date.
     * If they are not instanced in the database, will not be instanced in the object.
     *
     * @param id          Id of the user.
     * @param createdBy   User created by.
     * @param createdDate User created date.
     * @param updatedBy   User updated by.
     * @param updatedDate User updated date.
     * @param name        Name of the generator.
     * @param frequence   Frequence of the generator.
     * @param status      Status of the generator.
     * @param ip          Ip of the generator.
     * @param keyC        Key of communication of the generator.
     */
    public Generator(int id, int createdBy, Date createdDate, int updatedBy, Date updatedDate, String name, int frequence, boolean status, String ip, String keyC) {
        this(id, createdBy, createdDate, updatedBy, updatedDate, SQL_INT_NULL, null, name, frequence, status, ip, keyC);
    }

    /**
     * Create the generator, create model to write on Db.
     * Create an instance of User, with createdBy (same and updatedBy) actual date for
     * createdDate and updatedDate. The id is 0, but it will be assigned by the DBMS.
     *
     * @param createdBy User created by.
     * @param name      Name of the generator.
     * @param frequence Frequence of the generator.
     * @param status    Status of the generator.
     * @param ip        Ip of the generator.
     * @param keyC      Key of communication of the generator.
     */
    public Generator(int createdBy, String name, int frequence, boolean status, String ip, String keyC) {
        this(SQL_INT_NULL, createdBy, now(), createdBy, now(), name, frequence, status, ip, keyC);
    }

    /**
     * Create the generator from a Base object and name, frequence, status, ip, key of communication.
     *
     * @param base      Base data for create the generator.
     * @param name      Name of the generator.
     * @param frequence Frequence of the generator.
     * @param status    Status of the generator.
     * @param ip        Ip of the generator.
     * @param keyC      Key of communication of the generator.
     */
    public Generator(Base base, String name, int frequence, boolean status, String ip, String keyC) {
        super(base);

        this.name = name;
        this.frequence = frequence;
        this.status = status;
        this.ip = ip;
        this.keyC = keyC;
    }

    // --------------------------------------------------------------------------- Getters & Setters

    /**
     * Get the name of the generator.
     *
     * @return Name of the generator.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the generator.
     *
     * @param name Name of the generator.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the frequence of the generator.
     *
     * @return Frequence of the generator.
     */
    public int getFrequence() {
        return frequence;
    }

    /**
     * Set the frequence of the generator.
     *
     * @param frequence Frequence of the generator.
     */
    public void setFrequence(int frequence) {
        this.frequence = frequence;
    }

    /**
     * Get the status of the generator.
     *
     * @return Status of the generator.
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * Set the status of the generator.
     *
     * @param status Status of the generator.
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Get the ip of the generator.
     *
     * @return Ip of the generator.
     */
    public String getIp() {
        return ip;
    }

    /**
     * Set the ip of the generator.
     *
     * @param ip Ip of the generator.
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Get the key of communication of the generator.
     *
     * @return Key of communication of the generator.
     */
    public String getKeyC() {
        return keyC;
    }

    /**
     * Set the key of communication of the generator.
     *
     * @param keyC Key of communication of the generator.
     */
    public void setKeyC(String keyC) {
        this.keyC = keyC;
    }

}
