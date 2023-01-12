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

public class UpdateOrderStatusAction implements Action {
    private final OrderService orderService;

    public UpdateOrderStatusAction(AppContext appContext) {
        orderService = appContext.getOrderService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String orderStatus = request.getParameter(STATUS);
        String orderId = request.getParameter(ID);
        orderService.setStatus(orderStatus, orderId);
        request.setAttribute(ORDER, orderService.getById(orderId));
        return getActionToRedirect(SEARCH_ORDER_ACTION, ORDER_ID, request.getParameter(ID));
    }
}
