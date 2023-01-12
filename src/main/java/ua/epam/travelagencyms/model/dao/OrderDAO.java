package ua.epam.travelagencyms.model.dao;

import ua.epam.travelagencyms.exceptions.DAOException;
import ua.epam.travelagencyms.model.entities.order.Order;
import ua.epam.travelagencyms.model.entities.order.OrderStatus;
import ua.epam.travelagencyms.model.entities.tour.Tour;

import java.util.List;

public interface OrderDAO extends EntityDAO<Order> {

    void setOrderDiscount(long orderId, int discount, double price) throws DAOException;

    List<Order> getUserOrders(long userId) throws DAOException;

    List<Order> getToursOrders(long tourId) throws DAOException;

    List<Order> getSorted(String query) throws DAOException;

    int getNumberOfRecords(String filter) throws DAOException;

    void setOrderStatus(long orderId, OrderStatus status) throws DAOException;
}
