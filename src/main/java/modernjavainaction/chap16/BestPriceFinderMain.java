package modernjavainaction.chap16;

import org.openjdk.jmh.Main;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class BestPriceFinderMain {

  private static BestPriceFinder bestPriceFinder = new BestPriceFinder();

  private static final List<Shop> shops = Arrays.asList(
          new Shop("BestPrice"),
          new Shop("LetsSaveBig"),
          new Shop("MyFavoriteShop"),
          new Shop("BuyItAll"),
          new Shop("ShopEasy"));

  private static final Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), new ThreadFactory() {
    @Override
    public Thread newThread(Runnable r) {
      Thread t = new Thread(r);
      t.setDaemon(true);
      return t;
    }
  });

  public static List<String> findPrices(String product) {
     List<CompletableFuture<String>> priceFuture =  shops.parallelStream()
            .map(shop -> CompletableFuture.supplyAsync(
                            () -> String.format("%s price is %s",shop.getName(), shop.getPrice(product))
                                                , executor)
                    )
            .collect(Collectors.toList());
    return priceFuture.stream().map(CompletableFuture::join).collect(Collectors.toList());
  }

  public static void main(String[] args) {
    long start = System.nanoTime();
    System.out.println(start);
    System.out.println(findPrices("MyPhone27S"));
    long duration = (System.nanoTime() - start) / 1_000_000;
    System.out.println(duration);
    /*
    execute("sequential", () -> bestPriceFinder.findPricesSequential("myPhone27S"));
    execute("parallel", () -> bestPriceFinder.findPricesParallel("myPhone27S"));
    execute("composed CompletableFuture", () -> bestPriceFinder.findPricesFuture("myPhone27S"));
    bestPriceFinder.printPricesStream("myPhone27S");

     */
  }

  private static void execute(String msg, Supplier<List<String>> s) {
    long start = System.nanoTime();
    System.out.println(s.get());
    long duration = (System.nanoTime() - start) / 1_000_000;
    System.out.println(msg + " done in " + duration + " msecs");
  }

}
