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

package ch.giuliobosco.freqline.help;

/**
 * Array utils.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0 (2019-11-06 - 2019-11-06)
 */
public class ArrayHelper {

    /**
     * Is object in array.
     *
     * @param a Array of objects.
     * @param o Object to find.
     * @return True if object is in array.
     */
    public static boolean isInArray(Object[] a, Object o) {
        for (Object tmp : a) {
            if (o.equals(tmp)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Count object in array.
     *
     * @param a Array of objects.
     * @param o Object to count.
     * @return Number of objects in array.
     */
    public static int countInArray(Object[] a, Object o) {
        int counter = 0;

        for (Object tmp : a) {
            if (o.equals(tmp)) {
                counter++;
            }
        }

        return counter;
    }

    /**
     * Remove object from array.
     * @param a Array.
     * @param o Object
     * @return Array with out Object.
     */
    public static Object[] removeFromArray(Object[] a, Object o) {
        int nO = countInArray(a, o);

        Object[] r = new Object[a.length - nO];

        int ri = 0;
        for (int i = 0; i < a.length; i++) {
            if (!a[i].equals(o)) {
                r[ri] = a[i];
                ri++;
            }
        }

        return r;
    }
}
