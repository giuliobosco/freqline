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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Serial communication protocol class. Sequence of communication.
 * Build commands and responses.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0 (2020-02-12 - 2020-02-12)
 */
public class SerialCommunication {

    // ------------------------------------------------------------------------------------ Costants

    /**
     * Initial communication char.
     */
    public static final byte INIT = 42;

    /**
     * Command initial char.
     */
    public static final byte COMMAND_INIT = 43;

    /**
     * Response initial char.
     */
    public static final byte RESPONSE_INIT = 45;

    /**
     * End of sequence char.
     */
    public static final byte END = 10;

    /**
     * Bytes for unsigned byte casting.
     */
    public static final int TO_BYTE = 0x000000FF;

    // ---------------------------------------------------------------------------------- Attributes

    /**
     * Sequence type, could be command or response.
     */
    private byte sequenceType;

    /**
     * Sequence, is the command or response.
     */
    private byte sequence;

    /**
     * Message of the communication.
     */
    private byte[] message;

    // -------------------------------------------------------------------------------- Constructors

    /**
     * Create serial communication from sequenceType, sequence and message.
     *
     * @param sequenceType Sequence type, could be command or response.
     * @param sequence     Sequence, is the command or response.
     * @param message      Message of the communication.
     */
    public SerialCommunication(byte sequenceType, byte sequence, byte[] message) {
        this.sequenceType = sequenceType;
        this.sequence = sequence;
        this.message = message;
    }

    /**
     * Create serial communication form boolean command, sequence and message.
     *
     * @param command  True for command, false for response.
     * @param sequence Sequence, is the command or response.
     * @param message  Message of the communication.
     */
    public SerialCommunication(boolean command, byte sequence, byte[] message) {
        this(command ? COMMAND_INIT : RESPONSE_INIT, sequence, message);
    }

    /**
     * Create empty serial communication, for readUntilEnd().
     */
    public SerialCommunication() {
    }

    // --------------------------------------------------------------------------- Getters & Setters

    /**
     * Get the sequence type, could be command or response.
     *
     * @return Sequence type, could be command or response.
     */
    public byte getSequenceType() {
        return this.sequenceType;
    }

    /**
     * Get the sequence, is the command or response.
     *
     * @return Sequence, is the command or response.
     */
    public byte getSequence() {
        return this.sequence;
    }

    /**
     * Get the message of the communication.
     *
     * @return Message of the communication.
     */
    public byte[] getMessage() {
        return this.message;
    }

    // -------------------------------------------------------------------------------- Help Methods

    /**
     * Cast int to byte unsigned.
     *
     * @param b Byte to parse.
     * @return Unsigned byte of int.
     */
    protected byte toByteUnsigned(int b) {
        return (byte) (TO_BYTE & b);
    }

    /**
     * Push element to end of array.
     *
     * @param bytes Base array to push element b.
     * @param b     Element to push into bytes.
     * @return Array bytes with pushed b element.
     */
    protected byte[] arrayPush(byte[] bytes, byte b) {
        byte[] newBytes = new byte[bytes.length + 1];

        for (int i = 0; i < bytes.length; i++) {
            newBytes[i] = bytes[i];
        }

        newBytes[bytes.length] = b;

        return newBytes;
    }

    /**
     * Built array sequence of serial communication.
     * <ul>
     * <li>First byte: sequence type (command or response)</li>
     * <li>Second byte: sequence (the command or the response)</li>
     * <li>Bytes: message</li>
     * <li>Last byte: End of sequence (10)</li>
     * </ul>
     *
     * @return Built bytes sequence of communication.
     */
    protected byte[] buildBytesToWrite() {
        byte[] bytes = new byte[this.message.length + 3];

        bytes[0] = this.sequenceType;
        bytes[1] = this.sequence;

        for (int i = 0; i < this.message.length; i++) {
            bytes[i + 2] = this.message[i];
        }

        bytes[this.message.length + 2] = END;

        return bytes;
    }

    // ----------------------------------------------------------------------------- General Methods

    /**
     * Read sequence from input stream until end char.
     *
     * @param in Input stream for read data.
     * @throws IOException I/O Exception.
     */
    public void readUntilEnd(InputStream in) throws IOException {
        int read;
        while ((read = in.read()) != COMMAND_INIT && read != RESPONSE_INIT) {
        }

        this.sequenceType = toByteUnsigned(read);

        read = in.read();
        this.sequence = toByteUnsigned(read);

        while ((read = in.read()) != END) {
            this.message = arrayPush(this.message, toByteUnsigned(read));
        }
    }

    /**
     * Write sequence to output stream.
     *
     * @param out Output stream for write data.
     * @throws IOException I/O Exception.
     */
    public void write(OutputStream out) throws IOException {
        byte[] bytes = buildBytesToWrite();

        out.write(bytes);
    }

    // --------------------------------------------------------------------------- Static Components
}
