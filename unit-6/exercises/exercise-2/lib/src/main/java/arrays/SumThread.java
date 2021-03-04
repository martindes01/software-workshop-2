package arrays;

public class SumThread extends Thread {

    private int[] array;
    private int from;
    private int to;
    private int sum = 0;

    /**
     * Constructs a new thread that calculates the sum of the specified range of the
     * specified array. The initial index of the range (from) must lie between zero
     * and the length of the array, inclusive. The final index of the range (to)
     * must be less than or equal to the length of the array. If the final index is
     * less than or equal to the initial index, the sum is zero.
     *
     * @param array the array whose range to sum
     * @param from  the initial index of the range to sum
     * @param to    the final index of the range to sum
     */
    public SumThread(int[] array, int from, int to) {
        this.array = array;
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the calculated sum of the range of the array specified in the
     * constructor of this thread. If the thread has not been run, the sum is zero.
     *
     * @return the sum of the range of the array specified in the constructor of
     *         this thread if the thread has been run, zero otherwise
     */
    public int getSum() {
        return sum;
    }

    /**
     * Calculates the sum of the range of the array specified in the constructor of
     * this thread.
     */
    @Override
    public void run() {
        sum = Sum.sumRange(array, from, to);
    }

}
