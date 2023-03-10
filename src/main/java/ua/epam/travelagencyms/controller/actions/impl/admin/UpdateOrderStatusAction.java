package ua.epam.travelagencyms.controller.actions.impl.admin;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.LoyaltyProgramDTO;
import ua.epam.travelagencyms.exceptions.IncorrectFormatException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.OrderService;
import ua.epam.travelagencyms.model.services.UserService;
import ua.epam.travelagencyms.model.services.implementation.LoyaltyProgramService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.getActionToRedirect;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.SEARCH_ORDER_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.*;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

/**
 * This is UpdateOrderStatusAction class. Accessible by admin. Allows to update the status of the order.
 * Implements PRG pattern
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class UpdateOrderStatusAction implements Action {
    private final OrderService orderService;
    private final LoyaltyProgramService loyaltyProgramService;

    private final UserService userService;

    /**
     * @param appContext contains OrderService instance to use in action
     */
    public UpdateOrderStatusAction(AppContext appContext) {
        orderService = appContext.getOrderService();
        loyaltyProgramService = appContext.getLoyaltyProgramService();
        userService = appContext.getUserService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        // getting new order status from request
        String orderStatus = request.getParameter(STATUS);

        // getting order id from request
        String orderId = request.getParameter(ID);

        // getting user id from request
        String userId = request.getParameter(USER_ID);

        try {
            // updating order status in db
            orderService.setOrderStatus(orderId, orderStatus);

            // updating personal user discount accordingly
            if (!orderStatus.equals(REGISTERED)) {
               updateUserDiscount(userId);
            }

            // setting success message
            request.getSession().setAttribute(MESSAGE, SUCCEED_UPDATE);

        } catch (IncorrectFormatException e) {
            // setting error message
            request.getSession().setAttribute(ERROR, e.getMessage());
        }

        request.getSession().setAttribute(ORDER, orderService.getById(orderId));
        return getActionToRedirect(SEARCH_ORDER_ACTION, ORDER_ID, orderId);
    }

    private void updateUserDiscount(String userId) throws ServiceException {
        // getting loyalty program information
        LoyaltyProgramDTO loyaltyProgram = loyaltyProgramService.get();

        // counting how many paid orders user have
        long count = orderService.viewUsersOrders(Long.parseLong(userId))
                .stream()
                .filter(order -> order.getOrderStatus().equals(PAID))
                .count();

        // counting user discount according to loyalty program
        int userDiscount = (int) ((count / loyaltyProgram.getStep()) * loyaltyProgram.getDiscount());

        // If the user's discount exceeds the maximum discount, the maximum discount will be set
        userDiscount = Math.min(userDiscount, loyaltyProgram.getMaxDiscount());

        // updating user discount
        userService.setDiscount(userDiscount, userId);

    }
}
