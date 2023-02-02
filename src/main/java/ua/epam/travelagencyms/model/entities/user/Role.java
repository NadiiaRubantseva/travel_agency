package ua.epam.travelagencyms.model.entities.user;

/**
 * Role entity enum. Matches table 'role' in database.
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public enum Role {ADMIN(1), USER(2);
    private final int value;

    Role(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    /**
     * Obtains the role by the value. USER by default.
     * @param value matching role
     * @return the role assigned to this value
     */
    public static Role getRole(int value) {
        for (Role role: Role.values()) {
            if (role.value == value) {
                return role;
            }
        }
        return USER;
    }
}
