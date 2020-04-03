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

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0.2 (2019-10-26 - 2019-11-07)
 */
public class StringArrayHelper {

    // --------------------------------------------------------------------------- Static Components

    /**
     * Transform list to array.
     *
     * @param l List to transform.
     * @return Array, transformed list.
     */
    public static String[] toStringArray(List<String> l) {
        String[] a = new String[l.size()];

        for (int i = 0; i < l.size(); i++) {
            a[i] = l.get(i);
        }

        return a;
    }

    /**
     * Transform objects array to string array.
     *
     * @param objects Objects array.
     * @return String array.
     */
    public static String[] toStringArray(Object[] objects) {
        String[] a = new String[objects.length];

        for (int i = 0; i < a.length; i++) {
            a[i] = (String) objects[i];
        }

        return a;
    }

    /**
     * Transform array to list.
     *
     * @param a Array to transform.
     * @return List, transformed array.
     */
    public static List<String> arrayToList(String[] a) {
        return new ArrayList<String>(Arrays.asList(a));
    }

    /**
     * Concatenate array a and b.
     *
     * @param a First array to concatenate.
     * @param b Second array to concatenate.
     * @return Concatenated arrays.
     */
    public static String[] concatenateArray(String[] a, String[] b) {
        String[] r = new String[a.length + b.length];

        for (int i = 0; i < a.length; i++) {
            r[i] = a[i];
        }

        for (int i = 0; i < b.length; i++) {
            r[a.length + i] = b[i];
        }

        return r;
    }

    /**
     * Check if array is empty.
     *
     * @param a Array to check.
     * @return True if array is null or doesn't contains any element.
     */
    public static boolean arrayEmpty(String[] a) {
        return a == null || a.length == 0;
    }

    /**
     * Check if array is not empty.
     *
     * @param a Array to check.
     * @return True if array is not null and contains at least one element.
     */
    public static boolean arrayNotEmpty(String[] a) {
        return a != null && a.length > 0;
    }

    /**
     * String array to json array.
     *
     * @param a String array.
     * @return JSON Array.
     */
    public static JSONArray toJsonArray(String[] a) {
        JSONArray ja = new JSONArray();

        for (String s : a) {
            ja.put(s);
        }

        return ja;
    }

    /**
     * Remove string element from string array.
     *
     * @param array Array.
     * @param string String to array.
     * @return Array with out the array.
     */
    public static String[] removeFromArray(String[] array, String string) {

        Object[] o = ArrayHelper.removeFromArray(array, string);

        return objectArrayToStringArray(o);
    }

    /**
     * Convert object array to string array.
     *
     * @param a Array of objects.
     * @return Array of strings.
     */
    public static String[] objectArrayToStringArray(Object[] a) {
        String[] r = new String[a.length];

        for (int i = 0; i < a.length; i++) {
            r[i] = (String) a[i];
        }

        return r;
    }
}
