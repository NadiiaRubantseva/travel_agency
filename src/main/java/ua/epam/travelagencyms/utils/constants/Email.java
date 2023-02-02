package ua.epam.travelagencyms.utils.constants;

/**
 * Contains letter's subjects and bodies
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class Email {

    /** For new users only */
    public static final String SUBJECT_GREETINGS = "Welcome to Travel Agency!";

    /** Any notification letter subject */
    public static final String SUBJECT_NOTIFICATION = "Travel Agency notification!";

    /** Letter subject for email verification letter */
    public static final String EMAIL_VERIFICATION = "Email Verification";

    /** Place user's name instead of %s */
    public static final String HELLO = "Hello %s,<br>";
    public static final String INFORMATION = "We have some important information for you:";
    public static final String SIGNATURE = "Yours truly,<br>Travel Agency team";
    public static final String A_HREF = "<a href=";
    public static final String DOUBLE_ENTER = "<br><br>";

    /** Place password and then correct domain name instead of %s */
    public static final String MESSAGE_RESET_PASSWORD = HELLO +
            INFORMATION +
            DOUBLE_ENTER +
            "Your temporary password is %s. Do not forget to change it in your account!" +
            DOUBLE_ENTER +
            "Enter your account " +
            A_HREF + "%s" + "/signIn.jsp>here</a>, " +
            DOUBLE_ENTER +
            SIGNATURE;

    /** Place security code instead of %s */
    public static final String MESSAGE_VERIFY_EMAIL =
            "Registration successfully. Please verify your account using this code: %s." +
            DOUBLE_ENTER +
            SIGNATURE;

    private Email() {}

}
