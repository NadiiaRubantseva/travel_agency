package ua.epam.travelagencyms;

import ua.epam.travelagencyms.dto.TourDTO;
import ua.epam.travelagencyms.dto.UserDTO;
import ua.epam.travelagencyms.model.entities.user.Role;

import java.util.ArrayList;
import java.util.List;

import static ua.epam.travelagencyms.ConstantsForTest.*;

public class MethodsForTest {

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

    public static UserDTO getUserDTO() {
        return UserDTO.builder()
                .id(ID_VALUE)
                .email(EMAIL_VALUE)
                .name(NAME_VALUE)
                .surname(SURNAME_VALUE)
                .role(Role.ADMIN.name())
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

    public static TourDTO getTourDTO() {
        return TourDTO.builder()
                .id(ID_VALUE)
                .title(TITLE_VALUE)
                .persons(Integer.parseInt(PERSONS_VALUE))
                .price(Double.parseDouble(PRICE_VALUE))
                .hot(HOT_VALUE)
                .type(TYPE_VALUE)
                .hotel(HOTEL_VALUE)
                .image(IMAGE_VALUE)
                .decodedImage(DECODED_IMAGE_VALUE)
                .build();
    }

    public static TourDTO getTestTourDTO() {
        return TourDTO.builder()
                .id(ID_VALUE)
                .title(TITLE_VALUE)
                .persons(Integer.parseInt(PERSONS_VALUE))
                .price(Double.parseDouble(PRICE_VALUE))
                .hot(HOT_VALUE)
                .type(TYPE_VALUE)
                .hotel(HOTEL_VALUE)
                .build();
    }
}
