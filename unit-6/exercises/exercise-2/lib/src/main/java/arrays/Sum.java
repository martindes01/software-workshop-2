package arrays;

public class Sum {

    /**
     * Returns the sum of the specified array.
     *
     * @param array the array to sum
     * @return the sum of the specified array
     */
    public static int sum(int[] array) {
        return sumRange(array, 0, array.length);
    }

    /**
     * Returns the sum of specified range of the specified array. The initial index
     * of the range (from) must lie between zero and the length of the array,
     * inclusive. The final index of the range (to) must be less than or equal to
     * the length of the array. If the final index is less than or equal to the
     * initial index, the sum is zero.
     *
     * @param array the array whose range to sum
     * @param from  the initial index of the range to sum, inclusive
     * @param to    the final index of the range to sum, exclusive
     * @return the sum of the specified range of the specified array
     */
    public static int sumRange(int[] array, int from, int to) {
        int result = 0;

        for (int i = from; i < to; i++) {
            result += array[i];
        }

        return result;
    }

    /**
     * Returns the sum of the specified array. The array is split into two ranges,
     * whose sums are calculated in parallel.
     *
     * @param array the array to sum
     * @return the sum of the specified array
     */
    public static int sumParallel(int[] array) {
        SumThread t1 = new SumThread(array, 0, array.length / 2);
        SumThread t2 = new SumThread(array, array.length / 2, array.length);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (Exception e) {
        }

        return t1.getSum() + t2.getSum();
    }

}
