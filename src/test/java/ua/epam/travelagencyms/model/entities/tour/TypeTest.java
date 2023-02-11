package ua.epam.travelagencyms.model.entities.tour;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TypeTest {

    @ParameterizedTest
    @CsvSource({"REST, 1", "EXCURSION, 2", "SHOPPING, 3"})
    void testGetValue(String typeString, String value) {
        Type type = Type.valueOf(typeString);
        int number = Integer.parseInt(value);
        assertEquals(number, type.getValue());
    }

    @ParameterizedTest
    @CsvSource({"REST, 1", "EXCURSION, 2", "SHOPPING, 3"})
    void testGetRole(String typeString, String value) {
        Type type = Type.valueOf(typeString);
        int number = Integer.parseInt(value);
        assertEquals(type, Type.getType(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {0,5,100})
    void testDefaultRole(int number) {
        assertEquals(Type.REST, Type.getType(number));
    }
}