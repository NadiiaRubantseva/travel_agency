package ua.epam.travelagencyms.controller.actions.impl.admin;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.OrderService;
import ua.epam.travelagencyms.utils.query.QueryBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.epam.travelagencyms.controller.actions.constants.Pages.VIEW_ORDERS_BY_ADMIN_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;
import static ua.epam.travelagencyms.utils.PaginationUtil.paginate;
import static ua.epam.travelagencyms.utils.QueryBuilderUtil.orderQueryBuilder;

public class ViewOrdersAction implements Action {
    private final OrderService orderService;

    public ViewOrdersAction(AppContext appContext) {
        orderService = appContext.getOrderService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        QueryBuilder queryBuilder = getQueryBuilder(request);
        request.setAttribute(ORDERS, orderService.getSortedOrders(queryBuilder.getQuery()));
        int numberOfRecords = orderService.getNumberOfRecords(queryBuilder.getRecordQuery());
        paginate(numberOfRecords, request);
        return VIEW_ORDERS_BY_ADMIN_PAGE;
    }

    private QueryBuilder getQueryBuilder(HttpServletRequest request) {
        return orderQueryBuilder()
                .setStatusFilter(request.getParameter(ORDER_STATUS_ID))
                .setSortField(request.getParameter(SORT))
                .setOrder(request.getParameter(ORDER))
                .setLimits(request.getParameter(OFFSET), request.getParameter(RECORDS));
    }
}
