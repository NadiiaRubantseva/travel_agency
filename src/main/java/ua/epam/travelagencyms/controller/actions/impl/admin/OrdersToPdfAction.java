package ua.epam.travelagencyms.controller.actions.impl.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.OrderDTO;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.OrderService;
import ua.epam.travelagencyms.utils.PdfUtil;
import ua.epam.travelagencyms.utils.query.QueryBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.getActionToRedirect;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.VIEW_ORDERS_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;
import static ua.epam.travelagencyms.utils.QueryBuilderUtil.orderQueryBuilder;

/**
 * This is OrdersToPdfAction class. Accessible by admin. Allows to download list of all orders that match demands
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */

public class OrdersToPdfAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(OrdersToPdfAction.class);
    private final OrderService orderService;
    private final PdfUtil pdfUtil;

    /**
     * @param appContext contains OrderService and PdfUtil instances to use in action
     */
    public OrdersToPdfAction(AppContext appContext) {
        orderService = appContext.getOrderService();
        pdfUtil = appContext.getPdfUtil();
    }

    /**
     * Builds required query for service, sets order list in response to download.
     * Checks for locale to set it up for pdf document
     *
     * @param request to get queries parameters
     * @param response to set orders list there
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        // preparing query for db
        QueryBuilder queryBuilder = getQueryBuilder(request);

        // getting orders according to query
        List<OrderDTO> orders = orderService.getSortedOrders(queryBuilder.getQuery());

        // getting user locale
        String locale = (String) request.getSession().getAttribute(LOCALE);

        // getting stream for pdf document output
        ByteArrayOutputStream ordersPDF = pdfUtil.createOrdersPdf(orders, locale);

        // setting pdf document as a response
        setResponse(response, ordersPDF);

        // logging
        logger.info("Pdf document of orders successfully generated");

        // redirecting to the same page
        return getActionToRedirect(VIEW_ORDERS_ACTION);
    }

    /**
     * Sets orders list in response to download. Configure response to download pdf document
     *
     * @param response to set orders list there
     * @param output - output stream that contains pdf document
     */
    private void setResponse(HttpServletResponse response, ByteArrayOutputStream output) {
        response.setContentType("application/pdf");
        response.setContentLength(output.size());
        response.setHeader("Content-Disposition", "attachment; filename=\"orders.pdf\"");
        try (OutputStream outputStream = response.getOutputStream()) {
            output.writeTo(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private QueryBuilder getQueryBuilder(HttpServletRequest request) {
        String zero = "0";
        String max = String.valueOf(Integer.MAX_VALUE);
        return orderQueryBuilder()
                .setStatusFilter(request.getParameter(ORDER_STATUS_ID))
                .setSortField(request.getParameter(SORT))
                .setOrder(request.getParameter(ORDER))
                .setLimits(zero, max);
    }

}