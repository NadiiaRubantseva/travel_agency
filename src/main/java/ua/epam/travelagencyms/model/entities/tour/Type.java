package ua.epam.travelagencyms.model.entities.tour;

public enum Type {REST(1), EXCURSION(2), SHOPPING(3);
    private final int value;

    Type(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static Type getType(int value) {
        for (Type type: Type.values()) {
            if (type.value == value) {
                return type;
            }
        }
        return REST;
    }
}