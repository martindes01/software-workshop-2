public class CaesarShift {

    /**
     * Encrypts the specified plaintext message by right-shifting every letter by
     * the specified number of positions along the English alphabet. Non-letter
     * characters are not affected. The shift should be an integer in the range 0 to
     * 25, inclusive. It is assumed that this is the case.
     *
     * @param plaintext the plaintext message to encrypt
     * @param shift     the number of positions to right-shift the letters, from 0
     *                  to 25, inclusive
     * @return the resulting ciphertext
     */
    public static String caesarShift(String plaintext, int shift) {
        StringBuilder result = new StringBuilder(plaintext.length());

        for (char p : plaintext.toCharArray()) {
            char c;

            if (p >= 'A' && p <= 'Z') {
                c = (char) rangedModulo(p + shift, 'A', 'Z');
            } else if (p >= 'a' && p <= 'z') {
                c = (char) rangedModulo(p + shift, 'a', 'z');
            } else {
                c = p;
            }

            result.append(c);
        }

        return result.toString();
    }

    private static int rangedModulo(int value, int min, int max) {
        return (value - min) % (max - min + 1) + min;
    }

}
