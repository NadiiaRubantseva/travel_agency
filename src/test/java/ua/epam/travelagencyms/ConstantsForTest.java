package ua.epam.travelagencyms;

import ua.epam.travelagencyms.dto.TourDTO;
import ua.epam.travelagencyms.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConstantsForTest {

    public static String clearString(String string) {
        String[] lines = string.split("\n");
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            if (!line.contains("CreationDate") && !line.contains("ID") && !line.contains("BaseFont")) {
                sb.append(line).append("\n");
            }
        }
        return sb.toString();
    }

    // thenReturn()
    public static final String ONE = "1";

    // Get entity dto
    public static UserDTO getTestUserDTO() {
        return UserDTO.builder()
                .id(ID_VALUE)
                .email(EMAIL_VALUE)
                .name(NAME_VALUE)
                .surname(SURNAME_VALUE)
                .role(ROLE_VALUE)
                .build();
    }

    public static List<UserDTO> getUserDTOs(){
        List<UserDTO> users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            users.add(UserDTO.builder()
                    .id(i).email(EMAIL_VALUE).name(NAME_VALUE).surname(SURNAME_VALUE).role(NAME_VALUE)
                    .build());
        }
        return users;
    }

    public static TourDTO getTestTourDTO() {
        return TourDTO.builder()
                .id(ID_VALUE)
                .title(TITLE_VALUE)
                .persons(PERSONS_VALUE)
                .price(PRICE_VALUE)
                .hot(HOT_VALUE)
                .type(TYPE_TOUR_VALUE)
                .hotel(HOTEL_TOUR_VALUE)
                .build();
    }

    // Request.getMethod()
    public static final String POST = "POST";
    public static final String GET = "GET";

    // ID
    public static final long ID_VALUE = 1L;

    // User values
    public static final String EMAIL_VALUE = "user@mail.com";
    public static final String NAME_VALUE = "Name";
    public static final String SURNAME_VALUE = "Surname";
    public static final String ROLE_VALUE = "USER";

    // Tour
    public static final String TITLE_VALUE = "Best Title";
    public static final int PERSONS_VALUE = 2;
    public static final int PRICE_VALUE = 1299;
    public static final String HOT_VALUE = "hot";
    public static final String TYPE_TOUR_VALUE = "SHOPPING";
    public static final String HOTEL_TOUR_VALUE = "HOSTEL";


    // pdf
    public static final String FONT = "css/fonts/arial.ttf";



    public static final String PASSWORD = "Password1";
    public static final int ROLE_ID = 2;
    public static final String ROLE_USER = "USER";
    public static final String ANOTHER_EMAIL = "a@a.com";
    public static final String DUPLICATE = "Duplicate entry";
    public static final int THREE = 3;
    public static final int ZERO = 0;
    public static final String ANOTHER_PASSWORD = "newPassword1234";
    public static final String SURNAME = "Yi";

    public static final String NAME = "Joe";

    public static final String INCORRECT_SURNAME = "";

    public static final String INCORRECT_NAME = "";
    public static final String INCORRECT_EMAIL = "em.com";
    public static final String INCORRECT_PASSWORD = "Pass1";
    public static final String NAME_FIELD = "name";
    public static final String EMAIL_FIELD = "email";
    public static final String DESC = "DESC";

    public static final String TITLE = "title";
    public static final int PERSONS = 2;
    public static final double PRICE = 100;
    public static final String HOT = "Hot";
    public static final int TYPE_ID = 3;
    public static final int HOTEL_ID = 2;
    public static final String TITLE_FIELD = "title";
    public static final String PERSONS_FIELD = "persons";

    public static final String ANOTHER_TITLE = "PARIS - LOVE CITY";
    public static final int ANOTHER_PERSONS = 3;

    public static final String TYPE_TOUR = "REST";
    public static final String HOTEL_TOUR = "HOSTEL";
    public static final int ROLE_ID_VALUE = 2;
    public static final String PASSWORD_VALUE = "Password1";



}