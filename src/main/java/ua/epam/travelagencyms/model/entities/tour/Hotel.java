package ua.epam.travelagencyms.model.entities.tour;

/**
 * Hotel entity enum. Matches table 'hotel' in database.
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public enum Hotel {HOTEL(1), HOSTEL(2), MOTEL(3);
    private final int value;

    Hotel(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    /**
     * Obtains the role by the value. HOTEL by default.
     * @param value matching hotel
     * @return the hotel assigned to this value
     */
    public static Hotel getHotel(int value) {
        for (Hotel hotel: Hotel.values()) {
            if (hotel.value == value) {
                return hotel;
            }
        }
        return HOTEL;
    }
}