package ua.epam.travelagencyms.model.entities.tour;

/**
 * Type entity enum. Matches table 'type' in database.
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public enum Type {REST(1), EXCURSION(2), SHOPPING(3);
    private final int value;

    Type(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    /**
     * Obtains the type by the value. REST by default.
     * @param value matching type
     * @return the type assigned to this value
     */
    public static Type getType(int value) {
        for (Type type: Type.values()) {
            if (type.value == value) {
                return type;
            }
        }
        return REST;
    }
}