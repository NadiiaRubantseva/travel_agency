package ua.epam.travelagencyms.utils;

import ua.epam.travelagencyms.dto.TourDTO;
import ua.epam.travelagencyms.dto.UserDTO;
import ua.epam.travelagencyms.model.entities.tour.Hotel;
import ua.epam.travelagencyms.model.entities.tour.Tour;
import ua.epam.travelagencyms.model.entities.tour.Type;
import ua.epam.travelagencyms.model.entities.user.Role;
import ua.epam.travelagencyms.model.entities.user.User;

import static ua.epam.travelagencyms.controller.actions.constants.Parameters.HOT;

public final class ConvertorUtil {

    private ConvertorUtil() {}

    public static User convertDTOToUser(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .name(userDTO.getName())
                .surname(userDTO.getSurname())
                .build();
    }

    public static UserDTO convertUserToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .surname(user.getSurname())
                .role(String.valueOf(Role.getRole(user.getRoleId())))
                .status(user.isBlocked() ? "BLOCKED" : "ACTIVE")
                .avatar(ImageEncoder.encode(user.getAvatar()))
                .build();
    }

    public static TourDTO convertTourToDTO(Tour tour) {
        return TourDTO.builder()
                .id(tour.getId())
                .title(tour.getTitle())
                .persons(tour.getPersons())
                .price(tour.getPrice())
                .hot(tour.getHot() == 1 ? HOT : null)
                .type(String.valueOf(Type.getType(tour.getTypeId())))
                .hotel(String.valueOf(Hotel.getHotel(tour.getHotelId())))
                .image(tour.getImageContent())
                .decodedImage(ImageEncoder.encode(tour.getImageContent()))
                .build();
    }

    public static Tour convertDTOToTour(TourDTO tourDTO) {
        return Tour.builder()
                .id(tourDTO.getId())
                .title(tourDTO.getTitle())
                .persons(tourDTO.getPersons())
                .price(tourDTO.getPrice())
                .hot((byte)(tourDTO.getHot() == null ? 0 : 1))
                .typeId(Type.valueOf(tourDTO.getType()).getValue())
                .hotelId(Hotel.valueOf(tourDTO.getHotel()).getValue())
                .imageContent(tourDTO.getImage())
                .build();
    }
}