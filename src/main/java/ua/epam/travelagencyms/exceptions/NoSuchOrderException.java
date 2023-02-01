package ua.epam.travelagencyms.exceptions;

import static ua.epam.travelagencyms.exceptions.constants.Message.NO_ORDER;

/**
 * In case of no such order
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class NoSuchOrderException extends ServiceException {
    public NoSuchOrderException() {
        super(NO_ORDER);
    }
}