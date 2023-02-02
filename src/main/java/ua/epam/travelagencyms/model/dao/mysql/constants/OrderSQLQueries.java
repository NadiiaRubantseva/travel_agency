package ua.epam.travelagencyms.model.dao.mysql.constants;

/**
 * Class that contains all My SQL queries for OrderDAO
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class OrderSQLQueries {

    public static final String ADD_ORDER = "INSERT INTO `order` (`user_id`, `tour_id`, `total_cost`, `date`) VALUES (?, ?, ?, ?)";
    public static final String GET_ORDERS = "SELECT order.id, order.order_status_id, order.user_id, user.email, " +
            "user.name, user.surname, order.tour_id, tour.title, tour.price, order.discount, order.total_cost, order.date FROM `order` " +
            "LEFT JOIN user ON order.user_id=user.id LEFT JOIN tour ON order.tour_id=tour.id";

    public static final String GET_ORDER_BY_ID = GET_ORDERS + " WHERE order.id = ?";
    public static final String UPDATE_ORDER = "UPDATE `order` SET order_status_id=?, discount=?, total_cost=? WHERE id=?";
    public static final String CANCEL_ORDER = "UPDATE `order` SET order_status_id=3 WHERE id=?";
    public static final String GET_TOURS_ORDERS = GET_ORDERS + " WHERE tour_id=?";
    public static final String GET_USERS_ORDERS = GET_ORDERS + " WHERE user_id=?";
    public static final String GET_NUMBER_OF_RECORDS = "SELECT COUNT(id) AS numberOfRecords FROM `order` %s";
    public static final String GET_SORTED = GET_ORDERS + "%s";
    public static final String SET_STATUS = "UPDATE `order` SET order_status_id=? WHERE id=?";
    public static final String SET_DISCOUNT = "UPDATE `order` SET discount=?, total_cost=? WHERE id=?";

}
