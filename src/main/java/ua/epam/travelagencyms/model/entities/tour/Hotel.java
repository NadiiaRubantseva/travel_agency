package ua.epam.travelagencyms.model.entities.tour;

public enum Hotel {HOTEL(1), HOSTEL(2), MOTEL(3);
    private final int value;

    Hotel(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static Hotel getHotel(int value) {
        for (Hotel hotel: Hotel.values()) {
            if (hotel.value == value) {
                return hotel;
            }
        }
        return HOTEL;
    }
}