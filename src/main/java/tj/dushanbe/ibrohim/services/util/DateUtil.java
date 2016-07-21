package tj.dushanbe.ibrohim.services.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by ibrohim on 2/21/2015.
 */
public class DateUtil {

    public static final String[] MONTHS = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
            "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};

    public static Integer[] getCurrentDays() {
        LocalDate date = LocalDate.now();
        int lastDay = date.lengthOfMonth();

        Integer[] days = new Integer[lastDay];
        for (int i = 0; i < days.length; i++) {
            days[i] = i + 1;
        }

        return days;
    }

    public static Integer[] getDaysOfMonth(int month, int year) {
        LocalDate date = LocalDate.of(year, month, 1);
        int lastDay = date.lengthOfMonth();

        Integer[] days = new Integer[lastDay];
        for (int i = 0; i < days.length; i++) {
            days[i] = i + 1;
        }

        return days;
    }

    public static Integer[] getYears() {
        Integer[] years = new Integer[100];

        for (int i = 0; i < years.length; i++) {
            years[i] = 2014 + i;
        }
        return years;
    }

    public static int getMonthNumber(String month) {
        int selectedMonth = 0;
        for (int i = 0; i < MONTHS.length; i++) {
            if (MONTHS[i].equals(month)) selectedMonth = i + 1;
        }

        return selectedMonth;
    }

    public static Date getDate(Integer year, Integer month, Integer day) {
        LocalDate localDate = LocalDate.of(year, month, day);
        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);

        return date;
    }

    public static LocalDate getLocalDate(Date date) {
        Instant instant = Instant.ofEpochMilli(date.getTime());
        LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();

        return localDate;
    }

    public static String getDate(Long date) {
        Instant instant = Instant.ofEpochMilli(date);
        LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
        return localDate.toString();
    }
}
