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

package ch.giuliobosco.freqline.dao;

/**
 * Error in dao pattern, at data layer.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0 (2019-09-16 - 2019-09-16)
 */
public class DaoException extends Exception {
    // ------------------------------------------------------------------------------------ Costants

    /**
     * Exception Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    // -------------------------------------------------------------------------------- Constructors

    /**
     * Create empty Dao Exception.
     */
    public DaoException() {
    }

    /**
     * Create Dao Exception with message.
     *
     * @param message Exception message.
     */
    public DaoException(String message) {
        super(message);
    }

    /**
     * Create Dao Exception with message and cause.
     *
     * @param message Exception message.
     * @param cause   Exception cause.
     */
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

}
