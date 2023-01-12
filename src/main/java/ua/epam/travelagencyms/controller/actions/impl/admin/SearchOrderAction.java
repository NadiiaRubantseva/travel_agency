package ua.epam.travelagencyms.controller.actions.impl.admin;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.IncorrectFormatException;
import ua.epam.travelagencyms.exceptions.NoSuchOrderException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.epam.travelagencyms.controller.actions.constants.Pages.*;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

public class SearchOrderAction implements Action {
    private final OrderService orderService;

    public SearchOrderAction(AppContext appContext) {
        orderService = appContext.getOrderService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String path = ORDER_BY_ID_PAGE;
        try {
            request.setAttribute(ORDER, orderService.getById(request.getParameter(ORDER_ID)));
        } catch (NoSuchOrderException | IncorrectFormatException e) {
            request.setAttribute(ERROR, e.getMessage());
            path = SEARCH_ORDER_PAGE;
        }
        return path;
    }
}