package ua.epam.travelagencyms.controller.actions.impl.admin;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.controller.actions.util.MyRequest;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.TourDTO;
import ua.epam.travelagencyms.exceptions.DuplicateTitleException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.TourService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ua.epam.travelagencyms.ConstantsForTest.*;
import static ua.epam.travelagencyms.MethodsForTest.getTourDTO;
import static ua.epam.travelagencyms.controller.actions.ActionUtil.getActionToRedirect;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.*;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.EDIT_TOUR_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_UPDATE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.HOT;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.PERSONS;
import static ua.epam.travelagencyms.exceptions.constants.Message.BAD_IMAGE;
import static ua.epam.travelagencyms.exceptions.constants.Message.DUPLICATE_TITLE;

class EditTourActionTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final TourService tourService = mock(TourService.class);

    @Test
    void testExecutePost() throws ServiceException, ServletException, IOException {
        MyRequest myRequest = new MyRequest(request);
        setPostRequest();
        when(appContext.getTourService()).thenReturn(tourService);
        when(tourService.getImage(isA(String.class))).thenReturn("image");
        doNothing().when(tourService).update(isA(TourDTO.class));
        String path = new EditTourAction(appContext).execute(myRequest, response);

        assertEquals(getActionToRedirect(SEARCH_TOUR_ACTION, TOUR_ID, ONE), path);
        assertEquals(SUCCEED_UPDATE, myRequest.getSession().getAttribute(MESSAGE));
    }

    @Test
    void testExecutePostWithDuplicateTitle() throws ServiceException, ServletException, IOException {
        MyRequest myRequest = new MyRequest(request);
        setPostRequest();
        when(appContext.getTourService()).thenReturn(tourService);
        when(tourService.getImage(isA(String.class))).thenReturn("image");
        doThrow(new DuplicateTitleException()).when(tourService).update(getTourDTO());
        String path = new EditTourAction(appContext).execute(myRequest, response);

        assertEquals(getActionToRedirect(EDIT_TOUR_ACTION), path);
        assertEquals(getTourDTO(), myRequest.getSession().getAttribute(TOUR));
        assertEquals(DUPLICATE_TITLE, myRequest.getSession().getAttribute(ERROR));
    }

    @Test
    void testExecutePostWithBadImage() throws ServiceException, ServletException, IOException {
        MyRequest myRequest = new MyRequest(request);
        setBadPostRequest();
        String path = new EditTourAction(appContext).execute(myRequest, response);

        assertEquals(getActionToRedirect(EDIT_TOUR_ACTION), path);
        assertEquals(BAD_IMAGE, myRequest.getSession().getAttribute(ERROR));
    }

    @Test
    void testExecuteGet() throws ServiceException {
        MyRequest myRequest = new MyRequest(request);
        setGetRequest(myRequest, getTourDTO());
        when(appContext.getTourService()).thenReturn(tourService);
        when(tourService.getImage(isA(String.class))).thenReturn("image");
        String path = new EditTourAction(appContext).execute(myRequest, response);

        assertEquals(EDIT_TOUR_PAGE, path);
        assertEquals(getTourDTO(), myRequest.getAttribute(TOUR));
        assertEquals(SUCCEED_UPDATE, myRequest.getAttribute(MESSAGE));
        assertEquals(DUPLICATE_TITLE, myRequest.getAttribute(ERROR));
        assertNull(myRequest.getSession().getAttribute(MESSAGE));
        assertNull(myRequest.getSession().getAttribute(ERROR));
    }

    private void setGetRequest(MyRequest myRequest, TourDTO tourDTO) {
        when(request.getMethod()).thenReturn(GET);
        when(request.getParameter(TOUR_ID)).thenReturn(ONE);
        HttpSession session = myRequest.getSession();
        session.setAttribute(MESSAGE, SUCCEED_UPDATE);
        session.setAttribute(ERROR, DUPLICATE_TITLE);
        session.setAttribute(TOUR, tourDTO);
    }

    private void setPostRequest() throws ServletException, IOException {
        when(request.getMethod()).thenReturn(POST);
        when(request.getParameter(TOUR_ID)).thenReturn(ONE);
        when(request.getParameter(TITLE)).thenReturn(TITLE_VALUE);
        when(request.getParameter(PERSONS)).thenReturn(PERSONS_VALUE);
        when(request.getParameter(PRICE)).thenReturn(PRICE_VALUE);
        when(request.getParameter(HOT)).thenReturn(HOT_VALUE);
        when(request.getParameter(TYPE)).thenReturn(TYPE_VALUE);
        when(request.getParameter(HOTEL)).thenReturn(HOTEL_VALUE);
        when(request.getPart(IMAGE)).thenReturn(mock(Part.class));
        when(request.getParameter(DESCRIPTION)).thenReturn(DESCRIPTION_VALUE);
    }

    private void setBadPostRequest() throws ServletException, IOException {
        when(request.getMethod()).thenReturn(POST);
        when(request.getParameter(TOUR_ID)).thenReturn(ONE);
        when(request.getParameter(TITLE)).thenReturn(TITLE_VALUE);
        when(request.getParameter(PERSONS)).thenReturn(PERSONS_VALUE);
        when(request.getParameter(PRICE)).thenReturn(PRICE_VALUE);
        when(request.getParameter(HOT)).thenReturn(HOT_VALUE);
        when(request.getParameter(TYPE)).thenReturn(TYPE_VALUE);
        when(request.getParameter(HOTEL)).thenReturn(HOTEL_VALUE);
        when(request.getPart(IMAGE)).thenThrow(new ServletException());
        when(request.getParameter(DESCRIPTION)).thenReturn(DESCRIPTION_VALUE);
    }

}