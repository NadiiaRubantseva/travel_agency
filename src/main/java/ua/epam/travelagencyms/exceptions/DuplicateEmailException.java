package ua.epam.travelagencyms.exceptions;


import static ua.epam.travelagencyms.exceptions.constants.Message.DUPLICATE_EMAIL;

/**
 * Uses to change SQLException to ServiceException
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class DuplicateEmailException extends ServiceException {
    public DuplicateEmailException() {
        super(DUPLICATE_EMAIL);
    }
}