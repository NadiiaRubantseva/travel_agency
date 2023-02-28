package ua.epam.travelagencyms.utils;

import java.text.DecimalFormat;

public class NumbersUtil {

    public static String roundUpToInteger (double number) {
        DecimalFormat df = new DecimalFormat("#");
        return df.format(number >= 0 ? Math.floor(number + 0.5) : Math.ceil(number - 0.5));
    }

}
