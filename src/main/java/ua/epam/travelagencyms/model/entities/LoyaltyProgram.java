package ua.epam.travelagencyms.model.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoyaltyProgram {
    private int step;
    private int discount;
    private int maxDiscount;
}
