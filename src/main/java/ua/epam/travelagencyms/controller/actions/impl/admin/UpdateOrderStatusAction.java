package ua.epam.travelagencyms.controller.actions.impl.admin;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.OrderDTO;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.entities.LoyaltyProgram;
import ua.epam.travelagencyms.model.services.OrderService;
import ua.epam.travelagencyms.model.services.UserService;
import ua.epam.travelagencyms.model.services.implementation.LoyaltyProgramService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.*;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.UPDATE_ORDER_STATUS_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.VIEW_ORDER_BY_ADMIN_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.PAID;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_UPDATE;
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
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
            transferStringFromSessionToRequest(request, MESSAGE);
            transferStringFromSessionToRequest(request, ERROR);
            transferOrderDTOFromSessionToRequest(request);
            return VIEW_ORDER_BY_ADMIN_PAGE;
    }


    /**
     * Obtains required path and updates status of the order.
     * Sets error if status value is invalid.
     *
     * @param request to get order fields
     * @return path to redirect to executeGet method in Search order action through front-controller with required
     * parameters to find report.
     */
    public String executePost(HttpServletRequest request) throws ServiceException {
        String orderStatus = request.getParameter(STATUS);
        String orderId = request.getParameter(ID);
        String userId = request.getParameter(USER_ID);
        orderService.setOrderStatus(orderId, orderStatus);
        request.getSession().setAttribute(MESSAGE, SUCCEED_UPDATE);

        if (orderStatus.equals(PAID)) {
            LoyaltyProgram loyaltyProgram = loyaltyProgramService.get();
            List<OrderDTO> orders = orderService.viewUsersOrders(Long.parseLong(userId));
            long count = orders.stream().filter(order -> order.getOrderStatus().equals(PAID)).count();
            int userDiscount = (int) ((count / loyaltyProgram.getStep()) * loyaltyProgram.getDiscount());
            userDiscount = Math.min(userDiscount, loyaltyProgram.getMaxDiscount());
            userService.setDiscount(userDiscount, Long.parseLong(userId));
        }

        request.getSession().setAttribute(ORDER, orderService.getById(orderId));
        return getActionToRedirect(UPDATE_ORDER_STATUS_ACTION);
    }
}
