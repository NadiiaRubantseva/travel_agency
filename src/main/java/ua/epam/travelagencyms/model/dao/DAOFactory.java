package ua.epam.travelagencyms.model.dao;

import ua.epam.travelagencyms.model.dao.constants.DbImplementations;
import ua.epam.travelagencyms.model.dao.mysql.MysqlDAOFactory;

import javax.sql.DataSource;

public abstract class DAOFactory {
    private static DAOFactory instance;

    protected DAOFactory() {}

    public static synchronized DAOFactory getInstance(String dbImplementation, DataSource dataSource) {
        if (instance == null && DbImplementations.MYSQL.equals(dbImplementation)) {
            instance = new MysqlDAOFactory(dataSource);
        }
        return instance;
    }
    public abstract UserDAO getUserDAO();
    public abstract TourDAO getTourDAO();
    public abstract OrderDAO getOrderDAO();

}