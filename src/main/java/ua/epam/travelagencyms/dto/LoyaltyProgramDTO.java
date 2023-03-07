package ua.epam.travelagencyms.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Builder
public class LoyaltyProgramDTO {
    private int step;
    private int discount;
    private int maxDiscount;
}
