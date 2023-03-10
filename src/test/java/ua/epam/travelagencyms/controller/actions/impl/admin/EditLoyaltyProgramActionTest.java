package ua.epam.travelagencyms.controller.actions.impl.admin;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.controller.actions.util.MyRequest;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.LoyaltyProgramDTO;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.implementation.LoyaltyProgramService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static ua.epam.travelagencyms.ConstantsForTest.GET;
import static ua.epam.travelagencyms.TestUtils.*;
import static ua.epam.travelagencyms.controller.actions.ActionUtil.getActionToRedirect;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.EDIT_LOYALTY_PROGRAM_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.EDIT_LOYALTY_PROGRAM_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.VIEW_LOYALTY_PROGRAM_BY_ADMIN_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_UPDATE;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.UNSUCCESSFUL_UPDATE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

class EditLoyaltyProgramActionTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final LoyaltyProgramService loyaltyProgramService = mock(LoyaltyProgramService.class);

    @Test
    void testExecutePost() throws ServiceException {
        // arrange
        MyRequest myRequest = new MyRequest(request);
        when(request.getMethod()).thenReturn(POST);
        when(request.getParameter(STEP)).thenReturn(STEP_STRING_VALUE);
        when(request.getParameter(DISCOUNT)).thenReturn(LOYALTY_PROGRAM_DISCOUNT_STRING_VALUE);
        when(request.getParameter(MAX_DISCOUNT)).thenReturn(MAX_DISCOUNT_STRING_VALUE);
        when(appContext.getLoyaltyProgramService()).thenReturn(loyaltyProgramService);
        doNothing().when(loyaltyProgramService).update(isA(LoyaltyProgramDTO.class));

        // act
        String path = new EditLoyaltyProgramAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(getActionToRedirect(EDIT_LOYALTY_PROGRAM_ACTION), path);
        assertEquals(SUCCEED_UPDATE, myRequest.getSession().getAttribute(MESSAGE));
        assertEquals(VIEW_LOYALTY_PROGRAM_BY_ADMIN_PAGE, myRequest.getSession().getAttribute(CURRENT_PATH));
    }

    @Test
    void testExecuteBadPost() throws ServiceException {
        // arrange
        MyRequest myRequest = new MyRequest(request);
        when(request.getMethod()).thenReturn(POST);
        when(request.getParameter(STEP)).thenReturn(STEP_STRING_VALUE);
        when(request.getParameter(DISCOUNT)).thenReturn(LOYALTY_PROGRAM_DISCOUNT_STRING_VALUE);
        when(request.getParameter(MAX_DISCOUNT)).thenReturn(MAX_DISCOUNT_STRING_VALUE);
        when(appContext.getLoyaltyProgramService()).thenReturn(loyaltyProgramService);
        doThrow(new ServiceException()).when(loyaltyProgramService).update(isA(LoyaltyProgramDTO.class));

        // act
        String path = new EditLoyaltyProgramAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(getActionToRedirect(EDIT_LOYALTY_PROGRAM_ACTION), path);
        assertEquals(UNSUCCESSFUL_UPDATE, myRequest.getSession().getAttribute(ERROR));
        assertEquals(EDIT_LOYALTY_PROGRAM_PAGE, myRequest.getSession().getAttribute(CURRENT_PATH));
    }

    @Test
    void testExecuteGet() throws ServiceException {
        // arrange
        MyRequest myRequest = new MyRequest(request);
        setGetRequest(myRequest);
        doThrow(new ServiceException()).when(loyaltyProgramService).update(isA(LoyaltyProgramDTO.class));

        // act
        new EditLoyaltyProgramAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(SUCCEED_UPDATE, myRequest.getAttribute(MESSAGE));
        assertEquals(UNSUCCESSFUL_UPDATE, myRequest.getAttribute(ERROR));
        assertNull(myRequest.getSession().getAttribute(MESSAGE));
        assertNull(myRequest.getSession().getAttribute(ERROR));
    }

    void setGetRequest(MyRequest myRequest) {
        when(request.getMethod()).thenReturn(GET);
        HttpSession session = myRequest.getSession();
        session.setAttribute(MESSAGE, SUCCEED_UPDATE);
        session.setAttribute(ERROR, UNSUCCESSFUL_UPDATE);
    }
}