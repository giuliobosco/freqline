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
 * 
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0 (2020-02-19 - 2020-02-19)
 */
public class SerialRemoteCommand extends SerialCommand implements SerialInputCommand {

    // ---------------------------------------------------------------------------------- Attributes

    private SerialThread serialThread;

    // -------------------------------------------------------------------------------- Constructors

    public SerialRemoteCommand(SerialThread serialThread) {
        super(REMOTE_BYTE, EMPTY_BYTE_ARRAY);
        this.serialThread = serialThread;
    }

    // ----------------------------------------------------------------------------- General Methods

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
                AccGenerator.turnGeneratorOn(connector.getConnection(), AccGenerator.KEY_C, this.serialThread);
            }

        } catch (IOException | SQLException | ClassNotFoundException ignored) {

        }

        return response;
    }


    // --------------------------------------------------------------------------- Static Components

}
