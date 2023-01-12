package ua.epam.travelagencyms.exceptions;

import static ua.epam.travelagencyms.exceptions.constants.Message.INCORRECT_CODE;

public class IncorrectCodeException extends ServiceException {
    public IncorrectCodeException() {
        super(INCORRECT_CODE);
    }
}