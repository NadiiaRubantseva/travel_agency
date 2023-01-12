package ua.epam.travelagencyms.utils;

import ua.epam.travelagencyms.exceptions.IncorrectFormatException;
import ua.epam.travelagencyms.exceptions.PasswordMatchingException;
import ua.epam.travelagencyms.utils.constants.Regex;

import static ua.epam.travelagencyms.exceptions.constants.Message.ENTER_CORRECT_EMAIL;
import static ua.epam.travelagencyms.exceptions.constants.Message.ENTER_CORRECT_PASSWORD;

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

    public static void validateComplexName(String name, String message) throws IncorrectFormatException {
        validateFormat(name, Regex.COMPLEX_NAME_REGEX, message);
    }

    public static void validatePersons(int persons, String message) throws IncorrectFormatException {
        validateFormat(String.valueOf(persons), Regex.NATURAL_NUMBER_REGEX, message);
    }
    public static void validatePrice(double price, String message) throws IncorrectFormatException {
        validateFormat(String.valueOf(price), Regex.NATURAL_NUMBER_REGEX, message);
    }
    private ValidatorUtil() {}
}