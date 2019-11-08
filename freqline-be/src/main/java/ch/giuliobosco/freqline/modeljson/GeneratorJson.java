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
import org.json.JSONObject;

/**
 * Convert generator object to JSON.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0 (2019-11-08 - 2019-11-08)
 */
public class GeneratorJson extends BaseJson {
    // ------------------------------------------------------------------------------------ Costants

    /**
     * Name of the generator.
     */
    public static final String NAME = "name";

    /**
     * Frequence of the generator.
     */
    public static final String FREQUENCE = "frequence";

    /**
     * Status of the generator.
     */
    public static final String STATUS = "status";

    /**
     * Ip of the generator.
     */
    public static final String IP = "ip";

    /**
     * Key of communication of the generator.
     */
    public static final String KEY_C = "keyC";

    // ---------------------------------------------------------------------------------- Attributes

    /**
     * Create generator JSON from group object.
     *
     * @param generator Generator object.
     */
    public GeneratorJson(Generator generator) {
        super(generator);
    }

    /**
     * Create generator JSON from base object parsed to group.
     *
     * @param base Base object (with also generator attributes).
     */
    public GeneratorJson(Base base) {
        super(base);
    }

    /**
     * Create generator from JSON object.
     *
     * @param json JSON object.
     */
    public GeneratorJson(JSONObject json) {
        super(json);
    }

    /**
     * Create generator from JSON string.
     *
     * @param string JSON string.
     */
    public GeneratorJson(String string) {
        super(string);
    }

    // -------------------------------------------------------------------------------- Constructors

    /**
     * Get generator object.
     *
     * @param dao Dao connection to database.
     * @return Generator Object.
     * @throws Exception Error with mysql.
     */
    @Override
    public Base get(DbDao dao) throws Exception {
        String name = getString(NAME);
        int frequence = getInt(FREQUENCE);
        boolean status = getBoolean(STATUS);
        String ip = getString(IP);
        String keyC = getString(KEY_C);

        return new Generator(getBase(),
                name,
                frequence,
                status,
                ip,
                keyC
        );
    }

    /**
     * Set the generator object.
     *
     * @param base Object.
     */
    public void set(Base base) {
        Generator generator = (Generator) base;

        getJson().put(NAME, generator.getName());
        getJson().put(FREQUENCE, generator.getFrequence());
        getJson().put(STATUS, generator.isStatus());
        getJson().put(IP, generator.getIp());
        getJson().put(KEY_C, generator.getKeyC());
    }
}
