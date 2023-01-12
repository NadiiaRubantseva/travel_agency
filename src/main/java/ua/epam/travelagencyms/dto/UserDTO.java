package ua.epam.travelagencyms.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(of = {"email", "name", "surname"})
@Builder
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String email;
    private String name;
    private String surname;
    private String role;
    private String avatar;
}