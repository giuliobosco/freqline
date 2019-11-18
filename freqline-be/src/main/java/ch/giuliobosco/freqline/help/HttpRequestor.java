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

package ch.giuliobosco.freqline.help;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Http requestor.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0 (2019-11-18 - 2019-11-18)
 */
public class HttpRequestor {

    // ------------------------------------------------------------------------------------ Costants

    /**
     * Get request.
     */
    public static final String GET = "GET";

    // ---------------------------------------------------------------------------------- Attributes

    /**
     * Url of the request.
     */
    private URL url;

    /**
     * Http method of the request.
     */
    private String method;

    /**
     * Response of the request.
     */
    private String response;

    // -------------------------------------------------------------------------------- Constructors

    /**
     * Create HttpRequestor with URL and http mehtod.
     *
     * @param url    URL of the request.
     * @param method Http method of the request.
     */
    public HttpRequestor(URL url, String method) {
        this.url = url;
        this.method = method;
    }

    /**
     * Create HttpRequesto with URL as string.
     *
     * @param urlString URL as string.
     * @throws MalformedURLException URL as string is not valid as url.
     */
    public HttpRequestor(String urlString) throws MalformedURLException {
        this(new URL(urlString), GET);
    }

    // --------------------------------------------------------------------------- Getters & Setters

    /**
     * Get the response of the http request.
     *
     * @return Response of the http request.
     */
    public String getResponse() {
        return this.response;
    }

    // ----------------------------------------------------------------------------- General Methods

    /**
     * Execute the request.
     *
     * @throws IOException Error while executing the request.
     */
    public void executeRequest() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(this.method);

        int read;
        while ((read = connection.getInputStream().read()) != -1) {
            this.response += (char) read;
        }
    }

}