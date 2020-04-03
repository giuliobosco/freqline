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

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Test Serial response.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0 (2020-02-14 - 2020-02-14)
 */
public class SerialResponseTest {

    private static final byte[] ECHO_BYTES = {11, 20, 30};

    private static final byte[] ECHO_BYTES_NULL = new byte[0];

    /**
     * Test response codes.
     */
    @Test
    public void testResponseCodes() {
        assertEquals(SerialResponse.OK_BYTE, SerialResponse.OK.getResponse());
        assertEquals(SerialResponse.ERROR_BYTE, SerialResponse.ERROR.getResponse());
    }

    /**
     * Test echo response.
     */
    @Test
    public void testEchoResponseOk() {
        SerialInputCommand sc = new SerialEchoCommand(ECHO_BYTES);
        SerialResponse sr = sc.response();

        assertTrue(sr.isSame(sr));
        assertArrayEquals(ECHO_BYTES, sr.getMessage());
    }

    /**
     * Test echo response not equals.
     */
    @Test
    public void testEchoResponseNot() {
        SerialInputCommand sc = new SerialEchoCommand(ECHO_BYTES);
        SerialResponse sr = sc.response();

        assertTrue(sr.isSame(sr));
        assertFalse(Arrays.equals(ECHO_BYTES_NULL, sr.getMessage()));
    }
}
