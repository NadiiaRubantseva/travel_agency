package ua.epam.travelagencyms.exceptions;

/**
 * Main exception for all types of Web App mistakes.
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class ServiceException extends Exception{
    public ServiceException() {}
    public ServiceException(String message) {
        super(message);
    }
    public ServiceException(Throwable cause) {
        super(cause);
    }
}