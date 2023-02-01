package ua.epam.travelagencyms.exceptions;


import static ua.epam.travelagencyms.exceptions.constants.Message.DUPLICATE_TITLE;

/**
 * Uses to change SQLException to ServiceException
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class DuplicateTitleException extends ServiceException {
    public DuplicateTitleException() {
        super(DUPLICATE_TITLE);
    }
}