package ua.epam.travelagencyms.controller.actions;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.controller.actions.util.MyRequest;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ua.epam.travelagencyms.ConstantsForTest.GET;
import static ua.epam.travelagencyms.ConstantsForTest.POST;
import static ua.epam.travelagencyms.MethodsForTest.getTestTourDTO;
import static ua.epam.travelagencyms.MethodsForTest.getTestUserDTO;
import static ua.epam.travelagencyms.TestUtils.getTestOrderDTO;
import static ua.epam.travelagencyms.controller.actions.ActionUtil.*;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.DELETE_USER_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_UPDATE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;


class ActionUtilTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);

    @Test
    void testIsPostMethodForPost() {
        when(request.getMethod()).thenReturn(POST);
        assertTrue(isPostMethod(request));
    }

    @Test
    void testIsPostMethodForGet() {
        when(request.getMethod()).thenReturn(GET);
        assertFalse(isPostMethod(request));
    }

    @Test
    void testTransferStringFromSessionToRequest() {
        MyRequest myRequest = new MyRequest(request);
        myRequest.getSession().setAttribute(MESSAGE, SUCCEED_UPDATE);
        transferStringFromSessionToRequest(myRequest, MESSAGE);
        assertEquals(SUCCEED_UPDATE, myRequest.getAttribute(MESSAGE));
        assertNull(myRequest.getSession().getAttribute(MESSAGE));
    }

    @Test
    void testTransferStringFromSessionToRequestNullString() {
        MyRequest myRequest = new MyRequest(request);
        transferStringFromSessionToRequest(myRequest, MESSAGE);
        assertNull(myRequest.getAttribute(MESSAGE));
        assertNull(myRequest.getSession().getAttribute(MESSAGE));
    }

    @Test
    void testTransferUserDTOFromSessionToRequest() {
        MyRequest myRequest = new MyRequest(request);
        myRequest.getSession().setAttribute(USER, getTestUserDTO());
        transferUserDTOFromSessionToRequest(myRequest);
        assertEquals(getTestUserDTO(), myRequest.getAttribute(USER));
        assertNull(myRequest.getSession().getAttribute(USER));
    }

    @Test
    void testTransferUserDTOFromSessionToRequestNoUser() {
        MyRequest myRequest = new MyRequest(request);
        transferUserDTOFromSessionToRequest(myRequest);
        assertNull(myRequest.getAttribute(USER));
        assertNull(myRequest.getSession().getAttribute(USER));
    }

    @Test
    void testTransferTourDTOFromSessionToRequest() {
        MyRequest myRequest = new MyRequest(request);
        myRequest.getSession().setAttribute(TOUR, getTestTourDTO());
        transferTourDTOFromSessionToRequest(myRequest);
        assertEquals(getTestTourDTO(), myRequest.getAttribute(TOUR));
        assertNull(myRequest.getSession().getAttribute(TOUR));
    }

    @Test
    void testTransferTourDTOFromSessionToRequestNoTour() {
        MyRequest myRequest = new MyRequest(request);
        transferTourDTOFromSessionToRequest(myRequest);
        assertNull(myRequest.getAttribute(TOUR));
        assertNull(myRequest.getSession().getAttribute(TOUR));
    }

    @Test
    void testTransferOrderDTOFromSessionToRequest() {
        MyRequest myRequest = new MyRequest(request);
        myRequest.getSession().setAttribute(ORDER, getTestOrderDTO());
        transferOrderDTOFromSessionToRequest(myRequest);
        assertEquals(getTestOrderDTO(), myRequest.getAttribute(ORDER));
        assertNull(myRequest.getSession().getAttribute(ORDER));
    }

    @Test
    void testTransferOrderDTOFromSessionToRequestNoOrder() {
        MyRequest myRequest = new MyRequest(request);
        transferOrderDTOFromSessionToRequest(myRequest);
        assertNull(myRequest.getAttribute(ORDER));
        assertNull(myRequest.getSession().getAttribute(ORDER));
    }

    @Test
    void testGetActionToRedirectNoParameters() {
        String result = "controller?action=delete-user";
        assertEquals(result, getActionToRedirect(DELETE_USER_ACTION));
    }

    @Test
    void testGetActionToRedirectWithParameter() {
        String result = "controller?action=delete-user&id=1";
        assertEquals(result, getActionToRedirect(DELETE_USER_ACTION, ID, "1"));
    }

    @Test
    void testGetActionToRedirectWithParameters() {
        String result = "controller?action=delete-user&user-id=1&order-id=1";
        assertEquals(result, getActionToRedirect(DELETE_USER_ACTION, USER_ID, "1", ORDER_ID, "1"));
    }

    @Test
    void testGetURL() {
        String result = "http://localhost:8080/travel-agency-ms";
        when(request.getServletPath()).thenReturn("/controller");
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/travel-agency-ms/controller"));
        assertEquals(result, getURL(request));
    }

}