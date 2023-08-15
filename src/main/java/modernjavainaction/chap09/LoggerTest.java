package modernjavainaction.chap09;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class LoggerTest {
    private static boolean FINER = false;
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(2,3,4,5);
        numbers.stream()
                .map(x -> x + 17)
                .filter(x -> x % 2 == 0)
                .limit(3)
                .forEach(System.out::println);
    }

}
