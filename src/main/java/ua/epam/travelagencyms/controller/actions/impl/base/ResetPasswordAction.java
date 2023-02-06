package ua.epam.travelagencyms.controller.actions.impl.base;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.UserDTO;
import ua.epam.travelagencyms.exceptions.*;
import ua.epam.travelagencyms.model.services.*;
import ua.epam.travelagencyms.utils.EmailSender;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.getActionToRedirect;
import static ua.epam.travelagencyms.controller.actions.ActionUtil.getURL;
import static ua.epam.travelagencyms.controller.actions.ActionUtil.isPostMethod;
import static ua.epam.travelagencyms.controller.actions.ActionUtil.transferStringFromSessionToRequest;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.PASSWORD_RESET_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.RESET_PASSWORD_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.CHECK_EMAIL;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.EMAIL;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.ERROR;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.MESSAGE;
import static ua.epam.travelagencyms.utils.constants.Email.*;

/**
 * This is ResetPasswordAction class. Accessible by any user. Allows to reset user's password.
 * Implements PRG pattern
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class ResetPasswordAction implements Action {
    private final UserService userService;
    private final EmailSender emailSender;

    /**
     * @param appContext contains UserService and EmailSender instances to use in action
     */
    public ResetPasswordAction(AppContext appContext) {
        userService = appContext.getUserService();
        emailSender = appContext.getEmailSender();
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
     * @param request to get message, email and/or error attribute from session and put it in request
     * @return reset password page
     */
    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, EMAIL);
        transferStringFromSessionToRequest(request, ERROR);
        transferStringFromSessionToRequest(request, MESSAGE);
        return RESET_PASSWORD_PAGE;
    }

    /**
     * Called from doPost method in front-controller. Tries to reset users password via service. Gets email from
     * request. Sends email to user with new password if reset was successful.
     *
     * @param request to get users id and all passwords. Also, to set message in case of successful deleting and error
     * in another case
     *
     * @return path to redirect to executeGet method through front-controller
     */
    private String executePost(HttpServletRequest request) throws ServiceException {
        String email = request.getParameter(EMAIL);
        request.getSession().setAttribute(EMAIL, email);
        try {
            UserDTO user = userService.getByEmail(email);
            String newPass = userService.changePassword(user.getId());
            request.getSession().setAttribute(MESSAGE, CHECK_EMAIL);
            sendEmail(user, newPass, getURL(request));
        } catch (IncorrectFormatException | NoSuchUserException e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
        }
        return getActionToRedirect(PASSWORD_RESET_ACTION);
    }

    private void sendEmail(UserDTO user, String newPass, String url)  {
        String body = String.format(MESSAGE_RESET_PASSWORD, user.getName(), newPass, url);
        new Thread(() -> emailSender.send(PASSWORD_RESET, body, user.getEmail())).start();
    }
}