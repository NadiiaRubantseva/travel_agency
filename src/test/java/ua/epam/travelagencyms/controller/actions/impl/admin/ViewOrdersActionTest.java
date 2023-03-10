package ua.epam.travelagencyms.controller.actions.impl.admin;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.controller.actions.util.MyRequest;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.OrderDTO;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.OrderService;
import ua.epam.travelagencyms.utils.query.QueryBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ua.epam.travelagencyms.TestUtils.*;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.VIEW_ORDERS_BY_ADMIN_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;
import static ua.epam.travelagencyms.utils.QueryBuilderUtil.orderQueryBuilder;

public class ViewOrdersActionTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final OrderService orderService = mock(OrderService.class);
    private final String ZERO = "0";
    private final String EIGHT = "8";
    private final String ASC = "ASC";
    @Test
    void testExecute() throws ServiceException {
        // arrange
        setRequest();
        MyRequest myRequest = new MyRequest(request);
        when(appContext.getOrderService()).thenReturn(orderService);

        //  ORDER BY date ASC LIMIT 0, 8
        System.out.println(getQueryBuilder().getQuery());
        when(orderService.getSortedOrders(getQueryBuilder().getQuery())).thenReturn(getOrderDTOs());
        when(orderService.getNumberOfRecords(getQueryBuilder().getRecordQuery())).thenReturn(10);

        // act
        String path = new ViewOrdersAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(VIEW_ORDERS_BY_ADMIN_PAGE, path);
        assertEquals(getOrderDTOs(), myRequest.getAttribute(ORDERS));
        assertEquals(0, myRequest.getAttribute(OFFSET));
        assertEquals(8, myRequest.getAttribute(RECORDS));
        assertEquals(2, myRequest.getAttribute(PAGES));
        assertEquals(1, myRequest.getAttribute(CURRENT_PAGE));
        assertEquals(1, myRequest.getAttribute(START));
        assertEquals(2, myRequest.getAttribute(END));
    }

    private void setRequest() {
        when(request.getParameter(ORDER_STATUS_ID)).thenReturn(ORDER_STATUS_VALUE);
        when(request.getParameter(SORT)).thenReturn(DATE);
        when(request.getParameter(ORDER)).thenReturn(ASC);
        when(request.getParameter(OFFSET)).thenReturn(ZERO);
        when(request.getParameter(RECORDS)).thenReturn(EIGHT);
    }

    private QueryBuilder getQueryBuilder() {
        return orderQueryBuilder()
                .setSortField(DATE)
                .setOrder(ASC)
                .setLimits(ZERO, EIGHT);
    }

    public static List<OrderDTO> getOrderDTOs(){
        List<OrderDTO> orders = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            orders.add(OrderDTO.builder()
                    .id(i)
                    .orderStatus(ORDER_STATUS_VALUE)
                    .userId(ID_VALUE)
                    .userEmail(EMAIL_VALUE)
                    .userName(NAME_VALUE)
                    .userSurname(SURNAME_VALUE)
                    .tourId(ID_VALUE)
                    .tourTitle(TITLE_VALUE)
                    .tourPrice(PRICE_VALUE)
                    .discount(DISCOUNT_VALUE)
                    .totalCost(TOTAL_COST_VALUE)
                    .date(DATE_STRING_VALUE)
                    .build());
        }
        return orders;
    }
}
