package ua.epam.travelagencyms.exceptions;

import static ua.epam.travelagencyms.exceptions.constants.Message.NO_TOUR;

public class NoSuchTourException extends ServiceException {
    public NoSuchTourException() {
        super(NO_TOUR);
    }
}