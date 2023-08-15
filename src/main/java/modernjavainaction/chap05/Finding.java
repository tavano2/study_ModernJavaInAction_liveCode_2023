package modernjavainaction.chap05;

import static modernjavainaction.chap04.Dish.menu;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import modernjavainaction.chap04.Dish;

public class Finding {

  public static void main(String... args) {
    if (isVegetarianFriendlyMenu()) {
      System.out.println("Vegetarian friendly");
    }

    System.out.println(isHealthyMenu());
    System.out.println(isHealthyMenu2());

    Optional<Dish> dish = findVegetarianDish();
    dish.ifPresent(d -> System.out.println(d.getName()));

    List<Integer> someNumbers = Arrays.asList(1,2,3,4,5);
    Optional<Integer> firstSquareDivisibleByThree = someNumbers
            .stream()
            .map(n -> n * n)
            .filter(n -> n % 3 == 0)
            .findFirst();
    firstSquareDivisibleByThree.ifPresent(e -> System.out.println(e));
  }

  private static boolean isVegetarianFriendlyMenu() {
    return menu.stream().anyMatch(Dish::isVegetarian);
  }

  private static boolean isHealthyMenu() {
    return menu.stream().allMatch(d -> d.getCalories() < 1000);
  }

  private static boolean isHealthyMenu2() {
    return menu.stream().noneMatch(d -> d.getCalories() >= 1000);
  }

  private static Optional<Dish> findVegetarianDish() {
    return menu.stream().filter(Dish::isVegetarian).findAny();
  }

}
