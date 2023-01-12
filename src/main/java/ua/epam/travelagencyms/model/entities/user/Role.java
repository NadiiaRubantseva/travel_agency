package ua.epam.travelagencyms.model.entities.user;

public enum Role {ADMIN(1), USER(2);
    private final int value;

    Role(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static Role getRole(int value) {
        for (Role role: Role.values()) {
            if (role.value == value) {
                return role;
            }
        }
        return USER;
    }
}
