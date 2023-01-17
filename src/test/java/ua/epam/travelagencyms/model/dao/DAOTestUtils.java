package ua.epam.travelagencyms.model.dao;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.mockito.Mockito;
import ua.epam.travelagencyms.dto.OrderDTO;
import ua.epam.travelagencyms.model.entities.order.Order;
import ua.epam.travelagencyms.model.entities.order.OrderStatus;
import ua.epam.travelagencyms.model.entities.tour.Tour;
import ua.epam.travelagencyms.model.entities.user.User;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static ua.epam.travelagencyms.Constants.*;

public final class DAOTestUtils {

    public static User getTestUser() {
        return User.builder()
                .id(ID_VALUE)
                .email(EMAIL_VALUE)
                .name(NAME_VALUE)
                .surname(SURNAME_VALUE)
                .password(PASSWORD_VALUE)
                .roleId(ROLE_ID_VALUE)
                .build();
    }

    public static Tour getTestTour() {
        return Tour.builder()
                .id(ID_VALUE)
                .title(TITLE)
                .persons(PERSONS)
                .price(PRICE)
                .hot((byte) 0)
                .typeId(TYPE_ID)
                .hotelId(HOTEL_ID)
                .build();
    }

    public static Order getTestOrder() {
        return Order.builder()
                .id(ID_VALUE)
                .orderStatus(OrderStatus.getOrderStatus(1))
                .user(getTestUser())
                .tour(getTestTour())
                .totalCost(getTestTour().getPrice())
                .build();
    }

    public static OrderDTO getTestOrderDTO() {
        return OrderDTO.builder()
                .id(1)
                .orderStatus(OrderStatus.REGISTERED.toString())
                .userId(1)
                .tourId(1)
                .discount(0)
                .totalCost(100)
                .build();
    }
}