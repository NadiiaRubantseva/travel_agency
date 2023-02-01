package ua.epam.travelagencyms.exceptions;

/**
 * Wrapper for SQLException
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class DAOException extends Exception {

    public DAOException(String message, Throwable cause) {
        super(cause);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }
    public DAOException(String message) {
        super();
    }
}