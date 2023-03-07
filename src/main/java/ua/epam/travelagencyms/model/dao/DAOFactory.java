package ua.epam.travelagencyms.model.dao;

import ua.epam.travelagencyms.model.dao.constants.DbImplementations;
import ua.epam.travelagencyms.model.dao.mysql.MysqlLoyaltyProgramDAO;
import ua.epam.travelagencyms.model.dao.mysql.MysqlDAOFactory;

import javax.sql.DataSource;

/**
 * Abstract factory that provides concrete factories to obtain DAOs
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public abstract class DAOFactory {

    /** A single instance of the factory (Singleton pattern) */
    private static DAOFactory instance;

    protected DAOFactory() {}

    /**
     * Obtains single instance of the class. Synchronized to avoid multithreading collisions
     * @param dbImplementation - name of concrete database type (as an example 'MySql')
     * @param dataSource - datasource to connect to database
     * @return concrete DAO factory
     */
    public static synchronized DAOFactory getInstance(String dbImplementation, DataSource dataSource) {
        if (instance == null && DbImplementations.MYSQL.equals(dbImplementation)) {
            instance = new MysqlDAOFactory(dataSource);
        }
        return instance;
    }

    /**
     * Obtains concrete instance of DAO class
     * @return UserDAO for required database type
     */
    public abstract UserDAO getUserDAO();

    /**
     * Obtains concrete instance of DAO class
     * @return TourDAO for required database type
     */
    public abstract TourDAO getTourDAO();


    /**
     * Obtains concrete instance of DAO class
     * @return OrderDAO for required database type
     */
    public abstract OrderDAO getOrderDAO();

    public abstract MysqlLoyaltyProgramDAO getLoyaltyProgramDAO();

}