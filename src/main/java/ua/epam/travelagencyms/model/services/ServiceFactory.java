package ua.epam.travelagencyms.model.services;

import lombok.Getter;
import ua.epam.travelagencyms.model.dao.DAOFactory;
import ua.epam.travelagencyms.model.services.implementation.OrderServiceImpl;
import ua.epam.travelagencyms.model.services.implementation.TourServiceImpl;
import ua.epam.travelagencyms.model.services.implementation.UserServiceImpl;

/**
 * Service factory that provides concrete implementations of EventService, ReportService and UserService classes
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
@Getter
public final class ServiceFactory {

    private final UserService userService;
    private final TourService tourService;
    private final OrderService orderService;

    private ServiceFactory(DAOFactory daoFactory) {
        userService = new UserServiceImpl(daoFactory.getUserDAO());
        tourService = new TourServiceImpl(daoFactory.getTourDAO());
        orderService = new OrderServiceImpl(daoFactory.getOrderDAO());
    }

    /**
     * Obtains instance of ServiceFactory to get concrete Services
     * @param daoFactory - pass concrete DAOFactory instance to get access to DAO
     * @return ServiceFactory instance
     */
    public static ServiceFactory getInstance(DAOFactory daoFactory) {
        return new ServiceFactory(daoFactory);
    }

}