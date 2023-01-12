package ua.epam.travelagencyms.model.entities.order;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ua.epam.travelagencyms.model.entities.tour.Tour;
import ua.epam.travelagencyms.model.entities.user.User;

import java.io.Serializable;

@Data
@Builder
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private OrderStatus orderStatus;
    @EqualsAndHashCode.Exclude private User user;
    @EqualsAndHashCode.Exclude private Tour tour;
    private int discount;
    private double totalCost;
}
