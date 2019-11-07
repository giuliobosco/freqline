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

package ch.giuliobosco.freqline.dao;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Dao Structure.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0 (2019-09-20 - 2019-10-16)
 */
public interface IDao<Base> {

    /**
     * Get element by id.
     *
     * @param id Id of element.
     * @return Optional with element if exists.
     * @throws Exception Error while getting the element.
     */
    Optional<Base> getByID(int id) throws Exception;

    /**
     * Get all elements.
     *
     * @return Stream of elements.
     * @throws Exception Error while getting the elements.
     */
    Stream<Base> getAll() throws Exception;

    /**
     * Add element.
     *
     * @param base element to add.
     * @return True if correctly added.
     * @throws Exception Error while adding element.
     */
    boolean add(Base base) throws Exception;

    /**
     * Update element (by element id).
     *
     * @param base Element to update.
     * @return True if correctly updated.
     * @throws Exception Error while updating element.
     */
    boolean update(Base base) throws Exception;

    /**
     * Delete element (by element id).
     *
     * @param base Element to delete.
     * @return True if correctly deleted.
     * @throws Exception Error while deleting element.
     */
    boolean delete(Base base) throws Exception;
}
