package ua.epam.travelagencyms.controller.actions.impl.admin;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.getActionToRedirect;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.SEARCH_ORDER_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

/**
 * This is UpdateOrderDiscountAction class. Accessible by admin. Allows to update the discount for the order.
 * Implements PRG pattern
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class UpdateOrderDiscountAction implements Action {
    private final OrderService orderService;

    /**
     * @param appContext contains OrderService instance to use in action
     */
    public UpdateOrderDiscountAction(AppContext appContext) {
        orderService = appContext.getOrderService();
    }

    /**
     * Obtains required path and updates order discount.
     * Sets error if discount value is invalid.
     *
     * @param request to get order fields
     * @return path to redirect to executeGet method in Search order action through front-controller with required
     * parameters to find report.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String orderDiscount = request.getParameter(DISCOUNT);
        String orderId = request.getParameter(ID);
        String tourPrice = request.getParameter(TOUR_PRICE);
        orderService.setOrderDiscount(orderId, orderDiscount, tourPrice);
        request.setAttribute(ORDER, orderService.getById(orderId));
        return getActionToRedirect(SEARCH_ORDER_ACTION, ORDER_ID, request.getParameter(ID));
    }
}
