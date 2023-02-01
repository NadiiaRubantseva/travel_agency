package ua.epam.travelagencyms.exceptions;

import static ua.epam.travelagencyms.exceptions.constants.Message.NO_TOUR;

/**
 * In case of no such tour
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class NoSuchTourException extends ServiceException {
    public NoSuchTourException() {
        super(NO_TOUR);
    }
}