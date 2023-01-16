package ua.epam.travelagencyms.controller.actions.impl.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.OrderDTO;
import ua.epam.travelagencyms.dto.TourDTO;
import ua.epam.travelagencyms.dto.UserDTO;
import ua.epam.travelagencyms.exceptions.IncorrectFormatException;
import ua.epam.travelagencyms.exceptions.NoSuchTourException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.OrderService;
import ua.epam.travelagencyms.model.services.TourService;
import ua.epam.travelagencyms.model.services.UserService;
import ua.epam.travelagencyms.utils.EmailSender;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.epam.travelagencyms.controller.actions.constants.Pages.*;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_DELETE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

public class BookTourAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(BookTourAction.class.getName().getClass());

    private final TourService tourService;
    private final UserService userService;
    private final OrderService orderService;

    public BookTourAction(AppContext appContext) {
        tourService = appContext.getTourService();
        userService = appContext.getUserService();
        orderService = appContext.getOrderService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String path = TOUR_BY_TITLE_PAGE;
        try {
            request.setAttribute(TOUR, tourService.getById(request.getParameter(ID)));
        } catch (NoSuchTourException | IncorrectFormatException e) {
            request.setAttribute(ERROR, e.getMessage());
            path = SEARCH_TOUR_PAGE;
        }
        UserDTO user = (UserDTO) request.getSession().getAttribute(LOGGED_USER);
        long userId = userService.getByEmail(user.getEmail()).getId();
        TourDTO tour = (TourDTO) request.getAttribute(TOUR);
        OrderDTO order = OrderDTO.builder()
                .userId(userId)
                .tourId(tour.getId())
                .tourPrice(tour.getPrice())
                .build();
        orderService.addOrder(order);
        request.getSession().setAttribute(MESSAGE, SUCCEED_DELETE);
        return BOOK_TOUR_CONFIRMATION;
    }
}