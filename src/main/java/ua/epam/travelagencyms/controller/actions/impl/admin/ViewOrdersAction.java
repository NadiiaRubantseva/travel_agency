package ua.epam.travelagencyms.controller.actions.impl.admin;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.OrderService;
import ua.epam.travelagencyms.utils.query.QueryBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.transferStringFromSessionToRequest;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.VIEW_ORDERS_BY_ADMIN_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;
import static ua.epam.travelagencyms.utils.PaginationUtil.paginate;
import static ua.epam.travelagencyms.utils.QueryBuilderUtil.orderQueryBuilder;

/**
 * This is ViewOrdersAction class. Accessible by admin. Allows to return list of sorted orders.
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class ViewOrdersAction implements Action {
    private final OrderService orderService;

    /**
     * @param appContext contains OrderService instance to use in action
     */
    public ViewOrdersAction(AppContext appContext) {
        orderService = appContext.getOrderService();
    }

    /**
     * Builds required query for service, set orders list in request and obtains required path. Also sets all required
     * for pagination attributes
     *
     * @param request to get queries parameters and put orders list in request
     * @return view orders by admin page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        // transfer attributes from session to request if any
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);

        // getting query according to request
        QueryBuilder queryBuilder = getQueryBuilder(request);

        // setting orders list according to search criteria (query)
        request.setAttribute(ORDERS, orderService.getSortedOrders(queryBuilder.getQuery()));

        // getting number of records for pagination
        int numberOfRecords = orderService.getNumberOfRecords(queryBuilder.getRecordQuery());

        // setting attributes for pagination
        paginate(numberOfRecords, request);

        // return view orders page
        return VIEW_ORDERS_BY_ADMIN_PAGE;
    }

    private QueryBuilder getQueryBuilder(HttpServletRequest request) {
        return orderQueryBuilder()
                .setStatusFilter(request.getParameter(ORDER_STATUS_ID))
                .setDateFilter(request.getParameter(START_DATE), request.getParameter(END_DATE))
                .setSortField(request.getParameter(SORT))
                .setOrder(request.getParameter(ORDER))
                .setLimits(request.getParameter(OFFSET), request.getParameter(RECORDS));
    }
}
