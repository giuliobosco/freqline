/*
 * The MIT License
 *
 * Copyright 2020 giuliobosco.
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

package ch.giuliobosco.freqline.servlets.action;

import ch.giuliobosco.freqline.acc.SerialThread;
import ch.giuliobosco.freqline.servlets.BaseServlet;

/**
 * Serial servlet, abstract class for servlets requiring serial.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0 (2020-02-19 - 2020-02-19)
 */
public abstract class SerialServlet extends BaseServlet {
    // ---------------------------------------------------------------------------------- Attributes

    /**
     * Serial Thread.
     */
    private SerialThread serialThread;

    // -------------------------------------------------------------------------------- Constructors

    /**
     * Create serial servlet, with serial thread.
     *
     * @param serialThread Serial Thread.
     */
    public SerialServlet(SerialThread serialThread) {
        this.serialThread = serialThread;
    }

    // --------------------------------------------------------------------------- Getters & Setters

    /**
     * Get the serial thread.
     *
     * @return Serial threade.
     */
    public SerialThread getSerialThread() {
        return this.serialThread;
    }

    // -------------------------------------------------------------------------------- Help Methods

}
