package ua.epam.travelagencyms.model.entities.tour;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Tour entity class. Matches table 'tour' in database.
 * Use Tour.builder().fieldName(fieldValue).build() to create an instance
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
@Data
@Builder
public class Tour implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String title;
    private int persons;
    private double price;
    private byte hot;
    private byte[] image;
    private String description;
    @EqualsAndHashCode.Exclude private int typeId;
    @EqualsAndHashCode.Exclude private int hotelId;
}
