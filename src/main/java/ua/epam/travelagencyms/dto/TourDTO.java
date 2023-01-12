package ua.epam.travelagencyms.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(of = {"title", "persons", "price", "hot"})
@Builder
public class TourDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String title;
    private int persons;
    private double price;
    private String hot;
    private String type;
    private String hotel;
    private byte[] image;
    private String decodedImage;
}
