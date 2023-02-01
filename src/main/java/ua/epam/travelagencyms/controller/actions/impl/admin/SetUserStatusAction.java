package ua.epam.travelagencyms.controller.actions.impl.admin;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.getActionToRedirect;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.SEARCH_USER_BY_ID_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

/**
 * This is SetUserStatusAction class. Accessible by admin. Allows to set user's status. Implements PRG pattern
 *
 * @author Vitalii Kalinchyk
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
     * Obtains required path and sets users status
     *
     * @param request to get user id and new status and put user in request
     * @return path to redirect to executeGet method in SearchTourAction through front-controller with required
     * parameters to find user.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String id = request.getParameter(ID);
        String status = request.getParameter(STATUS);
        userService.setStatus(Long.parseLong(id), status);
        request.setAttribute(USER, userService.getById(id));
        return getActionToRedirect(SEARCH_USER_BY_ID_ACTION, ID, id);
    }
}