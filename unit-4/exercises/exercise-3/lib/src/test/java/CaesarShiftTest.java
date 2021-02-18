import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CaesarShiftTest {

    private final static String ALPHABET_UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final static String ALPHABET_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private final static String NON_LETTERS = " !\"#$%&'()*+,-./0123456789:;<=>?@[\\]^_`{|}~";

    @Test
    public void testCaesarShiftZero() {
        int shift = 0;
        String plaintext = "ABCabcXYZxyz";

        String expected = plaintext;
        String actual = CaesarShift.caesarShift(plaintext, shift);
        assertEquals(expected, actual, "Shifting by zero should have no effect");
    }

    @Test
    public void testCaesarShiftOneRight() {
        int shift = 1;
        String plaintext = "ABCabc";

        String expected = "BCDbcd";
        String actual = CaesarShift.caesarShift(plaintext, shift);
        assertEquals(expected, actual, "Shifting some uppercase and lowercase right by one without wrap-around");
    }

    @Test
    public void testCaesarShiftOneLeft() {
        int shift = 25;
        String plaintext = "XYZxyz";

        String expected = "WXYwxy";
        String actual = CaesarShift.caesarShift(plaintext, shift);
        assertEquals(expected, actual, "Shifting some uppercase and lowercase left by one without wrap-around");
    }

    @Test
    public void testCaesarShiftOneRightWrapAround() {
        int shift = 1;
        String plaintext = "XYZxyz";

        String expected = "YZAyza";
        String actual = CaesarShift.caesarShift(plaintext, shift);
        assertEquals(expected, actual, "Shifting some uppercase and lowercase right by one with wrap-around");
    }

    @Test
    public void testCaesarShiftOneLeftWrapAround() {
        int shift = 25;
        String plaintext = "ABCabc";

        String expected = "ZABzab";
        String actual = CaesarShift.caesarShift(plaintext, shift);
        assertEquals(expected, actual, "Shifting some uppercase and lowercase left by one with wrap-around");
    }

    @Test
    public void testCaesarShiftNonLetters() {
        int shift = 20;
        String plaintext = NON_LETTERS;

        String expected = plaintext;
        String actual = CaesarShift.caesarShift(plaintext, shift);
        assertEquals(expected, actual, "Shifting non-letters should have no effect");
    }

    @Test
    public void testCaesarShiftAll() {
        int shift = 20;
        String plaintext = ALPHABET_UPPERCASE + ALPHABET_LOWERCASE + NON_LETTERS;

        String expected = ALPHABET_UPPERCASE.substring(shift) + ALPHABET_UPPERCASE.substring(0, shift)
                + ALPHABET_LOWERCASE.substring(shift) + ALPHABET_LOWERCASE.substring(0, shift) + NON_LETTERS;
        String actual = CaesarShift.caesarShift(plaintext, shift);
        assertEquals(expected, actual, "Shifting all ASCII uppercase, lowercase and non-letters by " + shift);
    }

}
