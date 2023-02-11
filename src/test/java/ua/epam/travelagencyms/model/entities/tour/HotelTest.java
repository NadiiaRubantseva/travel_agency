package ua.epam.travelagencyms.model.entities.tour;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HotelTest {

    @ParameterizedTest
    @CsvSource({"HOTEL, 1", "HOSTEL, 2", "MOTEL, 3"})
    void testGetValue(String hotelString, String value) {
        Hotel hotel = Hotel.valueOf(hotelString);
        int number = Integer.parseInt(value);
        assertEquals(number, hotel.getValue());
    }

    @ParameterizedTest
    @CsvSource({"HOTEL, 1", "HOSTEL, 2", "MOTEL, 3"})
    void testGetRole(String hotelString, String value) {
        Hotel hotel = Hotel.valueOf(hotelString);
        int number = Integer.parseInt(value);
        assertEquals(hotel, Hotel.getHotel(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 5, 100})
    void testDefaultRole(int number) {
        assertEquals(Hotel.HOTEL, Hotel.getHotel(number));
    }
}