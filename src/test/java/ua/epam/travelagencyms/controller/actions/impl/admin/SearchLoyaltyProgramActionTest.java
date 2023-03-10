package ua.epam.travelagencyms.controller.actions.impl.admin;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.controller.actions.util.MyRequest;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.implementation.LoyaltyProgramService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static ua.epam.travelagencyms.TestUtils.GET;
import static ua.epam.travelagencyms.TestUtils.getTestLoyaltyProgramDTO;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.*;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.EDIT;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

public class SearchLoyaltyProgramActionTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final LoyaltyProgramService loyaltyProgramService = mock(LoyaltyProgramService.class);

    @Test
    void testExecuteGet() throws ServiceException {
        // arrange
        MyRequest myRequest = new MyRequest(request);
        when(request.getMethod()).thenReturn(GET);
        when(request.getParameter(PURPOSE)).thenReturn(null);
        when(appContext.getLoyaltyProgramService()).thenReturn(loyaltyProgramService);
        when(loyaltyProgramService.get()).thenReturn(getTestLoyaltyProgramDTO());

        // act
        String path = new SearchLoyaltyProgramAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(VIEW_LOYALTY_PROGRAM_BY_ADMIN_PAGE, path);
        assertEquals(getTestLoyaltyProgramDTO(), myRequest.getAttribute(LOYALTY_PROGRAM));
        assertNull(myRequest.getSession().getAttribute(MESSAGE));
        assertNull(myRequest.getSession().getAttribute(ERROR));
    }

    @Test
    void testExecuteGetForEdit() throws ServiceException {
        // arrange
        MyRequest myRequest = new MyRequest(request);
        when(request.getMethod()).thenReturn(GET);
        when(request.getParameter(PURPOSE)).thenReturn(EDIT);
        when(appContext.getLoyaltyProgramService()).thenReturn(loyaltyProgramService);
        when(loyaltyProgramService.get()).thenReturn(getTestLoyaltyProgramDTO());

        // act
        String path = new SearchLoyaltyProgramAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(EDIT_LOYALTY_PROGRAM_PAGE, path);
        assertEquals(getTestLoyaltyProgramDTO(), myRequest.getAttribute(LOYALTY_PROGRAM));
        assertNull(myRequest.getSession().getAttribute(MESSAGE));
        assertNull(myRequest.getSession().getAttribute(ERROR));
    }

    @Test
    void testServiceException() throws ServiceException {
        // arrange
        MyRequest myRequest = new MyRequest(request);
        when(request.getMethod()).thenReturn(GET);
        when(request.getParameter(PURPOSE)).thenReturn(null);
        when(appContext.getLoyaltyProgramService()).thenReturn(loyaltyProgramService);
        doThrow(ServiceException.class).when(loyaltyProgramService).get();

        // act
        String path = new SearchLoyaltyProgramAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(INDEX_PAGE, path);
    }
}
