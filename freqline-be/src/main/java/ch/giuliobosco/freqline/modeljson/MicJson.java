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
import ch.giuliobosco.freqline.model.Mic;
import org.json.JSONObject;

import java.sql.Timestamp;

/**
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0.1 (2019-11-08 - 2019-11-08)
 */
public class MicJson extends BaseJson {
    // ------------------------------------------------------------------------------------ Costants

    /**
     * Generator of the mic.
     */
    private static final String GENERATOR = "generator";

    /**
     * Decibel level of the mic.
     */
    private static final String DECIBEL = "decibel";

    /**
     * Decibel level of the mic.
     */
    private static final String TIMER = "timer";

    /**
     * Ip of the generator.
     */
    private static final String IP = "ip";

    /**
     * Key of communication of the generator.
     */
    private static final String KEY_C = "keyC";

    // ---------------------------------------------------------------------------------- Attributes

    /**
     * Create mic JSON from mic object.
     *
     * @param mic Mic object.
     */
    public MicJson(Mic mic) {
        super(mic);
    }

    /**
     * Create mic JSON from base object parsed to mic.
     *
     * @param base Base object (with also mic attributes).
     */
    public MicJson(Base base) {
        this((Mic) base);
    }

    /**
     * Create mic from JSON object.
     *
     * @param json JSON object.
     */
    public MicJson(JSONObject json) {
        super(json);
    }

    /**
     * Create mic from JSON string.
     *
     * @param string JSON string.
     */
    public MicJson(String string) {
        super(string);
    }

    // -------------------------------------------------------------------------------- Constructors

    /**
     * Get mic object.
     * If the id of the generator is 0, it will be set at null.
     *
     * @param dao Dao connection to database.
     * @return Mic Object.
     * @throws Exception Error with mysql, dao or not existing generator.
     */
    @Override
    public Base get(DbDao dao) throws Exception {
        int generatorId = getInt(GENERATOR);
        int decibel = getInt(DECIBEL);
        Timestamp timer = getTimestamp(TIMER);
        String ip = getString(IP);
        String keyC = getString(KEY_C);

        return new Mic(
                getBase(),
                (Generator) getBase(dao, generatorId),
                decibel,
                timer,
                ip,
                keyC
        );
    }

    /**
     * Set the mic object.
     *
     * @param base Object.
     */
    @Override
    public void set(Base base) {
        Mic mic = (Mic) base;

        getJson().put(GENERATOR, mic.getGenerator().getId());
        getJson().put(DECIBEL, mic.getDecibel());
        getJson().put(TIMER, mic.getTimer());
        getJson().put(IP, mic.getIp());
        getJson().put(KEY_C, mic.getKeyC());
    }
}
