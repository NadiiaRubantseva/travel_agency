package ua.epam.travelagencyms.controller.actions.impl.user;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.controller.actions.util.MyRequest;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static ua.epam.travelagencyms.TestUtils.ID_STRING_VALUE;
import static ua.epam.travelagencyms.TestUtils.POST;
import static ua.epam.travelagencyms.controller.actions.ActionUtil.getActionToRedirect;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.VIEW_ORDERS_OF_USER_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_CANCEL;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

public class CancelOrderActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final OrderService orderService = mock(OrderService.class);

    @Test
    void testExecutePost() throws ServiceException {
        // arrange
        MyRequest myRequest = new MyRequest(request);
        when(request.getMethod()).thenReturn(POST);
        when(request.getParameter(ORDER_ID)).thenReturn(ID_STRING_VALUE);
        when(appContext.getOrderService()).thenReturn(orderService);
        doNothing().when(orderService).delete(isA(String.class));

        // act
        String path = new CancelOrderAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(getActionToRedirect(VIEW_ORDERS_OF_USER_ACTION), path);
        assertEquals(SUCCEED_CANCEL, myRequest.getSession().getAttribute(MESSAGE));
        assertNull(myRequest.getSession().getAttribute(ERROR));
    }

    @Test
    void testExecuteBadPost() throws ServiceException {
        // arrange
        MyRequest myRequest = new MyRequest(request);
        when(request.getMethod()).thenReturn(POST);
        when(request.getParameter(ORDER_ID)).thenReturn(ID_STRING_VALUE);
        when(appContext.getOrderService()).thenReturn(orderService);
        doThrow(new ServiceException("failed to cancel")).when(orderService).delete(isA(String.class));

        // act
        String path = new CancelOrderAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(getActionToRedirect(VIEW_ORDERS_OF_USER_ACTION), path);
        assertEquals("failed to cancel", myRequest.getSession().getAttribute(ERROR));
        assertNull(myRequest.getSession().getAttribute(MESSAGE));
    }
}
