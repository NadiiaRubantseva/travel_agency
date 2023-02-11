package ua.epam.travelagencyms.controller.actions.impl.admin;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.controller.actions.util.MyRequest;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.DuplicateTitleException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.TourService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static ua.epam.travelagencyms.ConstantsForTest.*;
import static ua.epam.travelagencyms.MethodsForTest.getTourDTO;
import static ua.epam.travelagencyms.controller.actions.ActionUtil.getActionToRedirect;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.ADD_TOUR_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.ADD_TOUR_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.HOT;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.PERSONS;
import static ua.epam.travelagencyms.exceptions.constants.Message.DUPLICATE_TITLE;

class AddTourActionTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final TourService tourService = mock(TourService.class);

    @Test
    void testExecutePost() throws ServiceException, ServletException, IOException {
        MyRequest myRequest = new MyRequest(request);
        setPostRequest();
        when(appContext.getTourService()).thenReturn(tourService);
        doNothing().when(tourService).add(getTourDTO());
        when(tourService.getByTitle(TITLE_VALUE)).thenReturn(getTourDTO());
        String path = new AddTourAction(appContext).execute(myRequest, response);
        assertEquals(getActionToRedirect(ADD_TOUR_ACTION), path);
    }

    @Test
    void testExecuteBadPost() throws ServiceException, ServletException, IOException {
        MyRequest myRequest = new MyRequest(request);
        setPostRequest();
        when(appContext.getTourService()).thenReturn(tourService);
        doThrow(new DuplicateTitleException()).when(tourService).add(getTourDTO());
        String path = new AddTourAction(appContext).execute(myRequest, response);

        assertEquals(getActionToRedirect(ADD_TOUR_ACTION), path);
        assertEquals(getTourDTO(), myRequest.getSession().getAttribute(TOUR));
        assertEquals(DUPLICATE_TITLE, myRequest.getSession().getAttribute(ERROR));
    }

    @Test
    void testExecuteGet() throws ServiceException {
        MyRequest myRequest = new MyRequest(request);
        when(request.getMethod()).thenReturn(GET);
        myRequest.getSession().setAttribute(ERROR, DUPLICATE_TITLE);
        myRequest.getSession().setAttribute(TOUR, getTourDTO());
        myRequest.getSession().setAttribute(CURRENT_PATH, ADD_TOUR_PAGE);

        assertEquals(ADD_TOUR_PAGE, new AddTourAction(appContext).execute(myRequest, response));
        assertEquals(DUPLICATE_TITLE, myRequest.getAttribute(ERROR));
        assertNull(myRequest.getSession().getAttribute(ERROR));
        assertNull(myRequest.getSession().getAttribute(TOUR));
    }

    private void setPostRequest() throws ServletException, IOException {
        when(request.getMethod()).thenReturn(POST);
        when(request.getParameter(TITLE)).thenReturn(TITLE_VALUE);
        when(request.getParameter(PERSONS)).thenReturn(PERSONS_VALUE);
        when(request.getParameter(PRICE)).thenReturn(PRICE_VALUE);
        when(request.getParameter(HOT)).thenReturn(HOT_VALUE);
        when(request.getParameter(TYPE)).thenReturn(TYPE_VALUE);
        when(request.getParameter(TYPE)).thenReturn(TYPE_VALUE);
        when(request.getPart(IMAGE)).thenReturn(mock(Part.class));
    }
}