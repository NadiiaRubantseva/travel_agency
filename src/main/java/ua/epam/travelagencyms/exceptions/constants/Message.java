package ua.epam.travelagencyms.exceptions.constants;

/**
 * Contains messages for all user-defined exceptions
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class Message {
    public static final String NO_USER = "error.email.absent";
    public static final String NO_ORDER = "error.order.absent";
    public static final String NO_TOUR = "error.tour.absent";
    public static final String ENTER_CORRECT_EMAIL = "error.email.format";
    public static final String ENTER_CORRECT_PASSWORD = "error.pass.format";
    public static final String PASSWORD_MATCHING = "error.pass.match";
    public static final String WRONG_PASSWORD = "error.pass.wrong";
    public static final String DUPLICATE_EMAIL = "error.email.duplicate";
    public static final String BAD_IMAGE = "error.image.bad";
    public static final String ENTER_CORRECT_NAME = "error.name.format";
    public static final String ENTER_CORRECT_SURNAME = "error.surname.format";
    public static final String ENTER_CORRECT_TITLE = "error.title.format";
    public static final String ENTER_CORRECT_NUMBERS = "error.number.format";
    public static final String DUPLICATE_TITLE = "error.title.duplicate";
    public static final String INCORRECT_CODE = "error.code.incorrect";

    public static final String ERROR = "unsuccessful";

    private Message() {}
}