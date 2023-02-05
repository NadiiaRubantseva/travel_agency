package ua.epam.travelagencyms.model.dao.mysql;

import ua.epam.travelagencyms.model.dao.DAOFactory;
import ua.epam.travelagencyms.model.dao.OrderDAO;
import ua.epam.travelagencyms.model.dao.TourDAO;
import ua.epam.travelagencyms.model.dao.UserDAO;

import javax.sql.DataSource;

/**
 * My SQL factory that provides My SQL DAOs
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class MysqlDAOFactory extends DAOFactory {

    /** A single instance of the userDAO (Singleton pattern) */
    private UserDAO userDAO;

    /** A single instance of the tourDAO (Singleton pattern) */
    private TourDAO tourDAO;

    /** A single instance of the orderDAO (Singleton pattern) */
    private OrderDAO orderDAO;

    private MySqlLoyaltyProgramDAO loyaltyProgramDAO;

    private final DataSource dataSource;
    public MysqlDAOFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Obtains single instance of the UserDAO.
     * @return MysqlEventDAO
     */
    public UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new MysqlUserDAO(dataSource);
        }
        return userDAO;
    }

    /**
     * Obtains single instance of the TourDAO.
     * @return MysqlEventDAO
     */
    public TourDAO getTourDAO() {
        if (tourDAO == null) {
            tourDAO = new MysqlTourDAO(dataSource);
        }
        return tourDAO;
    }

    /**
     * Obtains single instance of the OrderDAO.
     * @return MysqlEventDAO
     */
    public OrderDAO getOrderDAO() {
        if (orderDAO == null) {
            orderDAO = new MySqlOrderDAO(dataSource);
        }
        return orderDAO;
    }

    @Override
    public MySqlLoyaltyProgramDAO getLoyaltyProgramDAO() {
        if (loyaltyProgramDAO == null) {
            loyaltyProgramDAO = new MySqlLoyaltyProgramDAO(dataSource);
        }
        return loyaltyProgramDAO;
    }
}