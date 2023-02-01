package ua.epam.travelagencyms.exceptions;

import static ua.epam.travelagencyms.exceptions.constants.Message.INCORRECT_CODE;

/**
 * If security code does not match database security code
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class IncorrectCodeException extends ServiceException {
    public IncorrectCodeException() {
        super(INCORRECT_CODE);
    }
}