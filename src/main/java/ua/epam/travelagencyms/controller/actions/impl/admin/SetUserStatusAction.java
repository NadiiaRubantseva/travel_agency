package ua.epam.travelagencyms.controller.actions.impl.admin;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.IncorrectFormatException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.getActionToRedirect;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.SEARCH_USER_BY_ID_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_UPDATE;
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
     * @param request to get method, session and set all required attributes
     * @return path to redirect or forward by front-controller
     * @throws ServiceException to call error page in front-controller
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        // getting user id from request
        String id = request.getParameter(USER_ID);

        // getting status is from request
        String status = request.getParameter(USER_STATUS);
        try {

            // setting new user status in db
            userService.setStatus(id, status);

            // setting success message
            request.getSession().setAttribute(MESSAGE, SUCCEED_UPDATE);

        } catch (IncorrectFormatException e) {

            // setting error message
            request.getSession().setAttribute(ERROR, e.getMessage());
        }

        return getActionToRedirect(SEARCH_USER_BY_ID_ACTION, ID, id);

    }
}