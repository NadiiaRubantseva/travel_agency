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
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.ADD_TOUR_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.ADD_TOUR_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.VIEW_TOUR_BY_ADMIN_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_ADDED;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;
import static ua.epam.travelagencyms.exceptions.constants.Message.DUPLICATE_TITLE;


class AddTourActionTest {

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
        doNothing().when(tourService).add(isA(TourDTO.class));

        // act
        String path = new AddTourAction(appContext).execute(myRequest, response);
        TourDTO tourDTO = (TourDTO) myRequest.getSession().getAttribute(TOUR);

        // assert
        assertEquals(getActionToRedirect(ADD_TOUR_ACTION), path);
        assertEquals(SUCCEED_ADDED, myRequest.getSession().getAttribute(MESSAGE));
        assertNull(myRequest.getSession().getAttribute(ERROR));
        assertEquals(VIEW_TOUR_BY_ADMIN_PAGE, myRequest.getSession().getAttribute(CURRENT_PATH));
        assertEquals(TITLE_VALUE, tourDTO.getTitle());
        assertEquals(PERSONS_VALUE, tourDTO.getPersons());
        assertEquals(PRICE_VALUE, tourDTO.getPrice());
        assertEquals(HOT_STRING_VALUE, tourDTO.getHot());
        assertEquals(TYPE_VALUE, tourDTO.getType());
        assertEquals(HOTEL_VALUE, tourDTO.getHotel());
        assertEquals(DESCRIPTION_VALUE, tourDTO.getDescription());
        assertEquals(IMAGE_ENCODED_VALUE, tourDTO.getImage());

    }

    @Test
    void testExecuteBadPost() throws ServiceException, ServletException, IOException {
        // arrange
        MyRequest myRequest = new MyRequest(request);
        setPostRequest();
        when(part.getInputStream()).thenReturn(inputStream);
        when(inputStream.readAllBytes()).thenReturn(IMAGE_VALUE);
        when(appContext.getTourService()).thenReturn(tourService);
        doThrow(new DuplicateTitleException()).when(tourService).add(isA(TourDTO.class));

        // act
        String path = new AddTourAction(appContext).execute(myRequest, response);
        TourDTO tourDTO = (TourDTO) myRequest.getSession().getAttribute(TOUR);

        // assert
        assertEquals(getActionToRedirect(ADD_TOUR_ACTION), path);
        assertEquals(ADD_TOUR_PAGE, myRequest.getSession().getAttribute(CURRENT_PATH));
        assertEquals(DUPLICATE_TITLE, myRequest.getSession().getAttribute(ERROR));
        assertEquals(TITLE_VALUE, tourDTO.getTitle());
        assertEquals(PERSONS_VALUE, tourDTO.getPersons());
        assertEquals(PRICE_VALUE, tourDTO.getPrice());
        assertEquals(HOT_STRING_VALUE, tourDTO.getHot());
        assertEquals(TYPE_VALUE, tourDTO.getType());
        assertEquals(HOTEL_VALUE, tourDTO.getHotel());
        assertEquals(DESCRIPTION_VALUE, tourDTO.getDescription());
        assertEquals(IMAGE_ENCODED_VALUE, tourDTO.getImage());
    }

    @Test
    void testExecuteGet() throws ServiceException {
        // arrange
        MyRequest myRequest = new MyRequest(request);
        when(request.getMethod()).thenReturn(GET);
        myRequest.getSession().setAttribute(ERROR, DUPLICATE_TITLE);
        myRequest.getSession().setAttribute(MESSAGE, SUCCEED_ADDED);
        myRequest.getSession().setAttribute(TOUR, getTestTourDTO());

        // act
        new AddTourAction(appContext).execute(myRequest, response);

        // assert
        assertNull(myRequest.getSession().getAttribute(ERROR));
        assertNull(myRequest.getSession().getAttribute(MESSAGE));
        assertNull(myRequest.getSession().getAttribute(TOUR));
        assertEquals(myRequest.getAttribute(TOUR), getTestTourDTO());
    }

    @Test
    void testExecuteGetSuccessfullyAddedTour() throws ServiceException {
        // arrange
        MyRequest myRequest = new MyRequest(request);
        when(request.getMethod()).thenReturn(GET);
        myRequest.getSession().setAttribute(MESSAGE, SUCCEED_ADDED);
        myRequest.getSession().setAttribute(TOUR, getTestTourDTO());

        // act
        new AddTourAction(appContext).execute(myRequest, response);

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
        new AddTourAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(myRequest.getAttribute(ERROR), DUPLICATE_TITLE);
        assertEquals(myRequest.getAttribute(TOUR), getTestTourDTO());
    }

    private void setPostRequest() throws ServletException, IOException {
        when(request.getMethod()).thenReturn(POST);
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