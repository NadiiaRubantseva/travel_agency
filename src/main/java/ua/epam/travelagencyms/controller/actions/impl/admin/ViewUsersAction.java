package ua.epam.travelagencyms.controller.actions.impl.admin;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.UserService;
import ua.epam.travelagencyms.utils.query.QueryBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.transferStringFromSessionToRequest;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.VIEW_USERS_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;
import static ua.epam.travelagencyms.utils.PaginationUtil.paginate;
import static ua.epam.travelagencyms.utils.QueryBuilderUtil.userQueryBuilder;

/**
 * This is ViewUsersAction class. Accessible by admin. Allows to return list of sorted users.
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class ViewUsersAction implements Action {
    private final UserService userService;

    /**
     * @param appContext contains UserService instance to use in action
     */
    public ViewUsersAction(AppContext appContext) {
        userService = appContext.getUserService();
    }

    /**
     * Builds required query for service, set users list in request and obtains required path. Also sets all required
     * for pagination attributes
     *
     * @param request to get queries parameters and put users list in request
     * @return view users page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        // transfer attributes from session to request if any
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);

        // getting query according to request
        QueryBuilder queryBuilder = getQueryBuilder(request);

        // setting users list according to search criteria (query)
        request.setAttribute(USERS, userService.getSortedUsers(queryBuilder.getQuery()));

        // getting number of records for pagination
        int numberOfRecords = userService.getNumberOfRecords(queryBuilder.getRecordQuery());

        // setting attributes for pagination
        paginate(numberOfRecords, request);

        // return view orders page
        return VIEW_USERS_PAGE;
    }

    private QueryBuilder getQueryBuilder(HttpServletRequest request) {
        return userQueryBuilder()
                .setRoleFilter(request.getParameter(ROLE))
                .setSortField(request.getParameter(SORT))
                .setOrder(request.getParameter(ORDER))
                .setLimits(request.getParameter(OFFSET), request.getParameter(RECORDS));
    }
}
