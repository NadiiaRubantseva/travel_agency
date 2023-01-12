package ua.epam.travelagencyms.exceptions;

import static ua.epam.travelagencyms.exceptions.constants.Message.NO_USER;

public class NoSuchUserException extends ServiceException {
    public NoSuchUserException() {
        super(NO_USER);
    }
}