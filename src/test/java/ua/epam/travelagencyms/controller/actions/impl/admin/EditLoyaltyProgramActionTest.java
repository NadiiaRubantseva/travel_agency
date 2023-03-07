package ua.epam.travelagencyms.controller.actions.impl.admin;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.controller.actions.util.MyRequest;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.LoyaltyProgramDTO;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.implementation.LoyaltyProgramService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ua.epam.travelagencyms.ConstantsForTest.POST;
import static ua.epam.travelagencyms.TestUtils.*;
import static ua.epam.travelagencyms.controller.actions.ActionUtil.getActionToRedirect;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.SEARCH_LOYALTY_PROGRAM_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_UPDATE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

class EditLoyaltyProgramActionTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final LoyaltyProgramService loyaltyProgramService = mock(LoyaltyProgramService.class);

    @Test
    void testExecutePost() throws ServiceException {
        MyRequest myRequest = new MyRequest(request);
        when(request.getMethod()).thenReturn(POST);
        when(request.getParameter(STEP)).thenReturn(STEP_STRING_VALUE);
        when(request.getParameter(DISCOUNT)).thenReturn(LOYALTY_PROGRAM_DISCOUNT_STRING_VALUE);
        when(request.getParameter(MAX_DISCOUNT)).thenReturn(MAX_DISCOUNT_STRING_VALUE);
        when(appContext.getLoyaltyProgramService()).thenReturn(loyaltyProgramService);
        doNothing().when(loyaltyProgramService).update(isA(LoyaltyProgramDTO.class));
        String path = new EditLoyaltyProgramAction(appContext).execute(myRequest, response);
        assertEquals(getActionToRedirect(SEARCH_LOYALTY_PROGRAM_ACTION), path);
        assertEquals(SUCCEED_UPDATE, myRequest.getSession().getAttribute(MESSAGE));
    }

    @Test
    void testExecuteBadPost() throws ServiceException {
        MyRequest myRequest = new MyRequest(request);
        when(request.getMethod()).thenReturn(POST);
        when(request.getParameter(STEP)).thenReturn(STEP_STRING_VALUE);
        when(request.getParameter(DISCOUNT)).thenReturn(LOYALTY_PROGRAM_DISCOUNT_STRING_VALUE);
        when(request.getParameter(MAX_DISCOUNT)).thenReturn(MAX_DISCOUNT_STRING_VALUE);
        when(appContext.getLoyaltyProgramService()).thenReturn(loyaltyProgramService);
        doThrow(new ServiceException()).when(loyaltyProgramService).update(isA(LoyaltyProgramDTO.class));
        String path = new EditLoyaltyProgramAction(appContext).execute(myRequest, response);
        assertNull(myRequest.getSession().getAttribute(MESSAGE));
//        assertNotNull(myRequest.getSession().getAttribute(ERROR));
        assertEquals(getActionToRedirect(SEARCH_LOYALTY_PROGRAM_ACTION), path);
    }
}