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
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static ua.epam.travelagencyms.TestUtils.*;
import static ua.epam.travelagencyms.controller.actions.ActionUtil.getActionToRedirect;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.EDIT_TOUR_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.EDIT_TOUR_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.VIEW_TOUR_BY_ADMIN_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_ADDED;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_UPDATE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;
import static ua.epam.travelagencyms.exceptions.constants.Message.DUPLICATE_TITLE;

class EditTourActionTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final TourService tourService = mock(TourService.class);
    private final Part part = mock(Part.class);
    private final InputStream inputStream = mock(InputStream.class);


    @Test
    void testExecutePost() throws ServiceException, ServletException, IOException {
        // arrange
        MyRequest myRequest = new MyRequest(request);
        setPostRequest();
        when(part.getInputStream()).thenReturn(inputStream);
        when(inputStream.readAllBytes()).thenReturn(IMAGE_VALUE);
        when(appContext.getTourService()).thenReturn(tourService);
        doNothing().when(tourService).update(isA(TourDTO.class));

        // act
        String path = new EditTourAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(getActionToRedirect(EDIT_TOUR_ACTION), path);
        assertEquals(SUCCEED_UPDATE, myRequest.getSession().getAttribute(MESSAGE));
        assertNull(myRequest.getSession().getAttribute(ERROR));
        assertEquals(VIEW_TOUR_BY_ADMIN_PAGE, myRequest.getSession().getAttribute(CURRENT_PATH));
        assertEquals(getTestTourDTO(), myRequest.getSession().getAttribute(TOUR));

    }

    @Test
    void testExecutePostWithNoImage() throws ServiceException, ServletException, IOException {
        // arrange
        MyRequest myRequest = new MyRequest(request);
        setPostRequest();
        when(part.getInputStream()).thenReturn(inputStream);
        when(inputStream.readAllBytes()).thenReturn(new byte[]{});
        when(appContext.getTourService()).thenReturn(tourService);
        when(tourService.getImage(isA(String.class))).thenReturn(IMAGE_ENCODED_VALUE);
        doNothing().when(tourService).update(isA(TourDTO.class));

        // act
        String path = new EditTourAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(getActionToRedirect(EDIT_TOUR_ACTION), path);
        assertEquals(SUCCEED_UPDATE, myRequest.getSession().getAttribute(MESSAGE));
        assertNull(myRequest.getSession().getAttribute(ERROR));
        assertEquals(VIEW_TOUR_BY_ADMIN_PAGE, myRequest.getSession().getAttribute(CURRENT_PATH));
        assertEquals(getTestTourDTO(), myRequest.getSession().getAttribute(TOUR));

    }

    @Test
    void testExecuteBadPost() throws ServiceException, ServletException, IOException {
        // arrange
        MyRequest myRequest = new MyRequest(request);
        setPostRequest();
        when(part.getInputStream()).thenReturn(inputStream);
        when(inputStream.readAllBytes()).thenReturn(IMAGE_VALUE);
        when(appContext.getTourService()).thenReturn(tourService);
        doThrow(new DuplicateTitleException()).when(tourService).update(isA(TourDTO.class));

        // act
        String path = new EditTourAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(getActionToRedirect(EDIT_TOUR_ACTION), path);
        assertEquals(EDIT_TOUR_PAGE, myRequest.getSession().getAttribute(CURRENT_PATH));
        assertEquals(DUPLICATE_TITLE, myRequest.getSession().getAttribute(ERROR));
        assertEquals(getTestTourDTO(), myRequest.getSession().getAttribute(TOUR));
        assertNull(myRequest.getSession().getAttribute(MESSAGE));
    }

    @Test
    void testExecuteGetSuccessfullyEditedTour() throws ServiceException {
        // arrange
        MyRequest myRequest = new MyRequest(request);
        when(request.getMethod()).thenReturn(GET);
        myRequest.getSession().setAttribute(MESSAGE, SUCCEED_ADDED);
        myRequest.getSession().setAttribute(TOUR, getTestTourDTO());

        // act
        new EditTourAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(myRequest.getAttribute(MESSAGE), SUCCEED_ADDED);
        assertNull(myRequest.getAttribute(ERROR));
        assertEquals(myRequest.getAttribute(TOUR), getTestTourDTO());
    }

    @Test
    void testExecuteGetFailedAddingTour() throws ServiceException {
        // arrange
        MyRequest myRequest = new MyRequest(request);
        when(request.getMethod()).thenReturn(GET);
        myRequest.getSession().setAttribute(ERROR, DUPLICATE_TITLE);
        assertNull(myRequest.getAttribute(MESSAGE));
        myRequest.getSession().setAttribute(TOUR, getTestTourDTO());

        // act
        new EditTourAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(myRequest.getAttribute(ERROR), DUPLICATE_TITLE);
        assertEquals(myRequest.getAttribute(TOUR), getTestTourDTO());
    }


    private void setPostRequest() throws ServletException, IOException {
        when(request.getMethod()).thenReturn(POST);
        when(request.getParameter(TOUR_ID)).thenReturn(ID_STRING_VALUE);
        when(request.getParameter(TITLE)).thenReturn(TITLE_VALUE);
        when(request.getParameter(PERSONS)).thenReturn(PERSONS_STRING_VALUE);
        when(request.getParameter(PRICE)).thenReturn(PRICE_STRING_VALUE);
        when(request.getParameter(HOT)).thenReturn(HOT_STRING_VALUE);
        when(request.getParameter(TYPE)).thenReturn(TYPE_VALUE);
        when(request.getParameter(HOTEL)).thenReturn(HOTEL_VALUE);
        when(request.getParameter(DESCRIPTION)).thenReturn(DESCRIPTION_VALUE);
        when(request.getPart(IMAGE)).thenReturn(part);
    }
}