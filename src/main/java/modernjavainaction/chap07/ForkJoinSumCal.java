package modernjavainaction.chap07;

import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class ForkJoinSumCal extends RecursiveTask<Long> {

    private final long[] numbers;
    private final int start;
    private final int end;
    public static final long THRESHOLD = 10_000;

    public ForkJoinSumCal(long[] numbers) {
        this(numbers,0,numbers.length);
    }
    public ForkJoinSumCal(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        int length = end-start;
        if (length <= THRESHOLD)  {
            return computeSequentially();
        }
        ForkJoinSumCal leftTask = new ForkJoinSumCal(numbers,start,start+length/2);
        leftTask.fork();
        ForkJoinSumCal rightTask = new ForkJoinSumCal(numbers,start,start+length/2);
        Long rightResult = rightTask.compute();
        Long leftResult = leftTask.join();
        return leftResult+rightResult;
    }

    private Long computeSequentially() {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }
        return sum;
    }

}
