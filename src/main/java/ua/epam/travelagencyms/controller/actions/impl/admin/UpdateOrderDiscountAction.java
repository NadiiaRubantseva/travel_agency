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

public class UpdateOrderDiscountAction implements Action {
    private final OrderService orderService;

    public UpdateOrderDiscountAction(AppContext appContext) {
        orderService = appContext.getOrderService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String orderDiscount = request.getParameter(DISCOUNT);
        String orderId = request.getParameter(ID);
        String tourPrice = request.getParameter(TOUR_PRICE);
        orderService.setDiscount(orderDiscount, tourPrice, orderId);
        request.setAttribute(ORDER, orderService.getById(orderId));
        return getActionToRedirect(SEARCH_ORDER_ACTION, ORDER_ID, request.getParameter(ID));
    }
}
