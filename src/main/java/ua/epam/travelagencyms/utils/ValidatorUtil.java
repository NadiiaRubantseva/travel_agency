package ua.epam.travelagencyms.utils;

import ua.epam.travelagencyms.exceptions.*;
import ua.epam.travelagencyms.utils.constants.Regex;

import static ua.epam.travelagencyms.exceptions.constants.Message.*;

public final class ValidatorUtil {

    public static void validateEmail(String email) throws IncorrectFormatException {
        validateFormat(email, Regex.EMAIL_REGEX, ENTER_CORRECT_EMAIL);
    }

    public static void validatePassword(String password) throws IncorrectFormatException {
        validateFormat(password, Regex.PASSWORD_REGEX, ENTER_CORRECT_PASSWORD);
    }

    private static void validateFormat(String name, String regex,String message) throws IncorrectFormatException {
        if (name == null || !name.matches(regex))
            throw new IncorrectFormatException(message);
    }

    public static void checkPasswordMatching(String password, String confirmPassword) throws PasswordMatchingException {
        if (!password.equals(confirmPassword)) {
            throw new PasswordMatchingException();
        }
    }

    public static void validateName(String name, String message) throws IncorrectFormatException {
        validateFormat(name, Regex.NAME_REGEX, message);
    }

    public static void validateId(String id) throws IncorrectFormatException {
        validateFormat(id, Regex.ID_REGEX, ENTER_CORRECT_ID);
    }

    public static void validateComplexName(String name, String message) throws IncorrectFormatException {
        validateFormat(name, Regex.COMPLEX_NAME_REGEX, message);
    }

    public static void validatePersons(int persons, String message) throws IncorrectFormatException {
        validateFormat(String.valueOf(persons), Regex.NATURAL_NUMBER_REGEX, message);
    }
    public static void validatePrice(double price, String message) throws IncorrectFormatException {
        validateFormat(String.valueOf(price), Regex.NATURAL_NUMBER_REGEX, message);
    }

    public static long getUserId(String idString) throws ServiceException {
        return checkId(idString, new NoSuchUserException());
    }

    public static long getTourId(String idString) throws ServiceException {
        return checkId(idString, new NoSuchTourException());
    }

    public static long getOrderId(String idString) throws ServiceException {
        return checkId(idString, new NoSuchOrderException());
    }

    private static long checkId(String idString, ServiceException exception) throws ServiceException {
        long id;
        try {
            id = Long.parseLong(idString);
        } catch (NumberFormatException e) {
            throw exception;
        }
        return id;
    }

    public static long getLong(String longString) throws ServiceException {
        long result;
        try {
            result = Long.parseLong(longString);
        } catch (NumberFormatException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    public static int getInt(String intString) throws ServiceException {
        int result;
        try {
            result = Integer.parseInt(intString);
        } catch (NumberFormatException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    private ValidatorUtil() {}
}