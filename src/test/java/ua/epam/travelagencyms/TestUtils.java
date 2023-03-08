package ua.epam.travelagencyms;

import ua.epam.travelagencyms.dto.LoyaltyProgramDTO;
import ua.epam.travelagencyms.dto.OrderDTO;
import ua.epam.travelagencyms.dto.TourDTO;
import ua.epam.travelagencyms.dto.UserDTO;
import ua.epam.travelagencyms.model.entities.loyaltyProgram.LoyaltyProgram;
import ua.epam.travelagencyms.model.entities.order.Order;
import ua.epam.travelagencyms.model.entities.tour.Tour;
import ua.epam.travelagencyms.model.entities.user.User;

import java.time.LocalDate;
import java.util.Arrays;

public final class TestUtils {

    // shared
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int ONE_HUNDRED = 100;
    public static final String QUERY = "query";
    public static final String FILTER = "filter";
    public static final long ID_VALUE = 1L;
    public static final String ID_STRING_VALUE = "1";

    // User

    public static final String EMAIL_VALUE = "my.email@mail.com";
    public static final String INCORRECT_EMAIL_VALUE = "my.incorrect.@.email@mail.com";
    public static final String PASSWORD_VALUE = "myPassword@!1";
    public static final String INCORRECT_PASSWORD_VALUE = "myIncorrectPassword@!1";
    public static final String NAME_VALUE = "MyName";
    public static final String INCORRECT_NAME_VALUE = "My @ 1Name";
    public static final String SURNAME_VALUE = "MySurname";
    public static final String INCORRECT_SURNAME_VALUE = "My @ 1Surname";
    public static final int DISCOUNT_VALUE = 10;
    public static final String DISCOUNT_STRING_VALUE = "10";
    public static final byte[] AVATAR_VALUE = new byte[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
    public static final String AVATAR_ENCODED_VALUE = Arrays.toString(AVATAR_VALUE);
    public static final boolean IS_BLOCKED_VALUE = false;
    public static final String IS_BLOCKED_STRING_VALUE = "Active";
    public static final boolean IS_EMAIL_VERIFIED_VALUE = true;
    public static final String IS_EMAIL_VERIFIED_STRING_VALUE = "Yes";
    public static final String VERIFICATION_CODE_VALUE = "123456";
    public static final String INCORRECT_VERIFICATION_CODE_VALUE = "654321";
    public static final int ROLE_ID_VALUE = 2;
    public static final String ROLE_VALUE = "USER";


    // Tour

    public static final String TITLE_VALUE = "AWESOME_TITLE";
    public static final int PERSONS_VALUE = 2;
    public static final double PRICE_VALUE = 1000;
    public static final String PRICE_STRING_VALUE = "1000";
    public static final byte HOT_VALUE = 1;
    public static final String HOT_STRING_VALUE = "true";
    public static final int TYPE_ID_VALUE = 3;
    public static final String TYPE_VALUE = "SHOPPING";
    public static final int HOTEL_ID_VALUE = 2;
    public static final String HOTEL_VALUE = "HOSTEL";
    public static final byte[] IMAGE_VALUE = new byte[]{1};
    public static final String IMAGE_ENCODED_VALUE = "data:image/jpeg;base64,AQ==";
    public static final String DESCRIPTION_VALUE = "My awesome description";


    // Order
    public static final int ORDER_STATUS_ID_VALUE = 2;
    public static final String ORDER_STATUS_VALUE = "PAID";
    public static final double TOTAL_COST_VALUE = 900;
    public static final LocalDate DATE_VALUE = LocalDate.of(2023,2,12);
    public static final String DATE_STRING_VALUE = "2023-02-12";

    // Loyalty Program
    public static final int STEP_VALUE = 2;
    public static final String STEP_STRING_VALUE = "2";
    public static final int LOYALTY_PROGRAM_DISCOUNT_VALUE = 5;
    public static final String LOYALTY_PROGRAM_DISCOUNT_STRING_VALUE = "5";
    public static final int MAX_DISCOUNT_VALUE = 30;
    public static final String MAX_DISCOUNT_STRING_VALUE = "30";

    public static User getTestUser() {
        return User.builder()
                .id(ID_VALUE)
                .email(EMAIL_VALUE)
                .password(PASSWORD_VALUE)
                .name(NAME_VALUE)
                .surname(SURNAME_VALUE)
                .discount(DISCOUNT_VALUE)
                .avatar(IMAGE_ENCODED_VALUE)
                .isBlocked(IS_BLOCKED_VALUE)
                .isEmailVerified(IS_EMAIL_VERIFIED_VALUE)
                .verificationCode(VERIFICATION_CODE_VALUE)
                .roleId(ROLE_ID_VALUE)
                .build();
    }

    public static UserDTO getTestUserDTO() {
        return UserDTO.builder()
                .id(ID_VALUE)
                .email(EMAIL_VALUE)
                .name(NAME_VALUE)
                .surname(SURNAME_VALUE)
                .discount(DISCOUNT_VALUE)
                .avatar(IMAGE_ENCODED_VALUE)
                .isBlocked(IS_BLOCKED_STRING_VALUE)
                .isEmailVerified(IS_EMAIL_VERIFIED_STRING_VALUE)
                .role(ROLE_VALUE)
                .build();
    }

    public static Tour getTestTour() {
        return Tour.builder()
                .id(ID_VALUE)
                .title(TITLE_VALUE)
                .persons(PERSONS_VALUE)
                .price(PRICE_VALUE)
                .hot(HOT_VALUE)
                .typeId(TYPE_ID_VALUE)
                .hotelId(HOTEL_ID_VALUE)
                .image(IMAGE_ENCODED_VALUE)
                .description(DESCRIPTION_VALUE)
                .build();
    }

    public static TourDTO getTestTourDTO() {
        return TourDTO.builder()
                .id(ID_VALUE)
                .title(TITLE_VALUE)
                .persons(PERSONS_VALUE)
                .price(PRICE_VALUE)
                .hot(HOT_STRING_VALUE)
                .type(TYPE_VALUE)
                .hotel(HOTEL_VALUE)
                .image(IMAGE_ENCODED_VALUE)
                .description(DESCRIPTION_VALUE)
                .build();
    }

    public static Order getTestOrder() {
        return Order.builder()
                .id(ID_VALUE)
                .orderStatusId(ORDER_STATUS_ID_VALUE)
                .user(getTestUser())
                .tour(getTestTour())
                .totalCost(TOTAL_COST_VALUE)
                .discount(DISCOUNT_VALUE)
                .date(DATE_VALUE)
                .build();
    }

    public static OrderDTO getTestOrderDTO() {
        return OrderDTO.builder()
                .id(ID_VALUE)
                .orderStatus(ORDER_STATUS_VALUE)
                .userId(ID_VALUE)
                .userEmail(EMAIL_VALUE)
                .userName(NAME_VALUE)
                .userSurname(SURNAME_VALUE)
                .tourId(ID_VALUE)
                .tourTitle(TITLE_VALUE)
                .tourPrice(PRICE_VALUE)
                .discount(DISCOUNT_VALUE)
                .totalCost(TOTAL_COST_VALUE)
                .date(DATE_STRING_VALUE)
                .build();
    }

    public static LoyaltyProgram getTestLoyaltyProgram() {
        return LoyaltyProgram.builder()
                .step(STEP_VALUE)
                .discount(LOYALTY_PROGRAM_DISCOUNT_VALUE)
                .maxDiscount(MAX_DISCOUNT_VALUE)
                .build();
    }

    public static LoyaltyProgramDTO getTestLoyaltyProgramDTO() {
        return LoyaltyProgramDTO.builder()
                .step(STEP_VALUE)
                .discount(LOYALTY_PROGRAM_DISCOUNT_VALUE)
                .maxDiscount(MAX_DISCOUNT_VALUE)
                .build();
    }
}