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

    /** Use it for passwords only.
     *  Explanation:
     *      1) ^ matches the start of the string;
     *      2) (?=.*[a-z]) is a positive lookahead that requires the presence of at least one lowercase letter;
     *      3) (?=.*[A-Z]) is a positive lookahead that requires the presence of at least one uppercase letter;
     *      4) (?=.*[0-9]) is a positive lookahead that requires the presence of at least one digit
     *      5) .{8,20} matches any character (except a newline) 8 to 20 times, which is the length requirement for the password
     *      6) $ matches the end of the string
     */
    public static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}$";

    /** Use it for names and surnames.
     *  Explanation:
     *      1) ^ and $ anchor the regex to the start and end of the string respectively.
     *      2) [\p{Lu}\p{Ll}'’-]{1,30} matches between 1 and 30 uppercase or lowercase letters, apostrophes, or dashes.
     *      3) ([\p{Zs}\-][\p{Lu}\p{Ll}'’-]{1,29})? matches an optional occurrence of a space or dash followed by between 1 and 29 uppercase or lowercase letters, apostrophes, or dashes.
     *      This allows for the possibility of a two-word name with a dash or space between them.
     *      4) \p{Lu} matches any uppercase letter in the Unicode character set, including those used in the Ukrainian alphabet.
     *      5) \p{Ll} matches any lowercase letter in the Unicode character set, including those used in the Ukrainian alphabet.
     *      6) \p{Zs} matches any Unicode whitespace character, including spaces used in the Ukrainian language.
     */
    public static final String NAME_REGEX = "^[\\p{Lu}\\p{Ll}'’-]{1,30}([\\p{Zs}\\-][\\p{Lu}\\p{Ll}'’-]{1,29})?$";

    /** Use it for titles*/
    public static final String COMPLEX_NAME_REGEX = "^[\\wА-ЩЬЮЯҐІЇЄа-щьюяґіїє'.,;:+\\-~`!@#$^&*()={}| ]{2,70}";

    /** Use it for numbers only*/
    public static final String NATURAL_NUMBER_REGEX = "\"^[+-]?\\\\d+\\\\.\\\\d+\"";

    private Regex() {}

}