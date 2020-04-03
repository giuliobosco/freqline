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
 * Serial command.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0.1 (2020-02-10 - 2020-02-14)
 */
public class SerialCommand extends SerialCommunication {

    // ------------------------------------------------------------------------------------ Costants

    /**
     * Command bytes.
     */
    public static final byte NULL_BYTE = 110,
            ECHO_BYTE = 69,
            FREQUENCE_BYTE = 102,
            GENERATOR_OFF_BYTE = 103,
            GENERATOR_ON_BYTE = 71,
            DECIBEL_BYTE = 100,
            MIC_BYTE = 109,
            REMOTE_BYTE = 114;

    /**
     * Null command.
     */
    public static final SerialCommand NULL = new SerialCommand(NULL_BYTE);

    /**
     * Echo command.
     */
    public static final SerialCommand ECHO = new SerialCommand(ECHO_BYTE);

    /**
     * Set frequence command.
     */
    public static final SerialCommand FREQUENCE = new SerialCommand(FREQUENCE_BYTE);

    /**
     * Turn generator on coomand.
     */
    public static final SerialCommand GENERATOR_OFF = new SerialCommand(GENERATOR_OFF_BYTE);

    /**
     * Turn generator off command.
     */
    public static final SerialCommand GENERATOR_ON = new SerialCommand(GENERATOR_ON_BYTE);

    /**
     * Set decibel command.
     */
    public static final SerialCommand DECIBEL = new SerialCommand(DECIBEL_BYTE);

    /**
     * Microphone changed status command.
     */
    public static final SerialCommand MIC = new SerialCommand(MIC_BYTE);

    /**
     * Remote changed status command.
     */
    public static final SerialCommand REMOTE = new SerialCommand(REMOTE_BYTE);

    // -------------------------------------------------------------------------------- Constructors

    /**
     * Create serial command with command byte and message.
     *
     * @param command Command byte.
     * @param message Command message.
     */
    public SerialCommand(byte command, byte[] message) {
        super(true, command, message);
    }

    /**
     * Create serial command with command byte.
     *
     * @param command Command byte.
     */
    public SerialCommand(byte command) {
        this(command, SerialCommunication.EMPTY_BYTE_ARRAY);
    }

    /**
     * Create empty response.
     */
    public SerialCommand() {
    }

    // --------------------------------------------------------------------------- Getters & Setters

    /**
     * Get the command byte.
     *
     * @return Command byte.
     */
    public byte getCommand() {
        return this.getSequence();
    }

    // --------------------------------------------------------------------------- Static Components

    /**
     * Load command from Serial Communication.
     *
     * @param serialCommunication Serial communication.
     * @return Serial response.
     */
    public static SerialResponse loadCommand(SerialCommunication serialCommunication) {

        if (serialCommunication.getSequenceType() != SerialCommunication.COMMAND_INIT) {
            return null;
        }

        switch (serialCommunication.getSequence()) {
            case SerialCommand.ECHO_BYTE:
                return new SerialEchoCommand(serialCommunication.getMessage()).buildResponse();
            case NULL_BYTE:
                return new SerialNullCommand().buildResponse();
        }

        return SerialResponse.ERROR;
    }

}
