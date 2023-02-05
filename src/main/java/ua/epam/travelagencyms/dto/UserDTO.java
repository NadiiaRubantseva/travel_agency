package ua.epam.travelagencyms.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * UserDTO class. Password field is absent.
 * Use UserDTO.builder().fieldName(fieldValue).build() to create an instance
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
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
    private String status;
    @ToString.Exclude
    private String avatar;
    private String discount;
}