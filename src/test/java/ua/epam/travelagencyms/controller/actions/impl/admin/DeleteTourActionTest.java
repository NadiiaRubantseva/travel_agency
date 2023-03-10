package ua.epam.travelagencyms.controller.actions.impl.admin;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.controller.actions.util.MyRequest;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.TourService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static ua.epam.travelagencyms.ConstantsForTest.ONE;
import static ua.epam.travelagencyms.ConstantsForTest.POST;
import static ua.epam.travelagencyms.controller.actions.ActionUtil.getActionToRedirect;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.VIEW_TOURS_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_DELETE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.MESSAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.TOUR_ID;

class DeleteTourActionTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final TourService tourService = mock(TourService.class);

    @Test
    void testExecutePost() throws ServiceException {
        // arrange
        MyRequest myRequest = new MyRequest(request);
        when(request.getMethod()).thenReturn(POST);
        when(request.getParameter(TOUR_ID)).thenReturn(ONE);
        when(appContext.getTourService()).thenReturn(tourService);
        doNothing().when(tourService).delete(ONE);

        // act
        String path = new DeleteTourAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(getActionToRedirect(VIEW_TOURS_ACTION), path);
        assertEquals(SUCCEED_DELETE, myRequest.getSession().getAttribute(MESSAGE));
    }
}