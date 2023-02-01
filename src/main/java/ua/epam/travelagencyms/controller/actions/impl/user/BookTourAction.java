package ua.epam.travelagencyms.controller.actions.impl.user;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.OrderDTO;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.OrderService;
import ua.epam.travelagencyms.utils.ConvertorUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.epam.travelagencyms.controller.actions.constants.Pages.*;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_DELETE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

/**
 * This is BookTourAction class. Accessible by user. Allows to book tour.
 * Implements PRG pattern
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class BookTourAction implements Action {
    private final OrderService orderService;

    /**
     * @param appContext contains OrderService instance to use in action
     */
    public BookTourAction(AppContext appContext) {
        orderService = appContext.getOrderService();
    }

    /**
     * Tries to book a tour from database.
     * Logs error in case if not able
     *
     * @param request to get tour's id, user's id, tour's price and set error message in case of unsuccessful ordering
     * @return path to redirect through front-controller
     * @throws ServiceException to call error page in front-controller
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String path = VIEW_TOURS_BY_USER_PAGE;
        try {
            OrderDTO order = ConvertorUtil.convertToOrderDTO(request);
            orderService.addOrder(order);
        } catch (Exception e) {
            request.setAttribute(ERROR, e.getMessage());
            path = SEARCH_TOUR_PAGE;
        }
        request.getSession().setAttribute(CURRENT_PATH, path);
        request.getSession().setAttribute(MESSAGE, SUCCEED_DELETE);
        return BOOK_TOUR_CONFIRMATION;
    }
}