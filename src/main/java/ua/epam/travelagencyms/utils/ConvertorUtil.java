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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.HOTEL;

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

    public static UserDTO getUserDTO(HttpServletRequest request) {
        return UserDTO.builder()
                .email(request.getParameter(EMAIL))
                .name(request.getParameter(NAME))
                .surname(request.getParameter(SURNAME))
                .build();
    }

    public static UserDTO getUserDTOFromEditUserAction(HttpServletRequest request, UserDTO currentUser) {
        return UserDTO.builder()
                .id(currentUser.getId())
                .email(request.getParameter(EMAIL))
                .name(request.getParameter(NAME))
                .surname(request.getParameter(SURNAME))
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

    public static OrderDTO convertToOrderDTO(HttpServletRequest request) {
        UserDTO user = (UserDTO) request.getSession().getAttribute(LOGGED_USER);
        String tourId = request.getParameter(ID);
        String tourPrice = request.getParameter(PRICE);
        return OrderDTO.builder()
                .userId(user.getId())
                .tourId(Long.parseLong(tourId))
                .tourPrice(Double.parseDouble(tourPrice))
                .date(LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .build();
    }

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

    public static OrderDTO convertOrderToDTO(Order order) {
        User user = order.getUser();
        Tour tour = order.getTour();
        return OrderDTO.builder()
                .id(order.getId())
                .orderStatus(order.getOrderStatus().name())
                .userId(user.getId())
                .userName(user.getEmail())
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
}