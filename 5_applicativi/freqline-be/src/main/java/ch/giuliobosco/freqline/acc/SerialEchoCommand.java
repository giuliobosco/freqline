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

package ch.giuliobosco.freqline.acc;

/**
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0.1 (2020-02-10 - 2020-02-17)
 */
public class SerialEchoCommand extends SerialCommand implements SerialInputCommand {

    // -------------------------------------------------------------------------------- Constructors

    /**
     * Create Serial Echo command with message.
     *
     * @param message Echo message.
     */
    public SerialEchoCommand(byte[] message) {
        super(SerialCommand.ECHO_BYTE, message);
    }

    /**
     * Create Serial Echo command with string message.
     *
     * @param message Echo string message.
     */
    public SerialEchoCommand(String message) {
        this(message.getBytes());
    }

    /**
     * Create empty echo command.
     */
    public SerialEchoCommand() {
        this(SerialCommand.EMPTY_BYTE_ARRAY);
    }

    // ----------------------------------------------------------------------------- General Methods

    /**
     * Build Serial Response.
     *
     * @return Serial Response.
     */
    @Override
    public SerialResponse buildResponse() {
        SerialResponse response = SerialResponse.OK;
        response.setMessage(this.getMessage());

        return response;
    }

    /**
     * Set the message as string.
     *
     * @param message Set message as string.
     */
    public void setMessage(String message) {
        super.setMessage(message.getBytes());
    }
}
