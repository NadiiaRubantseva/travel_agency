package ua.epam.travelagencyms.controller.actions.impl.base;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.UserDTO;
import ua.epam.travelagencyms.exceptions.IncorrectCodeException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.*;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.VERIFY_CODE_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.PROFILE_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.VERIFY_EMAIL_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

/**
 * This is VerifyCodeAction class. Accessible by any user. Created for validating security code purposes.
 * Implements PRG pattern
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class VerifyCodeAction implements Action {
    private final UserService userService;

    /**
     * @param appContext contains UserService instance to use in action
     */
    public VerifyCodeAction(AppContext appContext) {
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
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }

    /**
     * Called from doGet method in front-controller. Obtains required path and transfer attributes from session
     * to request.
     *
     * @param request to get message, error attributes from session and put it in request
     * @return profile page in case of success, otherwise verify email page
     */
    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);
        return getPath(request);
    }

    /**
     * Called from doPost method in front-controller. Tries to validate security code.
     *
     * @param request to get user id and verification code and set error in case of unsuccessful code verification
     * @return path to redirect to executeGet method through front-controller with required parameters
     */
    private String executePost(HttpServletRequest request) throws ServiceException {
        String path = PROFILE_PAGE;

        // getting UserDTO from request
        UserDTO user = (UserDTO) request.getSession().getAttribute(LOGGED_USER);

        // getting entered security code from db
        String enteredCode = request.getParameter(SECURITY_CODE).trim();

        try {

            // verifying security code
            userService.verifySecurityCode(user.getId(), enteredCode);

        } catch (IncorrectCodeException e) {
            // setting error message
            request.getSession().setAttribute(ERROR, e.getMessage());

            // setting page to keep staying on the same one
            path = VERIFY_EMAIL_PAGE;
        }

        // setting current path
        request.getSession().setAttribute(CURRENT_PATH, path);

        // redirecting
        return getActionToRedirect(VERIFY_CODE_ACTION);
    }
}