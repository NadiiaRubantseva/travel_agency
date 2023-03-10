package ua.epam.travelagencyms.controller.actions.impl.base;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.UserDTO;
import ua.epam.travelagencyms.exceptions.*;
import ua.epam.travelagencyms.model.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.*;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.CHANGE_PASSWORD_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.CHANGE_PASSWORD_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_UPDATE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

/**
 * This is ChangePasswordAction class. Accessible by any logged user. Allows to change user's password.
 * Implements PRG pattern
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class ChangePasswordAction implements Action {
    private final UserService userService;

    /**
     * @param appContext contains UserService instance to use in action
     */
    public ChangePasswordAction(AppContext appContext) {
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

    /**
     * Called from doGet method in front-controller. Obtains required path and transfer attributes from session
     * to request
     *
     * @param request to get message or error attribute from session and put it in request
     * @return change password page
     */
    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);
        return CHANGE_PASSWORD_PAGE;
    }

    /**
     * Called from doPost method in front-controller. Tries to change users password via service
     *
     * @param request to get users id and all passwords. Also, to set message in case of successful deleting and error
     * in another case
     *
     * @return path to redirect to executeGet method through front-controller
     */
    private String executePost(HttpServletRequest request) throws ServiceException {
        try {
            // retrieving passwords, updating password in db
            userServiceChangePassword(request);

            // setting success message
            request.getSession().setAttribute(MESSAGE, SUCCEED_UPDATE);
        } catch (IncorrectFormatException | IncorrectPasswordException | NoSuchUserException | PasswordMatchingException e) {

            // setting error message
            request.getSession().setAttribute(ERROR, e.getMessage());
        }
        return getActionToRedirect(CHANGE_PASSWORD_ACTION);
    }

    private void userServiceChangePassword(HttpServletRequest request) throws ServiceException {
        // getting logged user id
        long id = ((UserDTO) request.getSession().getAttribute(LOGGED_USER)).getId();

        // getting old password from request
        String oldPassword = request.getParameter(OLD_PASSWORD);

        // getting new password from request
        String password = request.getParameter(PASSWORD);

        // getting new password from request to confirm
        String confirmPassword = request.getParameter(CONFIRM_PASSWORD);

        // updating password in db
        userService.changePassword(id, oldPassword, password, confirmPassword);
    }
}