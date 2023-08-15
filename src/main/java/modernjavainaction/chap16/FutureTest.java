package modernjavainaction.chap16;

import java.util.concurrent.*;

public class FutureTest {


    public static void main(String[] args) {
        ExecutorService excutor = Executors.newCachedThreadPool();
        Future<Double> future = excutor.submit(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return doSomeLogComputation();
            }
        });
        doSomethingElseV2();
        try {
            Double result = future.get(1, TimeUnit.SECONDS);
            System.out.println(result);
        } catch (ExecutionException ee) {
            ee.printStackTrace();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        } catch (TimeoutException te) {
            te.printStackTrace();
        }
    }

    private static Double doSomeLogComputation() {
        double a1 = 1.2;
        double a2 = 1;
        return a1+a2;
    }

    private static void doSomethingElseV2() {
    }


}
