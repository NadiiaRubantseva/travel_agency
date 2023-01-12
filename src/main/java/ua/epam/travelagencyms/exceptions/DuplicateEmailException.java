package ua.epam.travelagencyms.exceptions;


import static ua.epam.travelagencyms.exceptions.constants.Message.DUPLICATE_EMAIL;

public class DuplicateEmailException extends ServiceException {
    public DuplicateEmailException() {
        super(DUPLICATE_EMAIL);
    }
}