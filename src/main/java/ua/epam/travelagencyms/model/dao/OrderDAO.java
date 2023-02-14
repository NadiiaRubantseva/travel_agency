package ua.epam.travelagencyms.model.dao;

import ua.epam.travelagencyms.exceptions.DAOException;
import ua.epam.travelagencyms.model.entities.order.Order;

import java.util.List;

/**
 * Order DAO interface.
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public interface OrderDAO extends EntityDAO<Order> {

    /**
     * Sets order's discount
     * @param orderId - value of order id
     * @throws DAOException is wrapper for SQLException
     */
    void setOrderDiscount(long orderId, int discount, double price) throws DAOException;

    /**
     * Obtains list of all orders of the user from database
     * @param userId - value of user id
     * @return orders list
     * @throws DAOException is wrapper for SQLException
     */
    List<Order> getUserOrders(long userId) throws DAOException;

    /**
     * Obtains list of all orders made for one specific tour from database
     * @param tourId - value of user id
     * @return orders list
     * @throws DAOException is wrapper for SQLException
     */
    List<Order> getToursOrders(long tourId) throws DAOException;

    /**
     * Obtains sorted and limited list of orders from database
     * @param query should contain filters, order, limits for pagination
     * @return order list that matches demands
     * @throws DAOException is wrapper for SQLException
     */
    List<Order> getSorted(String query) throws DAOException;

    /**
     * Obtains number of all records matching filter
     * @param filter should contain 'where' to specify query
     * @return number of records
     * @throws DAOException is wrapper for SQLException
     */
    int getNumberOfRecords(String filter) throws DAOException;

    /**
     * Sets order's status
     * @param orderId - value of order id
     * @throws DAOException is wrapper for SQLException
     */
    void setOrderStatus(long orderId, int status) throws DAOException;
}
