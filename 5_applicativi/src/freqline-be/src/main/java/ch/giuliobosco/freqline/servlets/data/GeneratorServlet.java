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
 
package ch.giuliobosco.freqline.servlets.data;

import ch.giuliobosco.freqline.dbdao.DbDao;
import ch.giuliobosco.freqline.dbdao.DbGeneratorDao;
import ch.giuliobosco.freqline.jdbc.JdbcConnector;
import ch.giuliobosco.freqline.model.Base;
import ch.giuliobosco.freqline.model.Generator;
import ch.giuliobosco.freqline.modeljson.BaseJson;
import ch.giuliobosco.freqline.modeljson.GeneratorJson;

import javax.servlet.annotation.WebServlet;
import java.util.Map;

/**
 * Generator Restful API servlet.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0.2 (2019-11-12 - 2020-02-19)
 */
@WebServlet(name = "GeneratorServlet", urlPatterns = {"data/generator/*"}, loadOnStartup = 1)
public class GeneratorServlet extends BaseDataServlet {

    /**
     * Get the dao
     *
     * @param connector Jdbc Connector.
     * @param actionBy  Action by.
     * @return Initialized generator dao.
     */
    @Override
    protected DbDao getDao(JdbcConnector connector, int actionBy) {
        return new DbGeneratorDao(connector, actionBy);
    }

    /**
     * Get json parser.
     *
     * @param base Base of the json.
     * @return Generator json parser.
     */
    @Override
    protected BaseJson getJson(Base base) {
        return new GeneratorJson(base);
    }

    /**
     * Get model class.
     *
     * @return Generator model class.
     */
    @Override
    protected Class<? extends Base> getModel() {
        return Generator.class;
    }

    /**
     * Map to model.
     *
     * @param params   Parameters.
     * @param base     Base element.
     * @param actionBy Action by.
     * @param dao      Dao.
     * @return Generator model.
     * @throws Exception Error with mysql.
     */
    @Override
    protected Base mapToBase(Map<String, String[]> params, Base base, int actionBy, DbDao dao) throws Exception {
        String name = firstValue(params, GeneratorJson.NAME);
        String frequenceString = firstValue(params, GeneratorJson.FREQUENCE);
        String statusString = firstValue(params, GeneratorJson.STATUS);
        String ip = firstValue(params, GeneratorJson.IP);
        String keyC = firstValue(params, "key_c");

        int frequence;
        boolean status;

        try {
            frequence = Integer.parseInt(frequenceString);
            status = Boolean.parseBoolean(statusString);

        } catch (NumberFormatException nfe) {
            return null;
        }

        if (base != null) {
            return new Generator(
                    base,
                    name,
                    frequence,
                    status,
                    ip,
                    keyC
            );
        }

        return new Generator(
                actionBy,
                name,
                frequence,
                status,
                ip,
                keyC
        );
    }

    /**
     * Get the servlet path.
     *
     * @return Servlet path.
     */
    @Override
    public String getPath() {
        return "data/generator";
    }

    /**
     * Required get permission string.
     *
     * @return Permission string.
     */
    @Override
    protected String requiredDeletePermission() {
        return "db";
    }

    /**
     * Required post permission string.
     *
     * @return Permission string.
     */
    @Override
    protected String requiredGetPermission() {
        return "user";
    }

    /**
     * Required put permission string.
     *
     * @return Permission string.
     */
    @Override
    protected String requiredPostPermission() {
        return "db";
    }

    /**
     * Required delete permission string.
     *
     * @return Permission string.
     */
    @Override
    protected String requiredPutPermission() {
        return "db";
    }
}
