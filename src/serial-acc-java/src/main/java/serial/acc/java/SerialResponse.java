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

/**
 * Serial command response.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0 (2020-02-13 - 2020-02-13)
 */
public class SerialResponse extends SerialCommunication {
    // ------------------------------------------------------------------------------------ Costants

    /**
     * Ok Response.
     */
    public static final SerialResponse OK = new SerialResponse((byte) 111);

    /**
     * Error Response.
     */
    public static final SerialResponse ERROR = new SerialResponse((byte) 101);

    // -------------------------------------------------------------------------------- Constructors

    /**
     * Create serial response with response byte and message.
     *
     * @param response Response byte.
     * @param message  Response message.
     */
    public SerialResponse(byte response, byte[] message) {
        super(false, response, message);
    }

    /**
     * Create serial response with response byte.
     *
     * @param response Response byte.
     */
    public SerialResponse(byte response) {
        this(response, SerialCommunication.EMPTY_BYTE_ARRAY);
    }

    /**
     * Create empty response.
     */
    public SerialResponse() {
    }

    // --------------------------------------------------------------------------- Getters & Setters

    /**
     * Get the response byte.
     *
     * @return Response byte.
     */
    public byte getResponse() {
        return this.getSequence();
    }
}
