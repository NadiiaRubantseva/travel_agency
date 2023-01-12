package ua.epam.travelagencyms.utils.constants;

public class Email {

    public static final String SUBJECT_GREETINGS = "Welcome to Travel Agency!";
    public static final String SUBJECT_NOTIFICATION = "Travel Agency notification!";
    public static final String EMAIL_VERIFICATION = "Email Verification";
    public static final String HELLO = "Hello %s,<br>";
    public static final String INFORMATION = "We have some important information for you:";
    public static final String SIGNATURE = "Yours truly,<br>Travel Agency team";
    public static final String A_HREF = "<a href=";
    public static final String DOUBLE_ENTER = "<br><br>";
    public static final String MESSAGE_RESET_PASSWORD = HELLO +
            INFORMATION +
            DOUBLE_ENTER +
            "Your temporary password is %s. Do not forget to change it in your account!" +
            DOUBLE_ENTER +
            "Enter your account " +
            A_HREF + "%s" + "/signIn.jsp>here</a>, " +
            DOUBLE_ENTER +
            SIGNATURE;

    public static final String MESSAGE_VERIFY_EMAIL =
            "Registration successfully. Please verify your account using this code: %s." +
            DOUBLE_ENTER +
            SIGNATURE;

    private Email() {}

}
