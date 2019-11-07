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
 * String helper.
 * Useful methods for handle Strings.
 *
 * @author giuliobosco
 * @version 1.0.7 (2019-10-03 - 2019-10-22)
 */
public class StringHelper {

    /**
     * Check if the character is lower case.
     *
     * @param c Character to check.
     * @return True if character is lower case.
     */
    public static boolean isLowerCase(char c) {
        return (int) c > 0x60 && (int) c < 0x7b;
    }

    /**
     * Check if string is all uppercase.
     *
     * @param string String to check.
     * @return True if the all the string is upper case.
     */
    public static boolean isLowerCase(String string) {
        char[] chars = string.toCharArray();

        for (char c : chars) {
            if (!isLowerCase(c)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Check if the character is lower case.
     *
     * @param c Character to check.
     * @return True if character is lower case.
     */
    public static boolean isUpperCase(char c) {
        return (int) c > 0x40 && (int) c < 0x5b;
    }

    /**
     * Check if string is all uppercase.
     *
     * @param string String to check.
     * @return True if the all the string is upper case.
     */
    public static boolean isUpperCase(String string) {
        char[] chars = string.toCharArray();

        for (char c : chars) {
            if (!isUpperCase(c)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Transform char to lower case string.
     *
     * @param c Char to transform.
     * @return Lower case string.
     */
    public static String toLwr(char c) {
        return toLwr(String.valueOf(c));
    }

    /**
     * Transform string to lower case string.
     *
     * @param string String to transform.
     * @return Lower case string.
     */
    public static String toLwr(String string) {
        return string.toLowerCase();
    }

    /**
     * Transform char to upper case string.
     *
     * @param c Char to transform.
     * @return Upper case string.
     */
    public static String toUpr(char c) {
        return toUpr(String.valueOf(c));
    }

    /**
     * Transform string to upper case string.
     *
     * @param string String to transform.
     * @return Upper case string.
     */
    public static String toUpr(String string) {
        return string.toUpperCase();
    }

    /**
     * Convert camel case (UpperCamelCase and lowerCamelCase) in snake_case.
     *
     * @param string String to convert.
     * @return snake_case string.
     */
    public static String camelToSnake(String string) {

        String ret = null;

        char[] chars = string.toCharArray();

        if (chars.length > 0) {
            ret = toLwr(chars[0]);

            for (int i = 1; i < chars.length; i++) {
                if (isUpperCase(chars[i])) {
                    ret += "_" + toLwr(chars[i]);
                } else {
                    ret += chars[i];
                }
            }
        }

        return ret;
    }

    /**
     * Convert string to SQL name.
     * Convert "name" in "`name`".
     *
     * @param string String to convert.
     * @return Converted string.
     */
    public static String toSqlName(String string) {
        return "`" + string + "`";
    }

    /**
     * Hex string form bytes.
     *
     * @param bytes Bytes to transform in string.
     * @return String transformed in bytes/
     */
    public static String hex(byte[] bytes) {
        StringBuilder hex = new StringBuilder(bytes.length * 2);

        for (byte b : bytes) {
            if ((b & 0xff) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xff));
        }

        return hex.toString();
    }

    /**
     * Get a random char A-Za-z.
     *
     * @return Random char.
     */
    public static char randomChar() {
        char c = 0;

        if (NumberHelper.random(0, 2) == 0) {
            c = (char) NumberHelper.random(0x40, 0x5c);
        } else {
            c = (char) NumberHelper.random(0x60, 0x7b);
        }

        return c;
    }

    /**
     * Get a random string of the specified string.
     *
     * @param length Length of the random string.
     * @return Random string.
     */
    public static String randomString(int length) {
        String s = "";

        for (int i = 0; i < length; i++) {
            s += randomChar();
        }

        return s;
    }

    /**
     * Check if there is a string.
     * Not null and longer than 0.
     *
     * @param str String to check.
     * @return True if there is a string.
     */
    public static boolean is(String str) {
        return str != null && str.trim().length() > 0;
    }

}
