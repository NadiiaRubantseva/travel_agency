package ua.epam.travelagencyms.model.entities.tour;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@Builder
public class Tour implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String title;
    private int persons;
    private double price;
    private byte hot;
    private byte[] imageContent;
    @EqualsAndHashCode.Exclude private int typeId;
    @EqualsAndHashCode.Exclude private int hotelId;
}
