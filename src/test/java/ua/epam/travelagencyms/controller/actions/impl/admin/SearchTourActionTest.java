package ua.epam.travelagencyms.controller.actions.impl.admin;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import ua.epam.travelagencyms.controller.actions.util.MyRequest;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.UserDTO;
import ua.epam.travelagencyms.exceptions.IncorrectFormatException;
import ua.epam.travelagencyms.exceptions.NoSuchTourException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.TourService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ua.epam.travelagencyms.TestUtils.*;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.*;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.ADMIN;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.EDIT;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;
import static ua.epam.travelagencyms.exceptions.constants.Message.ENTER_CORRECT_ID;
import static ua.epam.travelagencyms.exceptions.constants.Message.NO_TOUR;

public class SearchTourActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final TourService tourService = mock(TourService.class);

    @Test
    void testExecuteSearchTourByUser() throws ServiceException {
        // arrange
        when(request.getParameter(TOUR_ID)).thenReturn(ID_STRING_VALUE);
        MyRequest myRequest = new MyRequest(request);
        myRequest.getSession().setAttribute(LOGGED_USER, getTestUserDTO());
        when(appContext.getTourService()).thenReturn(tourService);
        when(tourService.getById(isA(String.class))).thenReturn(getTestTourDTO());

        // act
        String page = new SearchTourAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(VIEW_TOUR_PAGE, page);
        assertEquals(getTestTourDTO(), myRequest.getAttribute(TOUR));
        assertNull(myRequest.getSession().getAttribute(ERROR));
    }

    @Test
    void testExecuteSearchTourByUserWithZeroDiscount() throws ServiceException {
        // arrange
        when(request.getParameter(TOUR_ID)).thenReturn(ID_STRING_VALUE);
        MyRequest myRequest = new MyRequest(request);
        UserDTO userDTO = getTestUserDTO();
        userDTO.setDiscount(0);
        myRequest.getSession().setAttribute(LOGGED_USER, userDTO);
        when(appContext.getTourService()).thenReturn(tourService);
        when(tourService.getById(isA(String.class))).thenReturn(getTestTourDTO());

        // act
        String page = new SearchTourAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(VIEW_TOUR_PAGE, page);
        assertEquals(getTestTourDTO(), myRequest.getAttribute(TOUR));
        assertNull(myRequest.getSession().getAttribute(ERROR));
    }

    @Test
    void testExecuteSearchTour() throws ServiceException {
        // arrange
        when(request.getParameter(TOUR_ID)).thenReturn(ID_STRING_VALUE);
        MyRequest myRequest = new MyRequest(request);
        when(appContext.getTourService()).thenReturn(tourService);
        when(tourService.getById(isA(String.class))).thenReturn(getTestTourDTO());

        // act
        String page = new SearchTourAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(VIEW_TOURS_PAGE, page);
        assertEquals(getTestTourDTO(), myRequest.getAttribute(TOUR));
        assertNull(myRequest.getSession().getAttribute(ERROR));
    }

    @Test
    void testExecuteSearchTourByAdmin() throws ServiceException {
        // arrange
        when(request.getParameter(TOUR_ID)).thenReturn(ID_STRING_VALUE);
        MyRequest myRequest = new MyRequest(request);
        UserDTO userDTO = getTestUserDTO();
        userDTO.setRole(ADMIN);
        myRequest.getSession().setAttribute(LOGGED_USER, userDTO);
        when(appContext.getTourService()).thenReturn(tourService);
        when(tourService.getById(isA(String.class))).thenReturn(getTestTourDTO());

        // act
        String page = new SearchTourAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(VIEW_TOUR_BY_ADMIN_PAGE, page);
        assertEquals(getTestTourDTO(), myRequest.getAttribute(TOUR));
        assertNull(myRequest.getSession().getAttribute(ERROR));
    }

    @Test
    void testExecuteSearchTourByAdminPurposeEdit() throws ServiceException {
        // arrange
        when(request.getParameter(TOUR_ID)).thenReturn(ID_STRING_VALUE);
        when(request.getParameter(PURPOSE)).thenReturn(EDIT);
        MyRequest myRequest = new MyRequest(request);
        UserDTO userDTO = getTestUserDTO();
        userDTO.setRole(ADMIN);
        myRequest.getSession().setAttribute(LOGGED_USER, userDTO);
        when(appContext.getTourService()).thenReturn(tourService);
        when(tourService.getById(isA(String.class))).thenReturn(getTestTourDTO());

        // act
        String page = new SearchTourAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(EDIT_TOUR_PAGE, page);
        assertEquals(getTestTourDTO(), myRequest.getAttribute(TOUR));
        assertNull(myRequest.getSession().getAttribute(ERROR));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testExecuteNoTourIdForUser(String id) throws ServiceException {
        // arrange
        when(request.getParameter(TOUR_ID)).thenReturn(id);
        MyRequest myRequest = new MyRequest(request);
        when(appContext.getTourService()).thenReturn(tourService);

        if (id==null) {
            when(tourService.getById(null)).thenThrow(new IncorrectFormatException(ENTER_CORRECT_ID));
        } else {
            when(tourService.getById(isA(String.class))).thenThrow(new IncorrectFormatException(ENTER_CORRECT_ID));
        }

        // act
        String page = new SearchTourAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(VIEW_TOURS_PAGE, page);
        assertEquals(ENTER_CORRECT_ID, myRequest.getAttribute(ERROR));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testExecuteNoTourIdForAdmin(String id) throws ServiceException {
        // arrange
        when(request.getParameter(TOUR_ID)).thenReturn(id);
        MyRequest myRequest = new MyRequest(request);
        when(appContext.getTourService()).thenReturn(tourService);
        UserDTO userDTO = getTestUserDTO();
        userDTO.setRole(ADMIN);
        myRequest.getSession().setAttribute(LOGGED_USER, userDTO);

        if (id==null) {
            when(tourService.getById(null)).thenThrow(new IncorrectFormatException(ENTER_CORRECT_ID));
        } else {
            when(tourService.getById(isA(String.class))).thenThrow(new IncorrectFormatException(ENTER_CORRECT_ID));
        }

        // act
        String page = new SearchTourAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(SEARCH_TOUR_PAGE, page);
        assertEquals(ENTER_CORRECT_ID, myRequest.getAttribute(ERROR));
    }

    @ParameterizedTest
    @ValueSource(strings = {"3232r", "one", "-3"})
    void testExecuteBadIdsForUser(String id) throws ServiceException {
        // arrange
        when(request.getParameter(TOUR_ID)).thenReturn(id);
        MyRequest myRequest = new MyRequest(request);
        when(appContext.getTourService()).thenReturn(tourService);
        when(tourService.getById(id)).thenThrow(new IncorrectFormatException(ENTER_CORRECT_ID));

        // act
        String page = new SearchTourAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(VIEW_TOURS_PAGE, page);
        assertEquals(ENTER_CORRECT_ID, myRequest.getAttribute(ERROR));
    }

    @ParameterizedTest
    @ValueSource(strings = {"3232r", "one", "-3"})
    void testExecuteBadIdsForAdmin(String id) throws ServiceException {
        // arrange
        when(request.getParameter(TOUR_ID)).thenReturn(id);
        MyRequest myRequest = new MyRequest(request);
        UserDTO userDTO = getTestUserDTO();
        userDTO.setRole(ADMIN);
        myRequest.getSession().setAttribute(LOGGED_USER, userDTO);
        when(appContext.getTourService()).thenReturn(tourService);
        when(tourService.getById(id)).thenThrow(new IncorrectFormatException(ENTER_CORRECT_ID));

        // act
        String page = new SearchTourAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(SEARCH_TOUR_PAGE, page);
        assertEquals(ENTER_CORRECT_ID, myRequest.getAttribute(ERROR));
    }

    @ParameterizedTest
    @ValueSource(strings = {"10000", "100000"})
    void testExecuteNoTourForUser(String id) throws ServiceException {
        // arrange
        when(request.getParameter(TOUR_ID)).thenReturn(id);
        MyRequest myRequest = new MyRequest(request);
        when(appContext.getTourService()).thenReturn(tourService);
        when(tourService.getById(isA(String.class))).thenThrow(new NoSuchTourException());

        // act
        String page = new SearchTourAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(VIEW_TOURS_PAGE, page);
        assertEquals(NO_TOUR, myRequest.getAttribute(ERROR));
    }

    @ParameterizedTest
    @ValueSource(strings = {"10000", "100000"})
    void testExecuteNoTourForAdmin(String id) throws ServiceException {
        // arrange
        when(request.getParameter(TOUR_ID)).thenReturn(id);
        MyRequest myRequest = new MyRequest(request);
        UserDTO userDTO = getTestUserDTO();
        userDTO.setRole(ADMIN);
        myRequest.getSession().setAttribute(LOGGED_USER, userDTO);
        when(appContext.getTourService()).thenReturn(tourService);
        when(tourService.getById(isA(String.class))).thenThrow(new NoSuchTourException());

        // act
        String page = new SearchTourAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(SEARCH_TOUR_PAGE, page);
        assertEquals(NO_TOUR, myRequest.getAttribute(ERROR));
    }
}
