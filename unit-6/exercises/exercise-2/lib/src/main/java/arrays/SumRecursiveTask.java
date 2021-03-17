package arrays;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

@SuppressWarnings("serial")
public class SumRecursiveTask extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 8;

    private int[] array;
    private int from;
    private int to;

    /**
     * Constructs a new task computes the sum of the specified array.
     *
     * @param array the array to sum
     */
    public SumRecursiveTask(int[] array) {
        this(array, 0, array.length);
    }

    /**
     * Constructs a new task that computes the sum of the specified range of the
     * specified array. The initial index of the range (from) must be greater than
     * or equal to zero. The final index of the range (to) must be less than or
     * equal to the length of the array. If the final index is less than or equal to
     * the initial index, the sum is zero.
     *
     * @param array the array whose range to sum
     * @param from  the initial index of the range to sum, inclusive
     * @param to    the final index of the range to sum, exclusive
     */
    public SumRecursiveTask(int[] array, int from, int to) {
        this.array = array;
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the sum of the range of the array specified in the constructor of
     * this task.
     *
     * @return the sum of the range of the array specified in the constructor of
     *         this task
     */
    @Override
    protected Integer compute() {
        if ((to - from) <= THRESHOLD) {
            return Sum.sumRange(array, from, to);
        } else {
            int split = (from + to) / 2;

            List<ForkJoinTask<Integer>> subTasks = new ArrayList<>();
            subTasks.add(new SumRecursiveTask(array, from, split));
            subTasks.add(new SumRecursiveTask(array, split, to));

            return invokeAll(subTasks).stream().mapToInt(ForkJoinTask::join).sum();
        }
    }

}
