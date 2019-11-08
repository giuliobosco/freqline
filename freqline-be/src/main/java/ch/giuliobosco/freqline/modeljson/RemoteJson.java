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

package ch.giuliobosco.freqline.modeljson;

import ch.giuliobosco.freqline.dbdao.DbDao;
import ch.giuliobosco.freqline.model.Base;
import ch.giuliobosco.freqline.model.Generator;
import ch.giuliobosco.freqline.model.Remote;
import org.json.JSONObject;

/**
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0 (2019-11-08 - 2019-11-08)
 */
public class RemoteJson extends BaseJson {
    // ------------------------------------------------------------------------------------ Costants

    /**
     * Generator of the remote.
     */
    public static final String GENERATOR = "generator";

    /**
     * Command of the remote.
     */
    public static final String COMMAND = "command";

    /**
     * Ip of the generator.
     */
    private static final String IP = "ip";

    /**
     * Key of communication of the generator.
     */
    private static final String KEY_C = "keyC";

    // -------------------------------------------------------------------------------- Constructors

    /**
     * Create remote JSON from remote object.
     *
     * @param mic Remote object.
     */
    public RemoteJson(Remote mic) {
        super(mic);
    }

    /**
     * Create remote JSON from base object parsed to remote.
     *
     * @param base Base object (with also remote attributes).
     */
    public RemoteJson(Base base) {
        this((Remote) base);
    }

    /**
     * Create remote from JSON object.
     *
     * @param json JSON object.
     */
    public RemoteJson(JSONObject json) {
        super(json);
    }

    /**
     * Create remote from JSON string.
     *
     * @param string JSON string.
     */
    public RemoteJson(String string) {
        super(string);
    }

    // --------------------------------------------------------------------------- Getters & Setters


    /**
     * Get remote object.
     *
     * @param dao Dao connection to database.
     * @return Remote object.
     * @throws Exception Error with, mysql, dao or generator not existing.
     */
    @Override
    public Base get(DbDao dao) throws Exception {
        int generatorId = getInt(GENERATOR);
        String command = getString(COMMAND);
        String ip = getString(IP);
        String keyC = getString(KEY_C);

        return new Remote(
                getBase(),
                (Generator) getBase(dao, generatorId),
                command,
                ip,
                keyC
        );
    }

    /**
     * Set the remote object.
     *
     * @param base Object.
     */
    @Override
    public void set(Base base) {
        Remote remote = (Remote) base;

        getJson().put(GENERATOR, remote.getGenerator().getId());
        getJson().put(COMMAND, remote.getCommand());
        getJson().put(IP, remote.getIp());
        getJson().put(KEY_C, remote.getKeyC());
    }
}
