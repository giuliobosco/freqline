package ch.giuliobosco.freqline.help;

/**
 * Number help class.
 *
 * @author giuliobosco
 * @version 1.0 (2019-10-15 - 2019-10-15)
 */
public class NumberHelper {

    /**
     * Get a random number between min and max.
     *
     * @param min Minimum of random number.
     * @param max Maximum of random number (not included).
     * @return Random number between min and max.
     */
    public static int random(int min, int max) {
        return min + (int) ((max - min) * Math.random());
    }

    /**
     * Get a random number between 0 and max.
     *
     * @param max Maximum of random number.
     * @return Random number between 0 and max.
     */
    public static int random(int max) {
        return random(0, max);
    }

    /**
     * Get a random number between 0 and Integer max value.
     *
     * @return Random number between 0 and Integer max value.
     */
    public static int random() {
        return random(Integer.MAX_VALUE);
    }
}
