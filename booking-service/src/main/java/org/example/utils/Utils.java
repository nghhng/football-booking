package org.example.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class Utils {
    public static boolean isWeekend(String dateString) {
        LocalDate date = LocalDate.parse(dateString);
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
}
