package ua.epam.travelagencyms.controller.actions;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.controller.actions.util.MyRequest;
import ua.epam.travelagencyms.dto.TourDTO;
import ua.epam.travelagencyms.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ua.epam.travelagencyms.Constants.*;
import static ua.epam.travelagencyms.controller.actions.ActionUtil.*;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.DELETE_USER_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_UPDATE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.MESSAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.USER;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.TOUR;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.ID;


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
    void testGetURL() {
        String result = "http://localhost:8080/travel-agency-ms";
        when(request.getServletPath()).thenReturn("/controller");
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/travel-agency-ms/controller"));
        assertEquals(result, getURL(request));
    }

    private UserDTO getTestUserDTO() {
        return UserDTO.builder()
                .id(ID_VALUE)
                .email(EMAIL_VALUE)
                .name(NAME_VALUE)
                .surname(SURNAME_VALUE)
                .role(ROLE_VALUE)
                .build();
    }

    private TourDTO getTestTourDTO() {
        return TourDTO.builder()
                .id(ID_VALUE)
                .title(TITLE_VALUE)
                .persons(PERSONS_VALUE)
                .price(PRICE_VALUE)
                .hot(HOT_VALUE)
                .type(TYPE_TOUR_VALUE)
                .hotel(HOTEL_TOUR_VALUE)
                .build();
    }
}