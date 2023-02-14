package ua.epam.travelagencyms.utils;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.dto.*;
import ua.epam.travelagencyms.model.entities.tour.Tour;
import ua.epam.travelagencyms.model.entities.user.User;

import static org.junit.jupiter.api.Assertions.*;
import static ua.epam.travelagencyms.TestUtils.*;
import static ua.epam.travelagencyms.TestUtils.EMAIL_VALUE;
import static ua.epam.travelagencyms.TestUtils.NAME_VALUE;
import static ua.epam.travelagencyms.TestUtils.ROLE_ID_VALUE;
import static ua.epam.travelagencyms.TestUtils.SURNAME_VALUE;
import static ua.epam.travelagencyms.utils.ConvertorUtil.*;

class ConvertorUtilTest {

    @Test
    void testConvertDTOToUser() {
        User dtoToUser = convertDTOToUser(getTestUserDTO());
        assertEquals(ID_VALUE, dtoToUser.getId());
        assertEquals(EMAIL_VALUE, dtoToUser.getEmail());
        assertNull(dtoToUser.getPassword());
        assertEquals(NAME_VALUE, dtoToUser.getName());
        assertEquals(SURNAME_VALUE, dtoToUser.getSurname());
        assertNull(dtoToUser.getAvatar());
        assertEquals(DISCOUNT_VALUE, dtoToUser.getDiscount());
        assertEquals(IS_BLOCKED_VALUE, dtoToUser.isBlocked());
        assertEquals(IS_EMAIL_VERIFIED_VALUE, dtoToUser.isEmailVerified());
        assertNull(dtoToUser.getVerificationCode());
        assertEquals(ROLE_ID_VALUE, dtoToUser.getRoleId());
    }

    @Test
    void testConvertUserToDTO() {
        UserDTO testDTO = getTestUserDTO();
        UserDTO userToDTO = convertUserToDTO(getTestUser());
        assertEquals(testDTO, userToDTO);
        assertEquals(testDTO.getId(), userToDTO.getId());
        assertEquals(testDTO.getRole(), userToDTO.getRole());
    }

    @Test
    void testConvertDTOToTour() {
        Tour dtoToTour = convertTourDTOToTour(getTestTourDTO());
        assertEquals(ID_VALUE, dtoToTour.getId());
        assertEquals(TITLE_VALUE, dtoToTour.getTitle());
        assertEquals(PERSONS_VALUE, dtoToTour.getPersons());
        assertEquals(PRICE_VALUE, dtoToTour.getPrice());
        assertEquals(HOT_VALUE, dtoToTour.getHot());
        assertNull(dtoToTour.getImage());
        assertEquals(TYPE_ID_VALUE, dtoToTour.getTypeId());
        assertEquals(HOTEL_ID_VALUE, dtoToTour.getHotelId());
        assertEquals(DESCRIPTION_VALUE, dtoToTour.getDescription());
    }

    @Test
    void testConvertTourToDTO() {
        TourDTO testDTO = getTestTourDTO();
        TourDTO tourToDTO = convertTourToDTO(getTestTour());
        assertEquals(testDTO, tourToDTO);
    }
//
//    public static OrderDTO getTestOrderDTO() {
//        return OrderDTO.builder()
//                .id(1)
//                .orderStatus(OrderStatus.REGISTERED.toString())
//                .userId(1)
//                .tourId(1)
//                .discount(0)
//                .totalCost(100)
//                .date("27.01.2023")
//                .build();
//    }
}