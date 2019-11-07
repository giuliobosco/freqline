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

import org.json.JSONException;
import org.json.JSONObject;
import ch.giuliobosco.freqline.dao.DaoException;
import ch.giuliobosco.freqline.dbdao.DbDao;
import ch.giuliobosco.freqline.model.Base;

import java.util.Date;
import java.util.Optional;


/**
 * Transform Base object to JSON and reverse.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0.2 (2019-09-18 - 2019-10-10)
 */
public abstract class BaseJson {
    // ------------------------------------------------------------------------------------ Costants

    /**
     * Id string.
     */
    private static final String ID = "id";

    /**
     * Created by string.
     */
    private static final String CREATED_BY = "createdBy";

    /**
     * Created date string.
     */
    private static final String CREATED_DATE = "createdDate";

    /**
     * Updated by string.
     */
    private static final String UPDATED_BY = "updatedBy";

    /**
     * Updated date string.
     */
    private static final String UPDATED_DATE = "updatedBy";

    /**
     * Deleted by string.
     */
    private static final String DELETED_BY = "deletedBy";

    /**
     * Deleted date string.
     */
    private static final String DELETED_DATE = "deletedDate";

    // ---------------------------------------------------------------------------------- Attributes

    /**
     * Base in JSON format.
     */
    private JSONObject json;

    // -------------------------------------------------------------------------------- Constructors

    /**
     * Create BaseJson from base object.
     *
     * @param base Base object for create base JSON.
     */
    public BaseJson(Base base) {
        this.json = new JSONObject();
        this.setJsonBase(base);
        this.set(base);
    }

    /**
     * Create BaseJson from JSON object.
     *
     * @param json JSON object.
     */
    public BaseJson(JSONObject json) {
        this.json = json;
    }

    /**
     * Create BaseJson from JSON String.
     *
     * @param json JSON String.
     */
    public BaseJson(String json) {
        this(new JSONObject(json));
    }

    // --------------------------------------------------------------------------- Getters & Setters

    /**
     * Get the JSON Object.
     *
     * @return JSON Object.
     */
    public JSONObject getJson() {
        return this.json;
    }

    // -------------------------------------------------------------------------------- Help Methods

    /**
     * Get the base by the id and the dao.
     *
     * @param dao Dao of the element.
     * @param id  Id of the element.
     * @return Element.
     * @throws Exception SQL or DAO Exception.
     */
    protected Base getBase(DbDao dao, int id) throws Exception {
        Base base = null;

        if (id > 0) {
            if (dao != null) {
                Optional<Base> optional = dao.getById(id);

                if (optional.isPresent()) {
                    base = optional.get();
                } else {
                    throw new DaoException("No base with id: " + id);
                }
            } else {
                throw new DaoException("Missing DAO");
            }
        }

        return base;
    }

    /**
     * Set all the attributes of the Base object in the JSON object.
     *
     * @param base Base object to transform.
     */
    private void setJsonBase(Base base) {
        this.json.put(ID, base.getId());
        this.json.put(CREATED_BY, base.getCreatedBy());
        this.json.put(CREATED_DATE, base.getCreatedDate().getTime());
        this.json.put(UPDATED_BY, base.getUpdatedBy());
        this.json.put(UPDATED_DATE, base.getUpdatedDate().getTime());
        this.json.put(DELETED_BY, base.getDeletedBy());
        if (base.getDeletedDate() != null) {
            this.json.put(DELETED_DATE, base.getDeletedDate().getTime());
        }
    }

    /**
     * Get int from JSON.
     *
     * @param key Int key.
     * @return Int of the key.
     */
    protected int getInt(String key) {
        try {
            return getJson().getInt(key);
        } catch (JSONException jsone) {
            return Base.SQL_INT_NULL;
        }
    }

    /**
     * Get date from JSON.
     *
     * @param key Date key.
     * @return Date of the key.
     */
    protected Date getDate(String key) {
        try {
            return new Date(getJson().getLong(key));
        } catch (JSONException ignored) {
            return null;
        }
    }

    /**
     * Get string from JSON.
     *
     * @param key String key.
     * @return String of the key.
     */
    protected String getString(String key) {
        try {
            return getJson().getString(key);
        } catch (JSONException ignored) {
            return null;
        }
    }

    // ----------------------------------------------------------------------------- General Methods

    /**
     * Get the Base object from the JSON.
     *
     * @return Base object from the JSON.
     */
    protected Base getBase() {
        return new Base(
                getInt(ID),
                getInt(CREATED_BY),
                getDate(CREATED_DATE),
                getInt(UPDATED_BY),
                getDate(UPDATED_DATE),
                getInt(DELETED_BY),
                getDate(UPDATED_DATE)) {

            @Override
            public int getId() {
                return super.getId();
            }
        };
    }

    /**
     * Set object.
     *
     * @param base Object.
     */
    public abstract void set(Base base);

    /**
     * Get Object.
     *
     * @return Object.
     */
    public abstract Base get(DbDao dao) throws Exception;

    // --------------------------------------------------------------------------- Static Components
}
