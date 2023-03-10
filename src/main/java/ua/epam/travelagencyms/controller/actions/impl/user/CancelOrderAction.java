package ua.epam.travelagencyms.controller.actions.impl.user;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.getActionToRedirect;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.VIEW_ORDERS_OF_USER_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_CANCEL;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

/**
 * This is CancelOrderAction class. Accessible by user. Allows to cancel booking.
 * Implements PRG pattern
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class CancelOrderAction implements Action {
    private final OrderService orderService;

    /**
     * @param appContext contains OrderService instance to use in action
     */
    public CancelOrderAction(AppContext appContext) {
        orderService = appContext.getOrderService();
    }

    /**
     * Called from doPost method in front-controller. Tries to cancel tour.
     * Logs error in case if not able
     *
     * @param request to get order id attribute and sets message attribute to session
     * @return path for controller.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        try {
            // changing order status to canceled
            orderService.delete(request.getParameter(ORDER_ID));

            // setting success cancel message
            request.getSession().setAttribute(MESSAGE, SUCCEED_CANCEL);

        } catch (ServiceException e) {

            // setting error message
            request.getSession().setAttribute(ERROR, e.getMessage());

        }

        // redirecting to all orders page
        return getActionToRedirect(VIEW_ORDERS_OF_USER_ACTION);
    }
}