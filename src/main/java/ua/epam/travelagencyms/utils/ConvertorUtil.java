package ua.epam.travelagencyms.utils;

import ua.epam.travelagencyms.dto.LoyaltyProgramDTO;
import ua.epam.travelagencyms.dto.OrderDTO;
import ua.epam.travelagencyms.dto.TourDTO;
import ua.epam.travelagencyms.dto.UserDTO;
import ua.epam.travelagencyms.model.entities.loyaltyProgram.LoyaltyProgram;
import ua.epam.travelagencyms.model.entities.order.Order;
import ua.epam.travelagencyms.model.entities.order.OrderStatus;
import ua.epam.travelagencyms.model.entities.tour.Hotel;
import ua.epam.travelagencyms.model.entities.tour.Tour;
import ua.epam.travelagencyms.model.entities.tour.Type;
import ua.epam.travelagencyms.model.entities.user.Role;
import ua.epam.travelagencyms.model.entities.user.User;

import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.FALSE;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.TRUE;

/**
 * Converts DTO to Entities and vise versa
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public final class ConvertorUtil {

    private ConvertorUtil() {}

    /**
     * Converts UserDTO into User
     * @param userDTO to convert
     * @return User entity
     */
    public static User convertDTOToUser(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .name(userDTO.getName())
                .surname(userDTO.getSurname())
                .discount(userDTO.getDiscount())
                .isBlocked(userDTO.getIsBlocked().equalsIgnoreCase("Blocked"))
                .isEmailVerified(userDTO.getIsEmailVerified().equalsIgnoreCase("Yes"))
                .roleId(Role.valueOf(userDTO.getRole()).getValue())
                .avatar(userDTO.getAvatar())
                .build();
    }

    /**
     * Converts User into UserDTO
     * @param user to convert
     * @return UserDTO
     */
    public static UserDTO convertUserToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .surname(user.getSurname())
                .role(String.valueOf(Role.getRole(user.getRoleId())))
                .isBlocked(user.isBlocked() ? "Blocked" : "Active")
                .isEmailVerified(user.isEmailVerified() ? "Yes" : "No")
                .avatar(user.getAvatar())
                .discount(user.getDiscount())
                .build();
    }

    /**
     * Converts Tour into TourDTO
     * @param tour to convert
     * @return TourDTO
     */
    public static TourDTO convertTourToDTO(Tour tour) {
        return TourDTO.builder()
                .id(tour.getId())
                .title(tour.getTitle())
                .persons(tour.getPersons())
                .price(tour.getPrice())
                .hot(tour.getHot() == 1 ? TRUE : FALSE)
                .type(String.valueOf(Type.getType(tour.getTypeId())))
                .hotel(String.valueOf(Hotel.getHotel(tour.getHotelId())))
                .image(tour.getImage())
                .description(tour.getDescription())
                .build();
    }

    /**
     * Converts TourDTO into Tour
     * @param tourDTO to convert
     * @return Tour entity
     */
    public static Tour convertTourDTOToTour(TourDTO tourDTO) {
        return Tour.builder()
                .id(tourDTO.getId())
                .title(tourDTO.getTitle())
                .persons(tourDTO.getPersons())
                .price(tourDTO.getPrice())
                .hot((byte)(tourDTO.getHot().equals(TRUE) ? 1 : 0))
                .typeId(Type.valueOf(tourDTO.getType()).getValue())
                .hotelId(Hotel.valueOf(tourDTO.getHotel()).getValue())
                .description(tourDTO.getDescription())
                .image(tourDTO.getImage())
                .build();
    }

    /**
     * Converts OrderDTO into Order
     * @param orderDTO to convert
     * @return Order entity
     */
    public static Order convertDTOToOrder(OrderDTO orderDTO) {
        return Order.builder()
                .id(orderDTO.getId())
                .orderStatusId(OrderStatus.valueOf(orderDTO.getOrderStatus()).getValue())
                .user(User.builder().id(orderDTO.getUserId()).build())
                .tour(Tour.builder().id(orderDTO.getTourId()).price(orderDTO.getTourPrice()).title(orderDTO.getTourTitle()).build())
                .discount(orderDTO.getDiscount())
                .totalCost(orderDTO.getTotalCost())
                .build();
    }

    /**
     * Converts Order into OrderDTO
     * @param order to convert
     * @return OrderDTO
     */
    public static OrderDTO convertOrderToDTO(Order order) {
        User user = order.getUser();
        Tour tour = order.getTour();
        return OrderDTO.builder()
                .id(order.getId())
                .orderStatus(OrderStatus.getOrderStatus(order.getOrderStatusId()).toString())
                .userId(user.getId())
                .userEmail(user.getEmail())
                .userName(user.getName())
                .userSurname(user.getSurname())
                .tourId(tour.getId())
                .tourTitle(tour.getTitle())
                .tourPrice(tour.getPrice())
                .discount(order.getDiscount())
                .totalCost(order.getTotalCost())
                .date(String.valueOf(order.getDate()))
                .build();
    }

    public static LoyaltyProgram convertDTOToLoyaltyProgram(LoyaltyProgramDTO loyaltyProgramDTO) {
        return LoyaltyProgram.builder()
                .step(loyaltyProgramDTO.getStep())
                .discount(loyaltyProgramDTO.getDiscount())
                .maxDiscount(loyaltyProgramDTO.getMaxDiscount())
                .build();
    }

    public static LoyaltyProgramDTO convertLoyaltyProgramToDTO(LoyaltyProgram loyaltyProgram) {
        return LoyaltyProgramDTO.builder()
                .step(loyaltyProgram.getStep())
                .discount(loyaltyProgram.getDiscount())
                .maxDiscount(loyaltyProgram.getMaxDiscount())
                .build();
    }

}