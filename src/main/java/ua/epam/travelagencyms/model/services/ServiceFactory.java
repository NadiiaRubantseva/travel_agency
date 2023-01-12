package ua.epam.travelagencyms.model.services;

import ua.epam.travelagencyms.model.dao.DAOFactory;
import ua.epam.travelagencyms.model.services.implementation.OrderServiceImpl;
import ua.epam.travelagencyms.model.services.implementation.TourServiceImpl;
import ua.epam.travelagencyms.model.services.implementation.UserServiceImpl;

public final class ServiceFactory {

    private final UserService userService;
    private final TourService tourService;
    private final OrderService orderService;

    private ServiceFactory(DAOFactory daoFactory) {
        userService = new UserServiceImpl(daoFactory.getUserDAO());
        tourService = new TourServiceImpl(daoFactory.getTourDAO());
        orderService = new OrderServiceImpl(daoFactory.getOrderDAO());
    }

    public static ServiceFactory getInstance(DAOFactory daoFactory) {
        return new ServiceFactory(daoFactory);
    }

    public UserService getUserService() {
        return userService;
    }
    public TourService getTourService() {
        return tourService;
    }
    public OrderService getOrderService() {
        return orderService;
    }

}