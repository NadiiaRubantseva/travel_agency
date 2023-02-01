package ua.epam.travelagencyms.exceptions;

/**
 * Uses different messages for different fields
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class IncorrectFormatException extends ServiceException {
    public IncorrectFormatException(String message) {
        super(message);
    }
}