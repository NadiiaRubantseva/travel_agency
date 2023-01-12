package ua.epam.travelagencyms.controller.actions.impl.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.*;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.getActionToRedirect;
import static ua.epam.travelagencyms.controller.actions.ActionUtil.isPostMethod;
import static ua.epam.travelagencyms.controller.actions.ActionUtil.transferStringFromSessionToRequest;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.DELETE_USER_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.SEARCH_USER_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_DELETE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.MESSAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.USER_ID;

public class DeleteUserAction implements Action {
    private final UserService userService;

    public DeleteUserAction(AppContext appContext) {
        userService = appContext.getUserService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        return SEARCH_USER_PAGE;
    }

    private String executePost(HttpServletRequest request) throws ServiceException {
        userService.delete(request.getParameter(USER_ID));
        request.getSession().setAttribute(MESSAGE, SUCCEED_DELETE);
        return getActionToRedirect(DELETE_USER_ACTION);
    }
}