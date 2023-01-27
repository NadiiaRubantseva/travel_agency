package ua.epam.travelagencyms.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(of = {"id"})
@Builder
public class OrderDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String orderStatus;
    private long userId;
    private String userEmail;
    private String userName;
    private String userSurname;
    private long tourId;
    private String tourTitle;
    private double tourPrice;
    private int discount;
    private double totalCost;
    private String date;
}
