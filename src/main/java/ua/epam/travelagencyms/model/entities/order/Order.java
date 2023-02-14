package ua.epam.travelagencyms.model.entities.order;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ua.epam.travelagencyms.model.entities.tour.Tour;
import ua.epam.travelagencyms.model.entities.user.User;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Order entity class. Matches table 'order' in database.
 * Use Order.builder().fieldName(fieldValue).build() to create an instance
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
@Data
@Builder
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private int orderStatusId;
    @EqualsAndHashCode.Exclude private User user;
    @EqualsAndHashCode.Exclude private Tour tour;
    private int discount;
    private double totalCost;
    private LocalDate date;
}
