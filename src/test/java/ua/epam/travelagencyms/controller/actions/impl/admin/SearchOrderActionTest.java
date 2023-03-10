package ua.epam.travelagencyms.controller.actions.impl.admin;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import ua.epam.travelagencyms.controller.actions.util.MyRequest;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.IncorrectFormatException;
import ua.epam.travelagencyms.exceptions.NoSuchOrderException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ua.epam.travelagencyms.TestUtils.ID_STRING_VALUE;
import static ua.epam.travelagencyms.TestUtils.getTestOrderDTO;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.SEARCH_ORDER_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.VIEW_ORDER_BY_ADMIN_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;
import static ua.epam.travelagencyms.exceptions.constants.Message.ENTER_CORRECT_ID;
import static ua.epam.travelagencyms.exceptions.constants.Message.NO_ORDER;

public class SearchOrderActionTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final OrderService orderService = mock(OrderService.class);

    @Test
    void testExecute() throws ServiceException {
        // arrange
        when(request.getParameter(ORDER_ID)).thenReturn(ID_STRING_VALUE);
        MyRequest myRequest = new MyRequest(request);
        when(appContext.getOrderService()).thenReturn(orderService);
        when(orderService.getById(isA(String.class))).thenReturn(getTestOrderDTO());

        // act
        String page = new SearchOrderAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(VIEW_ORDER_BY_ADMIN_PAGE, page);
        assertEquals(getTestOrderDTO(), myRequest.getAttribute(ORDER));
        assertNull(myRequest.getSession().getAttribute(ERROR));
    }

    @ParameterizedTest
    @ValueSource(strings = {"3232r", "one", "-3"})
    void testExecuteBadIds(String id) throws ServiceException {
        // arrange
        when(request.getParameter(ORDER_ID)).thenReturn(id);
        MyRequest myRequest = new MyRequest(request);
        when(appContext.getOrderService()).thenReturn(orderService);
        when(orderService.getById(id)).thenThrow(new IncorrectFormatException(ENTER_CORRECT_ID));

        // act
        String page = new SearchOrderAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(SEARCH_ORDER_PAGE, page);
        assertEquals(ENTER_CORRECT_ID, myRequest.getAttribute(ERROR));
    }


    @ParameterizedTest
    @NullAndEmptySource
    void testExecuteNoIds(String id) throws ServiceException {
        // arrange
        when(request.getParameter(ORDER_ID)).thenReturn(id);
        MyRequest myRequest = new MyRequest(request);
        when(appContext.getOrderService()).thenReturn(orderService);

        if (id==null) {
            when(orderService.getById(null)).thenThrow(new IncorrectFormatException(ENTER_CORRECT_ID));
        } else {
            when(orderService.getById(isA(String.class))).thenThrow(new IncorrectFormatException(ENTER_CORRECT_ID));
        }

        // act
        String page = new SearchOrderAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(SEARCH_ORDER_PAGE, page);
        assertEquals(ENTER_CORRECT_ID, myRequest.getAttribute(ERROR));
    }

    @ParameterizedTest
    @ValueSource(strings = {"10000", "100000"})
    void testExecuteNoUser(String id) throws ServiceException {
        // arrange
        when(request.getParameter(ORDER_ID)).thenReturn(id);
        MyRequest myRequest = new MyRequest(request);
        when(appContext.getOrderService()).thenReturn(orderService);
        when(orderService.getById(id)).thenThrow(new NoSuchOrderException());

        // act
        String page = new SearchOrderAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(SEARCH_ORDER_PAGE, page);
        assertEquals(NO_ORDER, myRequest.getAttribute(ERROR));
    }

}
