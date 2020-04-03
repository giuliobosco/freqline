package ch.giuliobosco.freqline.help;

import java.lang.reflect.Field;

/**
 * Class with string helper.
 * Useful methods, handle strings and classes together.
 *
 * @author giuliobosco
 * @version 1.0.1 (2019-10-03 - 2019-10-03)
 */
public class ClassStringHelper {

    /**
     * Convert fields names to string array.
     *
     * @param fields Fields to convert in string array of their names.
     * @return Names of the fields.
     */
    public static String[] fieldsNameToStringArray(Field[] fields) {
        String[] attributes = new String[fields.length];

        for (int i = 0; i < fields.length; i++) {
            String attribute = fields[i].getName();
            attributes[i] = StringHelper.camelToSnake(attribute);
        }

        return attributes;
    }

    /**
     * Convert class fields names to string array.
     *
     * @param clazz Class to convert class fields names to string array.
     * @return Names of the fields of the class.
     */
    public static String[] classFieldsNameToStringArray(Class<?> clazz) {
        return fieldsNameToStringArray(clazz.getDeclaredFields());
    }
}
