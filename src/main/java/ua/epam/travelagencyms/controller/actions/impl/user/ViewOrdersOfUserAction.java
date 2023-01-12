package ua.epam.travelagencyms.controller.actions.impl.user;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.UserDTO;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.OrderService;
import ua.epam.travelagencyms.utils.query.QueryBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.epam.travelagencyms.controller.actions.constants.Pages.VIEW_ORDERS_BY_USER_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;
import static ua.epam.travelagencyms.utils.PaginationUtil.paginate;
import static ua.epam.travelagencyms.utils.QueryBuilderUtil.orderQueryBuilder;

public class ViewOrdersOfUserAction implements Action {
    private final OrderService orderService;

    public ViewOrdersOfUserAction(AppContext appContext) {
        orderService = appContext.getOrderService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        UserDTO userDTO = (UserDTO) request.getSession().getAttribute(LOGGED_USER);
        long userId = userDTO.getId();
        QueryBuilder queryBuilder = getQueryBuilder(request);
        queryBuilder.setUserIdFilter(userId);
        request.setAttribute(ORDERS, orderService.getSortedOrders(queryBuilder.getQuery()));
        int numberOfRecords = orderService.getNumberOfRecords(queryBuilder.getRecordQuery());
        paginate(numberOfRecords, request);
        return VIEW_ORDERS_BY_USER_PAGE;
    }

    private QueryBuilder getQueryBuilder(HttpServletRequest request) {
        return orderQueryBuilder()
                .setStatusFilter(request.getParameter(ORDER_STATUS_ID))
                .setSortField(request.getParameter(SORT))
                .setOrder(request.getParameter(ORDER))
                .setLimits(request.getParameter(OFFSET), request.getParameter(RECORDS));
    }
}
