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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Test Serial command.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0 (2020-02-13 - 2020-02-13)
 */
public class SerialCommandTest {

    /**
     * Data test bytes.
     */
    private static final byte[] BYTES = {10, 20, 30};

    /**
     * Echo command test bytes.
     */
    private static final byte[] ECHO_TEST_BYTES = {SerialCommunication.COMMAND_INIT, SerialCommand.ECHO_BYTE, 10, 20, 30, 10};

    /**
     * Test command codes.
     */
    @Test
    public void testCommandCodes() {
        assertEquals(SerialCommand.NULL_BYTE, SerialCommand.NULL.getCommand());
        assertEquals(SerialCommand.ECHO_BYTE, SerialCommand.ECHO.getCommand());
        assertEquals(SerialCommand.FREQUENCE_BYTE, SerialCommand.FREQUENCE.getCommand());
        assertEquals(SerialCommand.GENERATOR_OFF_BYTE, SerialCommand.GENERATOR_OFF.getCommand());
        assertEquals(SerialCommand.GENERATOR_ON_BYTE, SerialCommand.GENERATOR_ON.getCommand());
        assertEquals(SerialCommand.DECIBEL_BYTE, SerialCommand.DECIBEL.getCommand());
        assertEquals(SerialCommand.MIC_BYTE, SerialCommand.MIC.getCommand());
        assertEquals(SerialCommand.REMOTE_BYTE, SerialCommand.REMOTE.getCommand());
    }

    /**
     * Test command build.
     */
    @Test
    public void testCommandBuild() {
        SerialCommand sc = SerialCommand.ECHO;

        sc.setMessage(BYTES);

        assertArrayEquals(ECHO_TEST_BYTES, sc.buildBytesToWrite());
    }

}
