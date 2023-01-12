package ua.epam.travelagencyms.exceptions;


import static ua.epam.travelagencyms.exceptions.constants.Message.DUPLICATE_TITLE;

public class DuplicateTitleException extends ServiceException {
    public DuplicateTitleException() {
        super(DUPLICATE_TITLE);
    }
}