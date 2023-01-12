package ua.epam.travelagencyms.exceptions;

import static ua.epam.travelagencyms.exceptions.constants.Message.NO_ORDER;

public class NoSuchOrderException extends ServiceException {
    public NoSuchOrderException() {
        super(NO_ORDER);
    }
}