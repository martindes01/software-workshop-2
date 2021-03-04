package arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SumTest {

    private static final int[] ARRAY = new int[] { 22, 18, 12, -4, 27, 30, 36, 50, 7, 68, 91, 56, 2, 85, 42, 98 };
    private static final int SUM = 640;

    @Test
    public void testSum() {
        assertEquals(SUM, Sum.sum(ARRAY), "Sum calculated in serial");
    }

    @Test
    public void testSumParallel() {
        assertEquals(SUM, Sum.sumParallel(ARRAY), "Sum calculated in parallel");
    }

}
