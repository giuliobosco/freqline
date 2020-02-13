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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Test class for SerialCommunication.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0.2 (2020-02-12 - 2020-02-13)
 */
public class SerialCommunicationTest {
    /**
     * First byte test value.
     */
    private static final byte BYTE_TEST_VALUE = (byte) 43;

    /**
     * Second byte test value.
     */
    private static final byte BYTE_TEST_VALUE_B = (byte) 101;

    /**
     * Length of test bytes data.
     */
    private static final int BYTES_DATA_LENGTH = 10;

    /**
     * Build bytes array for test data.
     *
     * @return Bytes array for test data.
     */
    private byte[] getBytesData() {
        byte[] bytes = new byte[BYTES_DATA_LENGTH];

        for (int i = 0; i < BYTES_DATA_LENGTH; i++) {
            bytes[i] = (byte) i;
        }

        return bytes;
    }

    /**
     * Get a serial communication instance for test.
     *
     * @return Serial communication instance for test.
     */
    private SerialCommunication getSerialCommunicationInstance() {
        return new SerialCommunication(
                BYTE_TEST_VALUE,
                BYTE_TEST_VALUE_B,
                getBytesData()
        );
    }

    /**
     * Test getSequenceType() method of class SerialCommunication.
     */
    @Test
    public void testGetSequenceType() {
        SerialCommunication sc = getSerialCommunicationInstance();

        assertEquals(BYTE_TEST_VALUE, sc.getSequenceType());
        assertNotEquals(BYTE_TEST_VALUE_B, sc.getSequenceType());
    }

    /**
     * Test getSequence() method of class SerialCommunication.
     */
    @Test
    public void testGetSequence() {
        SerialCommunication sc = getSerialCommunicationInstance();

        assertEquals(BYTE_TEST_VALUE_B, sc.getSequence());
        assertNotEquals(BYTE_TEST_VALUE, sc.getSequence());
    }

    /**
     * Test getMessage() method of class SerialCommunication.
     */
    @Test
    public void testGetMessage() {
        SerialCommunication sc = getSerialCommunicationInstance();

        assertArrayEquals(getBytesData(), sc.getMessage());
    }

    /**
     * Test setMessage() method of class SerialCommunication.
     */
    @Test
    public void testSetMessage() {
        SerialCommunication sc = new SerialCommunication();

        sc.setMessage(getBytesData());

        assertArrayEquals(getBytesData(), sc.getMessage());
    }

    /**
     * Test arrayPush() method of class SerialCommunication.
     */
    @Test
    public void testArrayPush() {
        byte[] bytesA = {1, 2, 3, 4};
        byte[] bytesB = {1, 2, 3, 4, 5};

        bytesA = getSerialCommunicationInstance().arrayPush(bytesA, (byte) 5);

        assertArrayEquals(bytesA, bytesB);
    }

    /**
     * Test buildBytesToWrite() method of class SerialCommunication.
     */
    @Test
    public void testBuildBytesToWrite() {
        byte[] bytes = new byte[]{1, 2, 3};

        byte[] bytesTest = new byte[]{43, 101, 1, 2, 3, 10};

        bytes = new SerialCommunication(
                BYTE_TEST_VALUE,
                BYTE_TEST_VALUE_B,
                bytes).buildBytesToWrite();

        assertArrayEquals(bytesTest, bytes);
    }

    /**
     * Test write() method of class SerialCommunication.
     *
     * @throws IOException Error with I/O.
     */
    @Test
    public void testWrite() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        SerialCommunication sc = getSerialCommunicationInstance();
        sc.write(out);
        byte[] bytes = out.toByteArray();

        assertArrayEquals(sc.buildBytesToWrite(), bytes);
    }

    /**
     * Test readUntilEnd() method of class SerialCommunication.
     *
     * @throws IOException Error with I/O.
     */
    @Test
    public void testReadUntilEnd() throws IOException {
        byte[] mock = getSerialCommunicationInstance().buildBytesToWrite();
        SerialCommunication testMethod = new SerialCommunication();

        ByteArrayInputStream in = new ByteArrayInputStream(mock);

        testMethod.readUntilEnd(in);

        assertArrayEquals(mock, testMethod.buildBytesToWrite());
    }

    /**
     * Test isSameSequence() method of class SerialCommunication.
     */
    @Test
    public void testIsSameSequence() {
        SerialCommunication scA = getSerialCommunicationInstance();
        SerialCommunication scB = getSerialCommunicationInstance();

        assertTrue(scA.isSameSequence(scB));

        scB = new SerialCommunication(BYTE_TEST_VALUE_B, BYTE_TEST_VALUE, getBytesData());

        assertFalse(scA.isSameSequence(scB));
    }

    /**
     * Test isSameSequenceType() method of class SerialCommunication.
     */
    @Test
    public void testIsSameSequenceType() {
        SerialCommunication scA = getSerialCommunicationInstance();
        SerialCommunication scB = getSerialCommunicationInstance();

        assertTrue(scA.isSameSequenceType(scB));

        scB = new SerialCommunication(BYTE_TEST_VALUE_B, BYTE_TEST_VALUE, getBytesData());

        assertFalse(scA.isSameSequenceType(scB));
    }

    /**
     * Test isSame() method of class SerialCommunication.
     */
    @Test
    public void testIsSame() {
        SerialCommunication scA = getSerialCommunicationInstance();
        SerialCommunication scB = getSerialCommunicationInstance();

        assertTrue(scA.isSame(scB));

        scB = new SerialCommunication(BYTE_TEST_VALUE, BYTE_TEST_VALUE, getBytesData());

        assertFalse(scA.isSame(scB));
    }

    /**
     * Test equals() method of class SerialCommunication.
     */
    @Test
    public void testEquals() {
        SerialCommunication scA = getSerialCommunicationInstance();
        assertEquals(scA, scA);

        SerialCommunication scB = getSerialCommunicationInstance();
        assertEquals(scA, scB);

        scB = new SerialCommunication(BYTE_TEST_VALUE, BYTE_TEST_VALUE_B, getBytesData());
        assertEquals(scA, scB);

        scB = new SerialCommunication(BYTE_TEST_VALUE, BYTE_TEST_VALUE, getBytesData());
        assertNotEquals(scA, scB);
    }
}
