package ua.epam.travelagencyms.exceptions;

import static ua.epam.travelagencyms.exceptions.constants.Message.*;

/**
 * If password does not match database password
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class IncorrectPasswordException extends ServiceException{
    public IncorrectPasswordException() {
        super(WRONG_PASSWORD);
    }
}