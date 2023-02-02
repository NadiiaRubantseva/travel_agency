package ua.epam.travelagencyms.utils.constants;

/**
 * Contains all required for validation regexes
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class Regex {

    /** Use it for email only*/
    public static final String EMAIL_REGEX = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";

    /** Use it for password only*/
    public static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}$";

    /** Use it for names and surnames*/
    public static final String NAME_REGEX = "^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\\- ]{1,30}";

    /** Use it for titles*/
    public static final String COMPLEX_NAME_REGEX = "^[\\wА-ЩЬЮЯҐІЇЄа-щьюяґіїє'.,;:+\\-~`!@#$^&*()={}| ]{2,70}";

    /** Use it for numbers only*/
    public static final String NATURAL_NUMBER_REGEX = "\"^[+-]?\\\\d+\\\\.\\\\d+\"";

    private Regex() {}

}