package ua.epam.travelagencyms.model.services;

import ua.epam.travelagencyms.dto.OrderDTO;
import ua.epam.travelagencyms.exceptions.ServiceException;

import java.util.List;

/**
 * OrderService interface.
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public interface OrderService extends Service<OrderDTO> {

    /**
     * Calls DAO to add relevant entity
     * @param orderDTO - DTO to be added as entity to database
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    void addOrder(OrderDTO orderDTO) throws ServiceException;

    /**
     * Calls DAO to set order status
     * @param status - order status value
     * @param orderId - order id
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    void setStatus(String status, String orderId) throws ServiceException;

    /**
     * Calls DAO to set order discount
     * @param discount - order discount value
     * @param tourPrice - tour price
     * @param orderId - order id
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    void setDiscount(String discount, String tourPrice, String orderId) throws ServiceException;

    /**
     * Calls DAO to get orders made for a specific tour
     * @param tourId - tour id value.
     * @return List of OrderDTOs for this specific tour
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    List<OrderDTO> viewToursOrders(long tourId) throws ServiceException;

    /**
     * Calls DAO to get orders made by a specific user
     * @param userId - user id value.
     * @return List of OrderDTOs of this specific user
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    List<OrderDTO> viewUsersOrders(long userId) throws ServiceException;

    /**
     * Calls DAO to get sorted, filtered and limited list of DTOs. Converts Orders to OrderDTOs
     * @param query - to obtain necessary DTOs
     * @return List of OrderDTOs that match demands
     * @throws ServiceException - may wrap DAOException
     */
    List<OrderDTO> getSortedOrders(String query) throws ServiceException;

    /**
     * Calls DAO to get number of all records that match filter
     * @param filter - conditions for such Orders
     * @return number of records that match demands
     * @throws ServiceException - may wrap DAOException
     */
    int getNumberOfRecords(String filter) throws ServiceException;

}
