package ua.epam.travelagencyms.controller.actions.impl.admin;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.*;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.SET_USER_STATUS_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.VIEW_USER_BY_ADMIN_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_UPDATE;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.UNSUCCESSFUL_UPDATE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

/**
 * This is SetUserStatusAction class. Accessible by admin. Allows to set user's status. Implements PRG pattern
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class SetUserStatusAction implements Action {
    private final UserService userService;

    /**
     * @param appContext contains UserService instance to use in action
     */
    public SetUserStatusAction(AppContext appContext) {
        userService = appContext.getUserService();
    }

    /**
     * Checks method and calls required implementation
     *
     * @param request  to get method, session and set all required attributes
     * @return path to redirect or forward by front-controller
     * @throws ServiceException to call error page in front-controller
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);
        transferUserDTOFromSessionToRequest(request);
        return VIEW_USER_BY_ADMIN_PAGE;
    }

    public String executePost(HttpServletRequest request) throws ServiceException {
        String id = request.getParameter(USER_ID);
        String status = request.getParameter(USER_STATUS);
        try {
            userService.setStatus(Long.parseLong(id), status);
            request.getSession().setAttribute(USER, userService.getById(id));
            request.getSession().setAttribute(MESSAGE, SUCCEED_UPDATE);
        } catch (ServiceException e) {
            request.getSession().setAttribute(ERROR, UNSUCCESSFUL_UPDATE);
        }
        return getActionToRedirect(SET_USER_STATUS_ACTION);
    }
}