package ua.epam.travelagencyms.exceptions;

import static ua.epam.travelagencyms.exceptions.constants.Message.PASSWORD_MATCHING;

/**
 * If password doesn't match confirmation password.
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class PasswordMatchingException extends ServiceException{
    public PasswordMatchingException() {
        super(PASSWORD_MATCHING);
    }
}