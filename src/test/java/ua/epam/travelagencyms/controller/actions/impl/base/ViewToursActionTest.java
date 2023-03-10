package ua.epam.travelagencyms.controller.actions.impl.base;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.controller.actions.util.MyRequest;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.TourDTO;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.TourService;
import ua.epam.travelagencyms.utils.query.QueryBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ua.epam.travelagencyms.TestUtils.getTestTourDTO;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.VIEW_TOURS_BY_ADMIN_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.VIEW_TOURS_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.ADMIN;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;
import static ua.epam.travelagencyms.utils.QueryBuilderUtil.tourQueryBuilder;

public class ViewToursActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final TourService tourService = mock(TourService.class);
    private final String ZERO = "0";
    private final String EIGHT = "8";
    private final String ASC = "ASC";

    @Test
    void testExecuteForUsers() throws ServiceException {
        // arrange
        setRequest();
        MyRequest myRequest = new MyRequest(request);
        when(appContext.getTourService()).thenReturn(tourService);
        when(tourService.getSortedTours(getQueryBuilder().getQuery())).thenReturn(getTourDTOs());
        when(tourService.getNumberOfRecords(getQueryBuilder().getRecordQuery())).thenReturn(10);

        // act
        String path = new ViewToursAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(VIEW_TOURS_PAGE, path);
        assertEquals(getTourDTOs(), myRequest.getAttribute(TOURS));
        assertEquals(0, myRequest.getAttribute(OFFSET));
        assertEquals(8, myRequest.getAttribute(RECORDS));
        assertEquals(2, myRequest.getAttribute(PAGES));
        assertEquals(1, myRequest.getAttribute(CURRENT_PAGE));
        assertEquals(1, myRequest.getAttribute(START));
        assertEquals(2, myRequest.getAttribute(END));
    }

    @Test
    void testExecuteForAdminsOrManagers() throws ServiceException {
        // arrange
        setRequest();
        when(request.getParameter(VIEW)).thenReturn(ADMIN);
        MyRequest myRequest = new MyRequest(request);
        when(appContext.getTourService()).thenReturn(tourService);
        when(tourService.getSortedTours(getQueryBuilder().getQuery())).thenReturn(getTourDTOs());
        when(tourService.getNumberOfRecords(getQueryBuilder().getRecordQuery())).thenReturn(10);

        // act
        String path = new ViewToursAction(appContext).execute(myRequest, response);

        // assert
        assertEquals(VIEW_TOURS_BY_ADMIN_PAGE, path);
        assertEquals(getTourDTOs(), myRequest.getAttribute(TOURS));
        assertEquals(0, myRequest.getAttribute(OFFSET));
        assertEquals(8, myRequest.getAttribute(RECORDS));
        assertEquals(2, myRequest.getAttribute(PAGES));
        assertEquals(1, myRequest.getAttribute(CURRENT_PAGE));
        assertEquals(1, myRequest.getAttribute(START));
        assertEquals(2, myRequest.getAttribute(END));
    }

    private void setRequest() {
        when(request.getParameter(ORDER)).thenReturn(ASC);
        when(request.getParameter(OFFSET)).thenReturn(ZERO);
        when(request.getParameter(RECORDS)).thenReturn(EIGHT);
    }

    private QueryBuilder getQueryBuilder() {
        return tourQueryBuilder()
                .setOrder(ASC)
                .setLimits(ZERO, EIGHT);
    }

    public static List<TourDTO> getTourDTOs(){
        List<TourDTO> tours = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TourDTO tour = getTestTourDTO();
            tour.setId(i);
            tours.add(tour);
        }
        return tours;
    }
}
