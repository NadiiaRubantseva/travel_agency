package ua.epam.travelagencyms.controller.actions.impl.admin;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.controller.actions.util.MyRequest;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.IncorrectFormatException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.OrderService;
import ua.epam.travelagencyms.model.services.UserService;
import ua.epam.travelagencyms.model.services.implementation.LoyaltyProgramService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static ua.epam.travelagencyms.ConstantsForTest.POST;
import static ua.epam.travelagencyms.TestUtils.*;
import static ua.epam.travelagencyms.controller.actions.ActionUtil.getActionToRedirect;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.SEARCH_ORDER_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_UPDATE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;
import static ua.epam.travelagencyms.exceptions.constants.Message.ENTER_CORRECT_ID;

public class UpdateOrderStatusActionTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final UserService userService = mock(UserService.class);
    private final LoyaltyProgramService loyaltyProgramService = mock(LoyaltyProgramService.class);
    private final OrderService orderService = mock(OrderService.class);


    @Test
    void testExecuteStatusPaid() throws ServiceException {
        // arrange
        MyRequest myRequest = new MyRequest(request);
        when(request.getMethod()).thenReturn(POST);
        when(request.getParameter(ID)).thenReturn(ID_STRING_VALUE);
        when(request.getParameter(USER_ID)).thenReturn(ID_STRING_VALUE);
        when(request.getParameter(STATUS)).thenReturn(ORDER_STATUS_VALUE);
        when(appContext.getOrderService()).thenReturn(orderService);
        when(appContext.getUserService()).thenReturn(userService);
        when(appContext.getLoyaltyProgramService()).thenReturn(loyaltyProgramService);
        doNothing().when(orderService).setOrderStatus(isA(String.class), isA(String.class));
        when(loyaltyProgramService.get()).thenReturn(getTestLoyaltyProgramDTO());
        when(orderService.viewUsersOrders(isA(long.class))).thenReturn(List.of(getTestOrderDTO(), getTestOrderDTO(), getTestOrderDTO(), getTestOrderDTO(), getTestOrderDTO(), getTestOrderDTO()));
        doNothing().when(userService).setDiscount(isA(int.class), isA(String.class));

        // act
        String path = new UpdateOrderStatusAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(getActionToRedirect(SEARCH_ORDER_ACTION, ORDER_ID, ID_STRING_VALUE), path);
        assertEquals(SUCCEED_UPDATE, myRequest.getSession().getAttribute(MESSAGE));
        assertNull(myRequest.getSession().getAttribute(ERROR));
    }

    @Test
    void testExecuteStatusCanceled() throws ServiceException {
        // arrange
        MyRequest myRequest = new MyRequest(request);
        when(request.getMethod()).thenReturn(POST);
        when(request.getParameter(ID)).thenReturn(ID_STRING_VALUE);
        when(request.getParameter(USER_ID)).thenReturn(ID_STRING_VALUE);
        when(request.getParameter(STATUS)).thenReturn("CANCELED");
        when(appContext.getOrderService()).thenReturn(orderService);
        when(appContext.getUserService()).thenReturn(userService);
        when(appContext.getLoyaltyProgramService()).thenReturn(loyaltyProgramService);
        doNothing().when(orderService).setOrderStatus(isA(String.class), isA(String.class));
        when(loyaltyProgramService.get()).thenReturn(getTestLoyaltyProgramDTO());
        when(orderService.viewUsersOrders(isA(long.class))).thenReturn(List.of(getTestOrderDTO(), getTestOrderDTO(), getTestOrderDTO(), getTestOrderDTO(), getTestOrderDTO(), getTestOrderDTO()));
        doNothing().when(userService).setDiscount(isA(int.class), isA(String.class));

        // act
        String path = new UpdateOrderStatusAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(getActionToRedirect(SEARCH_ORDER_ACTION, ORDER_ID, ID_STRING_VALUE), path);
        assertEquals(SUCCEED_UPDATE, myRequest.getSession().getAttribute(MESSAGE));
        assertNull(myRequest.getSession().getAttribute(ERROR));
    }

    @Test
    void testExecuteStatusRegistered() throws ServiceException {
        // arrange
        MyRequest myRequest = new MyRequest(request);
        when(request.getMethod()).thenReturn(POST);
        when(request.getParameter(ID)).thenReturn(ID_STRING_VALUE);
        when(request.getParameter(STATUS)).thenReturn("REGISTERED");
        when(appContext.getOrderService()).thenReturn(orderService);
        doNothing().when(orderService).setOrderStatus(isA(String.class), isA(String.class));

        // act
        String path = new UpdateOrderStatusAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(getActionToRedirect(SEARCH_ORDER_ACTION, ORDER_ID, ID_STRING_VALUE), path);
        assertEquals(SUCCEED_UPDATE, myRequest.getSession().getAttribute(MESSAGE));
        assertNull(myRequest.getSession().getAttribute(ERROR));
    }

    @Test
    void testExecuteFail() throws ServiceException {
        // arrange
        MyRequest myRequest = new MyRequest(request);
        when(request.getMethod()).thenReturn(POST);
        when(request.getParameter(ID)).thenReturn(ID_STRING_VALUE);
        when(request.getParameter(STATUS)).thenReturn(ORDER_STATUS_VALUE);
        when(appContext.getOrderService()).thenReturn(orderService);
        doThrow(new IncorrectFormatException(ENTER_CORRECT_ID)).when(orderService).setOrderStatus(isA(String.class), isA(String.class));

        // act
        String path = new UpdateOrderStatusAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(getActionToRedirect(SEARCH_ORDER_ACTION, ORDER_ID, ID_STRING_VALUE), path);
        assertEquals(ENTER_CORRECT_ID, myRequest.getSession().getAttribute(ERROR));
        assertNull(myRequest.getSession().getAttribute(MESSAGE));
    }

}
