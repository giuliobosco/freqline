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

package ch.giuliobosco.freqline;

import ch.giuliobosco.freqline.acc.SerialThread;
import ch.giuliobosco.freqline.servlets.BaseServlet;
import ch.giuliobosco.freqline.servlets.action.*;
import ch.giuliobosco.freqline.servlets.data.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Freqline server (test example).
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0.1 (2020-02-17 - 2020-02-19)
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

    // -------------------------------------------------------------------------------- Help Methods

    /**
     * Start serial Thread.
     */
    private void startSerialThread() {
        this.serialThread = new SerialThread();
        this.serialThread.start();
    }

    /**
     * Load project servlets.
     *
     * @param context Context.
     */
    private void loadServlets(ServletContextHandler context) {
        // action servlets
        addServlet(context, new GeneratorDecibelServlet(this.serialThread));
        addServlet(context, new GeneratorFrequenceServlet(this.serialThread));
        addServlet(context, new GeneratorMicServlet());
        addServlet(context, new GeneratorStatusServlet(this.serialThread));
        addServlet(context, new LoginServlet());

        // data servlets
        addServlet(context, new GeneratorServlet());
        addServlet(context, new GroupPermissionServlet());
        addServlet(context, new GroupServlet());
        addServlet(context, new MicServlet());
        addServlet(context, new PermissionServlet());
        addServlet(context, new RemoteServlet());
        addServlet(context, new UserGroupServlet());
        addServlet(context, new UserServlet());
    }

    /**
     * Add servlet to context, with servlet path.
     *
     * @param context     Context.
     * @param baseServlet Servlet to add.
     */
    private void addServlet(ServletContextHandler context, BaseServlet baseServlet) {
        context.addServlet(new ServletHolder(baseServlet), baseServlet.getPath());
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

        loadServlets(context);

        server.start();
    }
}
