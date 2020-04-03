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

package serial.acc.java;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Freqline server (test example).
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0 (2020-02-17 - 2020-02-17)
 */
public class FreqlineServer {
    // ------------------------------------------------------------------------------------ Costants

    /**
     * Http port.
     */
    private static final int HTTP_PORT = 8080;

    // ---------------------------------------------------------------------------------- Attributes

    /**
     * Serial thread.
     */
    private SerialThread serialThread;

    // -------------------------------------------------------------------------------- Constructors
    // --------------------------------------------------------------------------- Getters & Setters
    // -------------------------------------------------------------------------------- Help Methods

    /**
     * Start serial Thread.
     */
    private void startSerialThread() {
        this.serialThread = new SerialThread();
        this.serialThread.start();
    }

    // ----------------------------------------------------------------------------- General Methods

    /**
     * Start server.
     *
     * @throws Exception Error while starting server or opening serial port.
     */
    public void start() throws Exception {
        startSerialThread();
        Server server = new Server(HTTP_PORT);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        server.setHandler(context);

        context.addServlet(new ServletHolder(new SerialServlet(this.serialThread)), "/");

        server.start();
    }
}
