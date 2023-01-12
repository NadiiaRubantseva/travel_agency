package ua.epam.travelagencyms.exceptions;

import static ua.epam.travelagencyms.exceptions.constants.Message.*;

public class IncorrectPasswordException extends ServiceException{
    public IncorrectPasswordException() {
        super(WRONG_PASSWORD);
    }
}