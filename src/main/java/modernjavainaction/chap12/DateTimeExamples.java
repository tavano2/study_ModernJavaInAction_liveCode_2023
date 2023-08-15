package modernjavainaction.chap12;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.nextOrSame;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeExamples {

  private static final ThreadLocal<DateFormat> formatters = new ThreadLocal<DateFormat>() {

    @Override
    protected DateFormat initialValue() {
      return new SimpleDateFormat("dd-MMM-yyyy");
    }
  };

  public static void main(String[] args) {
    useOldDate();
    useLocalDate();
    useTemporalAdjuster();
    useDateFormatter();
  }

  private static void useOldDate() {
    Date date = new Date(114, 2, 18);
    System.out.println(date);

    System.out.println(formatters.get().format(date));

    Calendar calendar = Calendar.getInstance();
    calendar.set(2014, Calendar.FEBRUARY, 18);
    System.out.println(calendar);
  }

  private static void useLocalDate() {
    LocalDate date = LocalDate.of(2014, 3, 18);
    int year = date.getYear(); // 2014
    Month month = date.getMonth(); // MARCH
    int day = date.getDayOfMonth(); // 18
    DayOfWeek dow = date.getDayOfWeek(); // TUESDAY
    int len = date.lengthOfMonth(); // 31 (3월의 길이)
    boolean leap = date.isLeapYear(); // false (윤년이 아님)
    System.out.println(date);

    int y = date.get(ChronoField.YEAR);
    int m = date.get(ChronoField.MONTH_OF_YEAR);
    int d = date.get(ChronoField.DAY_OF_MONTH);
    System.out.println(y);
    System.out.println(m);
    System.out.println(d);

    LocalTime time = LocalTime.of(13, 45, 20); // 13:45:20
    int hour = time.getHour(); // 13
    int minute = time.getMinute(); // 45
    int second = time.getSecond(); // 20
    System.out.println(time);

    LocalDateTime dt1 = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45, 20); // 2014-03-18T13:45
    LocalDateTime dt2 = LocalDateTime.of(date, time);
    LocalDateTime dt3 = date.atTime(13, 45, 20);
    LocalDateTime dt4 = date.atTime(time);
    LocalDateTime dt5 = time.atDate(date);
    System.out.println(dt1);


    LocalDate date1 = dt1.toLocalDate();
    System.out.println(date1);
    LocalTime time1 = dt1.toLocalTime();
    System.out.println(time1);

    Instant instant = Instant.ofEpochSecond(44 * 365 * 86400);
    Instant now = Instant.now();

    Duration d1 = Duration.between(LocalTime.of(13, 45, 10), time); // 10
    Duration d2 = Duration.between(instant, now); // 297742588
    System.out.println(d1.getSeconds());
    System.out.println(d2.getSeconds());

    Duration threeMinutes = Duration.of(3, ChronoUnit.MINUTES);
    System.out.println(threeMinutes);

    JapaneseDate japaneseDate = JapaneseDate.from(date);
    System.out.println(japaneseDate);

    System.out.println("Period 실습");
    Period tenDays  = Period.between(LocalDate.of(2017,9,11),LocalDate.of(2017,9,21));
    System.out.println(tenDays);
    Period tenDays2 = Period.ofDays(10); // P10D
    Period threeWeeks = Period.ofWeeks(3); // P21D
    Period twoYearsSixMonthsOneDay = Period.of(2,6,1); // P2Y6M1D
    System.out.println(tenDays2.getDays());
    System.out.println(threeWeeks.getDays());
    System.out.println(twoYearsSixMonthsOneDay.getYears());
  }

  private static void useTemporalAdjuster() {
    LocalDate date = LocalDate.of(2014, 3, 18);
    date = date.with(nextOrSame(DayOfWeek.SUNDAY)); // 2014-03-23
    date = date.with(lastDayOfMonth()); // 2014-03-31

    date = date.with(new NextWorkingDay()); // 2014-04-01
    date = date.with(nextOrSame(DayOfWeek.FRIDAY)); // 2014-04-04
    date = date.with(new NextWorkingDay()); // 2014-04-07

    date = date.with(nextOrSame(DayOfWeek.FRIDAY)); // 2014-04-11
    System.out.println(date);
    date = date.with(temporal -> {
      DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
      int dayToAdd = 1;
      if (dow == DayOfWeek.FRIDAY) {
        dayToAdd = 3;
      }
      if (dow == DayOfWeek.SATURDAY) {
        dayToAdd = 2;
      }
      return temporal.plus(dayToAdd, ChronoUnit.DAYS);
    });
    System.out.println(date);
  }

  private static class NextWorkingDay implements TemporalAdjuster {

    @Override
    public Temporal adjustInto(Temporal temporal) {
      DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
      int dayToAdd = 1;
      if (dow == DayOfWeek.FRIDAY) {
        dayToAdd = 3;
      }
      if (dow == DayOfWeek.SATURDAY) {
        dayToAdd = 2;
      }
      return temporal.plus(dayToAdd, ChronoUnit.DAYS);
    }
  }

  private static void useDateFormatter() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate date = LocalDate.of(2014, 3, 18);
    String formattedDate = date.format(formatter);
    LocalDate date2 = LocalDate.parse(formattedDate,formatter);

    DateTimeFormatter italianFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.ITALIAN);
    System.out.println(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
    System.out.println(date.format(formatter));
    System.out.println(date.format(italianFormatter));

    DateTimeFormatter complexFormatter = new DateTimeFormatterBuilder()
        .appendText(ChronoField.DAY_OF_MONTH)
        .appendLiteral(". ")
        .appendText(ChronoField.MONTH_OF_YEAR)
        .appendLiteral(" ")
        .appendText(ChronoField.YEAR)
        .parseCaseInsensitive()
        .toFormatter(Locale.ITALIAN);

    System.out.println(date.format(complexFormatter));
  }

}
