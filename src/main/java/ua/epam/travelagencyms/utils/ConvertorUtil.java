package ua.epam.travelagencyms.utils;

import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.OrderDTO;
import ua.epam.travelagencyms.dto.TourDTO;
import ua.epam.travelagencyms.dto.UserDTO;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.entities.order.Order;
import ua.epam.travelagencyms.model.entities.order.OrderStatus;
import ua.epam.travelagencyms.model.entities.tour.Hotel;
import ua.epam.travelagencyms.model.entities.tour.Tour;
import ua.epam.travelagencyms.model.entities.tour.Type;
import ua.epam.travelagencyms.model.entities.user.Role;
import ua.epam.travelagencyms.model.entities.user.User;
import ua.epam.travelagencyms.model.services.TourService;

import javax.servlet.http.HttpServletRequest;


import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.HOTEL;

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
                .avatar(ImageEncoder.encode(user.getAvatar()))
                .discount(String.valueOf(user.getDiscount()))
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
                .hot(tour.getHot() == 1 ? HOT : null)
                .type(String.valueOf(Type.getType(tour.getTypeId())))
                .hotel(String.valueOf(Hotel.getHotel(tour.getHotelId())))
                .image(tour.getImageContent())
                .decodedImage(ImageEncoder.encode(tour.getImageContent()))
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
                .hot((byte)(tourDTO.getHot() == null ? 0 : 1))
                .typeId(Type.valueOf(tourDTO.getType()).getValue())
                .hotelId(Hotel.valueOf(tourDTO.getHotel()).getValue())
                .imageContent(tourDTO.getImage())
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
                .orderStatus(OrderStatus.valueOf(orderDTO.getOrderStatus()))
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
                .orderStatus(order.getOrderStatus().name())
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

    public static UserDTO getUserDTO(HttpServletRequest request) {
        return UserDTO.builder()
                .email(request.getParameter(EMAIL))
                .name(request.getParameter(NAME))
                .surname(request.getParameter(SURNAME))
                .build();
    }

    public static TourDTO getTourDTOFromAddRequest(HttpServletRequest request) {
        return TourDTO.builder()
                .title(request.getParameter(TITLE))
                .persons(Integer.parseInt(request.getParameter(PERSONS)))
                .price(Double.parseDouble(request.getParameter(PRICE)))
                .hot(request.getParameter(HOT) == null ? null : HOT)
                .type(request.getParameter(TYPE))
                .hotel(request.getParameter(HOTEL))
                .image(ImageEncoder.getImage(request))
                .decodedImage(ImageEncoder.encode(ImageEncoder.getImage(request)))
                .build();
    }

    public static TourDTO getFullTourDTOFromRequest(HttpServletRequest request) throws ServiceException {
        TourService tourService = AppContext.getAppContext().getTourService();
        String id = request.getParameter(ID);
        byte[]image = tourService.getImage(id);

        return TourDTO.builder()
                .id(Long.parseLong(request.getParameter(ID)))
                .title(request.getParameter(TITLE))
                .persons(Integer.parseInt(request.getParameter(PERSONS)))
                .price(Double.parseDouble(request.getParameter(PRICE)))
                .hot(request.getParameter(HOT) == null ? null : HOT)
                .type(request.getParameter(TYPE))
                .hotel(request.getParameter(HOTEL))
                .image(image)
                .decodedImage(ImageEncoder.encode(image))
                .build();
    }
}