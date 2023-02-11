package ua.epam.travelagencyms.utils;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.dto.*;
import ua.epam.travelagencyms.model.entities.tour.Tour;
import ua.epam.travelagencyms.model.entities.user.User;

import static org.junit.jupiter.api.Assertions.*;
import static ua.epam.travelagencyms.ConstantsForTest.*;
import static ua.epam.travelagencyms.utils.ConvertorUtil.*;

class ConvertorUtilTest {

    @Test
    void testConvertDTOToUser() {
        User testUser = getTestUser();
        User dtoToUser = convertDTOToUser(getTestUserDTO());
        assertEquals(testUser, dtoToUser);
        assertNull(dtoToUser.getPassword());
        assertNotEquals(testUser.getPassword(), dtoToUser.getPassword());
    }

    @Test
    void testConvertUserToDTO() {
        UserDTO testDTO = getTestUserDTO();
        UserDTO userToDTO = convertUserToDTO(getTestUser());
        assertEquals(testDTO, userToDTO);
        assertEquals(testDTO.getId(), userToDTO.getId());
        assertEquals(testDTO.getRole(), userToDTO.getRole());
    }


    private UserDTO getTestUserDTO() {
        return UserDTO.builder()
                .id(ID_VALUE)
                .email(EMAIL_VALUE)
                .name(NAME)
                .surname(SURNAME)
                .role(ROLE_USER)
                .build();
    }

    private User getTestUser() {
        return User.builder()
                .id(ID_VALUE)
                .email(EMAIL_VALUE)
                .password(PASSWORD)
                .name(NAME)
                .surname(SURNAME)
                .roleId(ROLE_ID)
                .build();
    }

    @Test
    void testConvertDTOToTour() {
        Tour testTour = getTestTour();
        Tour dtoToTour = convertTourDTOToTour(getTestTourDTO());
        assertEquals(testTour, dtoToTour);
    }

    @Test
    void testConvertTourToDTO() {
        TourDTO testDTO = getTestTourDTO();
        TourDTO tourToDTO = convertTourToDTO(getTestTour());
        assertEquals(testDTO, tourToDTO);
        assertEquals(testDTO.getId(), tourToDTO.getId());
        assertEquals(testDTO.getType(), tourToDTO.getType());
    }

    private Tour getTestTour() {
        return Tour.builder()
                .id(ID_VALUE)
                .title(TITLE_VALUE)
                .persons(PERSONS)
                .price(Double.parseDouble(PRICE_VALUE))
                .hot((byte) 0)
                .typeId(TYPE_ID)
                .hotelId(HOTEL_ID)
                .build();
    }

    private TourDTO getTestTourDTO() {
        return TourDTO.builder()
                .id(ID_VALUE)
                .title(TITLE_VALUE)
                .persons(PERSONS)
                .price(Double.parseDouble(PRICE_VALUE))
                .hot(HOT)
                .type(TYPE_TOUR)
                .hotel(HOTEL_TOUR)
                .build();
    }
}