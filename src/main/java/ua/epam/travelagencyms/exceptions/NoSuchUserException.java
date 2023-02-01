package ua.epam.travelagencyms.exceptions;

import static ua.epam.travelagencyms.exceptions.constants.Message.NO_USER;

/**
 * In case of no such user
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class NoSuchUserException extends ServiceException {
    public NoSuchUserException() {
        super(NO_USER);
    }
}