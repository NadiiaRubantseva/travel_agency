package ua.epam.travelagencyms.model.entities.user;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@Builder
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String email;
    private transient String password;
    private String name;
    private String surname;
    private byte[] avatar;
    @EqualsAndHashCode.Exclude private int roleId;
}
