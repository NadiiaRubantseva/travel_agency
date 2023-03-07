package ua.epam.travelagencyms.model.entities.loyaltyProgram;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoyaltyProgram {
    private long id;
    private int step;
    private int discount;
    private int maxDiscount;
}
