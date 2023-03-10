package ua.epam.travelagencyms.controller.actions.impl.base;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.TourService;
import ua.epam.travelagencyms.utils.query.QueryBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.transferStringFromSessionToRequest;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.VIEW_TOURS_BY_ADMIN_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.VIEW_TOURS_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.ADMIN;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;
import static ua.epam.travelagencyms.utils.PaginationUtil.paginate;
import static ua.epam.travelagencyms.utils.QueryBuilderUtil.tourQueryBuilder;

/**
 * This is ViewToursAction class. Accessible by admin. Allows to return list of sorted tours.
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class ViewToursAction implements Action {
    private final TourService tourService;

    /**
     * @param appContext contains TourService instance to use in action
     */
    public ViewToursAction(AppContext appContext) {
        tourService = appContext.getTourService();
    }

    /**
     * Builds required query for service, set tours list in request and obtains required path. Also sets all required
     * for pagination attributes
     *
     * @param request to get queries parameters and put tours list in request
     * @return view tours page by admin if user is admin, or view tours by user page if user is user.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        // transferring any messages from session to request
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);

        // page to display tours
        String path = VIEW_TOURS_PAGE;

        // getting view option
        String view = request.getParameter(VIEW);

        // setting special view tours page for admin and manager
        if (view != null && view.equalsIgnoreCase(ADMIN)) {
            path = VIEW_TOURS_BY_ADMIN_PAGE;
        }

        // getting query according to submitted search criteria
        QueryBuilder queryBuilder = getQueryBuilder(request);

        // setting tours as an attribute
        request.setAttribute(TOURS, tourService.getSortedTours(queryBuilder.getQuery()));

        // retrieving number of records (for pagination)
        int numberOfRecords = tourService.getNumberOfRecords(queryBuilder.getRecordQuery());

        // setting attributes for pagination
        paginate(numberOfRecords, request);

        // returning path
        return path;
    }

    private QueryBuilder getQueryBuilder(HttpServletRequest request) {
        return tourQueryBuilder()
                .setTypeFilter(request.getParameter(TYPE))
                .setHotelFilter(request.getParameter(HOTEL))
                .setPriceFilter(request.getParameter(MIN_PRICE), request.getParameter(MAX_PRICE))
                .setPersonsFilter(request.getParameter(PERSONS))
                .setSortField(request.getParameter(SORT))
                .setOrder(request.getParameter(ORDER))
                .setLimits(request.getParameter(OFFSET), request.getParameter(RECORDS));
    }
}
