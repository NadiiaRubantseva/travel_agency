package ua.epam.travelagencyms.controller.actions.impl.admin;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.IncorrectFormatException;
import ua.epam.travelagencyms.exceptions.NoSuchOrderException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.transferStringFromSessionToRequest;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.SEARCH_ORDER_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.VIEW_ORDER_BY_ADMIN_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

/**
 * This is SearchOrderAction class. Accessible by admin and manager.
 * Allows to find an order from database by id.
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class SearchOrderAction implements Action {
    private final OrderService orderService;

    /**
     * @param appContext contains OrderService instance to use in action
     */
    public SearchOrderAction(AppContext appContext) {
        orderService = appContext.getOrderService();
    }

    /**
     * Obtains required path and sets user to request if it finds
     *
     * @param request to get order id and put order in request or error if it can't find user
     * @return viewOrderById page if it finds or SearchOrder page if not
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        // transfer attributes from session to request if any
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);


        // page if order was found
        String path = VIEW_ORDER_BY_ADMIN_PAGE;

        try {

            // setting order attribute to the request
            request.setAttribute(ORDER, orderService.getById(request.getParameter(ORDER_ID)));

        } catch (IncorrectFormatException | NoSuchOrderException e) {

            // setting error message
            request.setAttribute(ERROR, e.getMessage());

            // setting search order page to keep staying on the same one
            path = SEARCH_ORDER_PAGE;
        }

        // returning page information to the front controller
        return path;
    }
}