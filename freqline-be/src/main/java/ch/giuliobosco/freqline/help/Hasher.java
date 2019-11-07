package ch.giuliobosco.freqline.help;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Hasher, class with hashing methods.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0.1 (2019-10-10 - 2019-10-15)
 */
public class Hasher {

    /**
     * SHA 256 algorithm identification string.
     */
    private static final String SHA_256 = "SHA-256";

    /**
     * Execute the hash of the string.
     *
     * @param string String to hash.
     * @return Hash of the string.
     */
    public static byte[] sha256(String string) {
        byte[] hashBytes = null;

        try {
            MessageDigest digest = MessageDigest.getInstance(SHA_256);
            hashBytes = digest.digest(string.getBytes(StandardCharsets.UTF_8));

        } catch (NoSuchAlgorithmException ignored) {

        }

        return hashBytes;
    }

    /**
     * Execute the hash of the string with the salt.
     *
     * @param string String to hash.
     * @param salt   Salt for the hash.
     * @return Hash of the string.
     */
    public static byte[] sha256(String string, String salt) {
        return sha256(string + salt);
    }

    /**
     * Get the sha 256 of the string as hex string.
     *
     * @param string String to transform.
     * @return Hex string of the hash of the string.
     */
    public static String sha256Hex(String string) {
        return StringHelper.hex(sha256(string));
    }

    /**
     * Get the sha 256 of the string and salt as hex string.
     *
     * @param string String (with salt) to transform.
     * @param salt   Salt for the string.
     * @return Hex string of the hash of the string (with salt).
     */
    public static String sha256Hex(String string, String salt) {
        return StringHelper.hex(sha256(string, salt));
    }
}
