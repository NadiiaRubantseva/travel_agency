package ua.epam.travelagencyms.model.entities.user;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * User entity class. Matches table 'user' in database.
 * Use User.builder().fieldName(fieldValue).build() to create an instance
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
@Data
@Builder
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String email;
    private transient String password;
    private String name;
    private String surname;
    private boolean isBlocked;
    private byte[] avatar;
    private int discount;
    @EqualsAndHashCode.Exclude private int roleId;
}
