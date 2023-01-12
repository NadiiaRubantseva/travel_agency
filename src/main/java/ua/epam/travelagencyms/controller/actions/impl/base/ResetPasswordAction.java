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
import static ua.epam.travelagencyms.utils.constants.Email.MESSAGE_RESET_PASSWORD;
import static ua.epam.travelagencyms.utils.constants.Email.SUBJECT_NOTIFICATION;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.RESET_PASSWORD_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.CHECK_EMAIL;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.EMAIL;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.ERROR;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.MESSAGE;

public class ResetPasswordAction implements Action {
    private final UserService userService;
    private final EmailSender emailSender;

    public ResetPasswordAction(AppContext appContext) {
        userService = appContext.getUserService();
        emailSender = appContext.getEmailSender();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, EMAIL);
        transferStringFromSessionToRequest(request, ERROR);
        transferStringFromSessionToRequest(request, MESSAGE);
        return RESET_PASSWORD_PAGE;
    }

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
        new Thread(() -> emailSender.send(SUBJECT_NOTIFICATION, body, user.getEmail())).start();
    }
}