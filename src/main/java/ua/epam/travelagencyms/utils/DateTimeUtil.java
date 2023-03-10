package ua.epam.travelagencyms.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static String getDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }
}
