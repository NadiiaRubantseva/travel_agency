package ua.epam.travelagencyms.model.entities.user;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoleTest {

    @ParameterizedTest
    @CsvSource({"ADMIN, 1", "USER, 2"})
    void testGetValue(String roleString, String value) {
        Role role = Role.valueOf(roleString);
        int number = Integer.parseInt(value);
        assertEquals(number, role.getValue());
    }

    @ParameterizedTest
    @CsvSource({"ADMIN, 1", "USER, 2"})
    void testGetRole(String roleString, String value) {
        Role role = Role.valueOf(roleString);
        int number = Integer.parseInt(value);
        assertEquals(role, Role.getRole(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {0,5,100})
    void testDefaultRole(int number) {
        assertEquals(Role.USER, Role.getRole(number));
    }
}