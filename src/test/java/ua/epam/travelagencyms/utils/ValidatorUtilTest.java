package ua.epam.travelagencyms.utils;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.exceptions.IncorrectFormatException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ua.epam.travelagencyms.exceptions.constants.Message.*;
import static ua.epam.travelagencyms.utils.ValidatorUtil.*;

class ValidatorUtilTest {

    @Test
    void testValidateEmail() {
        String email = "karl.kory@amber.com.tv";
        assertDoesNotThrow(() -> validateEmail(email));
    }

    @Test
    void testValidateBadEmail() {
        String email = "karl.kory.amber.com.tv";
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, () -> validateEmail(email));
        assertEquals(ENTER_CORRECT_EMAIL, exception.getMessage());

        String email2 = "karl.kory@amber";
        assertThrows(IncorrectFormatException.class, () -> validateEmail(email2));

        String email3 = "karl.kory@.amber";
        assertThrows(IncorrectFormatException.class, () -> validateEmail(email3));

        String email4 = "@amber.com.tv";
        assertThrows(IncorrectFormatException.class, () -> validateEmail(email4));

        String email5 = "karl.kory@amber.com.t";
        assertThrows(IncorrectFormatException.class, () -> validateEmail(email5));
    }

    @Test
    void testValidatePassword() {
        String password = "Password1";
        assertDoesNotThrow(() -> validatePassword(password));

        String password2 = "Password1_";
        assertDoesNotThrow(() -> validatePassword(password2));
    }

    @Test
    void testValidateBadPassword() {
        String password = "NoDigitPass";
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, () -> validatePassword(password));
        assertEquals(ENTER_CORRECT_PASSWORD, exception.getMessage());

        String password2 = "no_upper_letters1";
        assertThrows(IncorrectFormatException.class, () -> validatePassword(password2));

        String password3 = "NO_LOW_CASE_1";
        assertThrows(IncorrectFormatException.class, () -> validatePassword(password3));

        String password4 = "Short1";
        assertThrows(IncorrectFormatException.class, () -> validatePassword(password4));

        String password5 = "TooLongPassword1234567890";
        assertThrows(IncorrectFormatException.class, () -> validatePassword(password5));
    }

    @Test
    void testValidateName() {
        String name = "Joe Biden";
        assertDoesNotThrow(() -> validateName(name, ENTER_CORRECT_NAME));

        String name2 = "Залужний";
        assertDoesNotThrow(() -> validateName(name2, ENTER_CORRECT_NAME));

        String name3 = "Квітка-Основ'яненко";
        assertDoesNotThrow(() -> validateName(name3, ENTER_CORRECT_NAME));
    }

    @Test
    void testValidateBadName() {
        String name = "Joe Biden 2";
        IncorrectFormatException exception =
                assertThrows(IncorrectFormatException.class, () -> validateName(name, ENTER_CORRECT_NAME));
        assertEquals(ENTER_CORRECT_NAME, exception.getMessage());

        String name2 = "Залужный";
        assertThrows(IncorrectFormatException.class, () -> validateName(name2, ENTER_CORRECT_NAME));

        String name3 = "Занадтодовгеім'ямаєбутинебільшетридцятисимволів";
        assertThrows(IncorrectFormatException.class, () -> validateName(name3, ENTER_CORRECT_NAME));
    }

    @Test
    void testValidateComplexName() {
        String title = "1";
        IncorrectFormatException exception =
                assertThrows(IncorrectFormatException.class, () -> validateComplexName(title, ENTER_CORRECT_TITLE));
        assertEquals(ENTER_CORRECT_TITLE, exception.getMessage());
    }

    @Test
    void testValidatePersons() {
        int persons = -1;
        IncorrectFormatException exception =
                assertThrows(IncorrectFormatException.class, () -> validatePersons(persons, ENTER_CORRECT_NUMBERS));
        assertEquals(ENTER_CORRECT_NUMBERS, exception.getMessage());
    }

    @Test
    void testValidatePrice() {
        double price = -1;
        IncorrectFormatException exception =
                assertThrows(IncorrectFormatException.class, () -> validatePrice(price, ENTER_CORRECT_NUMBERS));
        assertEquals(ENTER_CORRECT_NUMBERS, exception.getMessage());
    }
}