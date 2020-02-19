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

import ch.giuliobosco.freqline.jdbc.JapiConnector;
import ch.giuliobosco.freqline.jdbc.JdbcConnector;
import ch.giuliobosco.freqline.queries.GeneratorQuery;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Serial mic command.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0 (2020-02-19 - 2020-02-19)
 */
public class SerialMicCommand extends SerialCommand implements SerialInputCommand {

    // ---------------------------------------------------------------------------------- Attributes

    /**
     * Serial Thread.
     */
    private SerialThread serialThread;

    // -------------------------------------------------------------------------------- Constructors

    /**
     * Create serial mic command with serial thread.
     *
     * @param serialThread Serial thread.
     */
    public SerialMicCommand(SerialThread serialThread) {
        super(MIC_BYTE, EMPTY_BYTE_ARRAY);
    }

    // ----------------------------------------------------------------------------- General Methods

    /**
     * Build command response and update generator status.
     *
     * @return Command response.
     */
    @Override
    public SerialResponse buildResponse() {
        SerialResponse response = SerialResponse.OK;
        response.setMessage(this.getMessage());

        try {
            JdbcConnector connector = new JapiConnector();
            connector.openConnection();

            boolean status = GeneratorQuery.getGeneratorStatus(connector.getConnection(), AccGenerator.KEY_C);

            if (status) {
                AccGenerator.turnGeneratorOff(connector.getConnection(), AccGenerator.KEY_C, this.serialThread);
            } else {
                long timer = GeneratorQuery.getMicTimer(connector.getConnection(), AccGenerator.KEY_C);
                AccGenerator.turnGeneratorOn(connector.getConnection(), AccGenerator.KEY_C, timer, this.serialThread);
            }
        } catch (IOException | ClassNotFoundException | SQLException ignored) {

        }

        return response;
    }
}
