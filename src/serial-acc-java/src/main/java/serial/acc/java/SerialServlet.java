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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Serial Thread (test example).
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0 (2020-02-17 - 2020-02-17)
 */
public class SerialServlet extends HttpServlet {

    /**
     * Serial thread.
     */
    private SerialThread serialThread;

    /**
     * Create serial servlet, with serial thread.\
     *
     * @param serialThread Serial thread.
     */
    public SerialServlet(SerialThread serialThread) {
        super();
        this.serialThread = serialThread;
    }

    /**
     * Do at get request.
     *
     * @param req  Http request.
     * @param resp Http response.
     * @throws ServletException Error on servlet.
     * @throws IOException      I/O servlet.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String string = req.getParameter("echo");
        SerialEchoCommand serialEchoCommand = new SerialEchoCommand(string);

        this.serialThread.addCommand(serialEchoCommand);

        resp.getOutputStream().println("added");
    }
}
