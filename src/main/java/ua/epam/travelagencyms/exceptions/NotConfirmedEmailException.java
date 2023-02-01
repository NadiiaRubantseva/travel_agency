package ua.epam.travelagencyms.exceptions;

/**
 * If email is not confirmed by the user according to database return information.
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class NotConfirmedEmailException extends ServiceException {
    public NotConfirmedEmailException() {
        super("not confirmed email");
    }
}