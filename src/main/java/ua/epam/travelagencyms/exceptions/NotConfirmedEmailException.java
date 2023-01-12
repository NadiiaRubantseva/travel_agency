package ua.epam.travelagencyms.exceptions;


public class NotConfirmedEmailException extends ServiceException {
    public NotConfirmedEmailException() {
        super("not confirmed email");
    }
}