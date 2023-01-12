package ua.epam.travelagencyms.exceptions;

import static ua.epam.travelagencyms.exceptions.constants.Message.PASSWORD_MATCHING;

public class PasswordMatchingException extends ServiceException{
    public PasswordMatchingException() {
        super(PASSWORD_MATCHING);
    }
}