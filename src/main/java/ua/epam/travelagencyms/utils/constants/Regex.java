package ua.epam.travelagencyms.utils.constants;

public class Regex {
    public static final String EMAIL_REGEX = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
    public static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}$";
    public static final String NAME_REGEX = "^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\\- ]{1,30}";
    public static final String COMPLEX_NAME_REGEX = "^[\\wА-ЩЬЮЯҐІЇЄа-щьюяґіїє'.,;:+\\-~`!@#$^&*()={}| ]{2,70}";
    public static final String NATURAL_NUMBER_REGEX = "\"^[+-]?\\\\d+\\\\.\\\\d+\"";

    private Regex() {}

}