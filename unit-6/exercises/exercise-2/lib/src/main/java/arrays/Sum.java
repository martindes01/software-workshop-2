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

}
