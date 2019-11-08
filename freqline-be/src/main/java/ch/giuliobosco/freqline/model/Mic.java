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

import java.sql.Timestamp;
import java.util.Date;

/**
 * Freqline, Mic model.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0 (2019-11-08 - 2019-11-08)
 */
public class Mic extends Base {
    // ---------------------------------------------------------------------------------- Attributes

    /**
     * Generator of the mic.
     */
    private Generator generator;

    /**
     * Decibel level of the mic.
     */
    private int decibel;

    /**
     * Timer for stop the generator.
     */
    private Timestamp timer;

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
     * Create the mic with all parameters.
     *
     * @param id          Id of the user.
     * @param createdBy   User created by.
     * @param createdDate User created date.
     * @param updatedBy   User updated by.
     * @param updatedDate User updated date.
     * @param deletedBy   User deleted by.
     * @param deletedDate user deleted date.
     * @param generator   Generator of the mic.
     * @param decibel     Decibel level of the mic.
     * @param timer       Timer for stop the generator.
     * @param ip          Ip of the mic.
     * @param keyC        Key of communication of the mic.
     */
    public Mic(int id, int createdBy, Date createdDate, int updatedBy, Date updatedDate, int deletedBy, Date deletedDate, Generator generator, int decibel, Timestamp timer, String ip, String keyC) {
        super(id, createdBy, createdDate, updatedBy, updatedDate, deletedBy, deletedDate);

        this.generator = generator;
        this.decibel = decibel;
        this.timer = timer;
        this.ip = ip;
        this.keyC = keyC;
    }

    /**
     * Create the mic with all parameters, without the deleted by and deleted date.
     * If they are not instanced in the database, will not be instanced in the object.
     *
     * @param id          Id of the user.
     * @param createdBy   User created by.
     * @param createdDate User created date.
     * @param updatedBy   User updated by.
     * @param updatedDate User updated date.
     * @param generator   Generator of the mic.
     * @param decibel     Decibel level of the mic.
     * @param timer       Timer for stop the generator.
     * @param ip          Ip of the mic.
     * @param keyC        Key of communication of the mic.
     */
    public Mic(int id, int createdBy, Date createdDate, int updatedBy, Date updatedDate, Generator generator, int decibel, Timestamp timer, String ip, String keyC) {
        this(id, createdBy, createdDate, updatedBy, updatedDate, SQL_INT_NULL, null, generator, decibel, timer, ip, keyC);
    }

    /**
     * Create the mic, create model to write on DB.
     * Create an instance of User, with createdBy (same ad updatedBy) actual date for
     * createdDate and updatedDate. The id is 0, but it will be assigned by the DBMS.
     *
     * @param createdBy User created by.
     * @param generator Generator of the mic.
     * @param decibel   Decibel level of the mic.
     * @param timer     Timer for stop the generator.
     * @param ip        Ip of the mic.
     * @param keyC      Key of communication of the mic.
     */
    public Mic(int createdBy, Generator generator, int decibel, Timestamp timer, String ip, String keyC) {
        this(SQL_INT_NULL, createdBy, now(), createdBy, now(), generator, decibel, timer, ip, keyC);
    }

    /**
     * Create the mic from a Base object and the decibel, the timer, the ip and the key of
     * communication.
     *
     * @param base      Base data for create the user.
     * @param generator Generator of the mic.
     * @param decibel   Decibel level of the mic.
     * @param timer     Timer for stop the generator.
     * @param ip        Ip of the mic.
     * @param keyC      Key of communication of the mic.
     */
    public Mic(Base base, Generator generator, int decibel, Timestamp timer, String ip, String keyC) {
        super(base);

        this.generator = generator;
        this.decibel = decibel;
        this.timer = timer;
        this.ip = ip;
        this.keyC = keyC;
    }

    // --------------------------------------------------------------------------- Getters & Setters

    /**
     * Get the generator of the mic.
     *
     * @return Generator of the mic.
     */
    public Generator getGenerator() {
        return generator;
    }

    /**
     * Set the generator of the mic.
     *
     * @param generator Generator of the mic.
     */
    public void setGenerator(Generator generator) {
        this.generator = generator;
    }

    /**
     * Get the decibel level of the mic.
     *
     * @return Decibel level of the mic.
     */
    public int getDecibel() {
        return decibel;
    }

    /**
     * Set the decibel level of the mic.
     *
     * @param decibel Decibel level of the mic.
     */
    public void setDecibel(int decibel) {
        this.decibel = decibel;
    }

    /**
     * Get the timer for stop the generator.
     *
     * @return Timer for stop the generator.
     */
    public Timestamp getTimer() {
        return timer;
    }

    /**
     * Set the timer for stop the generator.
     *
     * @param timer Timer for stop the generator.
     */
    public void setTimer(Timestamp timer) {
        this.timer = timer;
    }

    /**
     * Get the ip of the mic.
     *
     * @return Ip of the mic.
     */
    public String getIp() {
        return ip;
    }

    /**
     * Set the ip of the mic.
     *
     * @param ip Ip of the mic.
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Get the key of communication of the mic.
     *
     * @return Key of communication of the mic.
     */
    public String getKeyC() {
        return keyC;
    }

    /**
     * Set the key of communication of the mic.
     *
     * @param keyC Key of communication of the mic.
     */
    public void setKeyC(String keyC) {
        this.keyC = keyC;
    }
}
