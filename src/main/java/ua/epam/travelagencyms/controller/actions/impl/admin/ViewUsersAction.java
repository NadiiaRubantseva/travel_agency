package ua.epam.travelagencyms.controller.actions.impl.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.*;
import ua.epam.travelagencyms.utils.query.QueryBuilder;

import static ua.epam.travelagencyms.controller.actions.constants.Pages.VIEW_USERS_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.OFFSET;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.ORDER;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.RECORDS;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.ROLE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.SORT;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.USERS;
import static ua.epam.travelagencyms.utils.PaginationUtil.paginate;
import static ua.epam.travelagencyms.utils.QueryBuilderUtil.userQueryBuilder;

public class ViewUsersAction implements Action {
    private final UserService userService;

    public ViewUsersAction(AppContext appContext) {
        userService = appContext.getUserService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        QueryBuilder queryBuilder = getQueryBuilder(request);
        request.setAttribute(USERS, userService.getSortedUsers(queryBuilder.getQuery()));
        int numberOfRecords = userService.getNumberOfRecords(queryBuilder.getRecordQuery());
        paginate(numberOfRecords, request);
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
