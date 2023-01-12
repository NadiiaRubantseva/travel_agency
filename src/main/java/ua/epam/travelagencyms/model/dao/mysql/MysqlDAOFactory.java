package ua.epam.travelagencyms.model.dao.mysql;

import ua.epam.travelagencyms.model.dao.DAOFactory;
import ua.epam.travelagencyms.model.dao.OrderDAO;
import ua.epam.travelagencyms.model.dao.TourDAO;
import ua.epam.travelagencyms.model.dao.UserDAO;

import javax.sql.DataSource;

public class MysqlDAOFactory extends DAOFactory {
    private UserDAO userDAO;
    private TourDAO tourDAO;
    private OrderDAO orderDAO;

    private final DataSource dataSource;
    public MysqlDAOFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new MysqlUserDAO(dataSource);
        }
        return userDAO;
    }

    public TourDAO getTourDAO() {
        if (tourDAO == null) {
            tourDAO = new MysqlTourDAO(dataSource);
        }
        return tourDAO;
    }

    public OrderDAO getOrderDAO() {
        if (orderDAO == null) {
            orderDAO = new MySqlOrderDAO(dataSource);
        }
        return orderDAO;
    }
}