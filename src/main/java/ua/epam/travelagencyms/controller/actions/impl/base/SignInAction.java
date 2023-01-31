package ua.epam.travelagencyms.controller.actions.impl.base;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.UserService;
import ua.epam.travelagencyms.dto.UserDTO;
import ua.epam.travelagencyms.exceptions.*;
import ua.epam.travelagencyms.utils.EmailSender;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.isPostMethod;
import static ua.epam.travelagencyms.controller.actions.ActionUtil.*;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.SIGN_IN_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.*;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;
import static ua.epam.travelagencyms.utils.constants.Email.*;


public class SignInAction implements Action {
    private final UserService userService;
    private final EmailSender emailSender;

    public SignInAction(AppContext appContext) {
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
        return getPath(request);
    }

    private String executePost(HttpServletRequest request) throws ServiceException {
        String path = PROFILE_PAGE;
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        try {
            setLoggedUser(request, userService.signIn(email, password));
            userService.isEmailConfirmed(email);
            return path;
        } catch (NoSuchUserException | IncorrectPasswordException e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
            request.getSession().setAttribute(EMAIL, email);
            path = SIGN_IN_PAGE;
        } catch (NotConfirmedEmailException e) {
            String code = userService.setVerificationCode(email);
            sendEmail(code, email);
            path = VERIFY_EMAIL_PAGE;
        }
        request.getSession().setAttribute(CURRENT_PATH, path);
        return getActionToRedirect(SIGN_IN_ACTION);
    }

    private static void setLoggedUser(HttpServletRequest request, UserDTO user) {
        request.getSession().setAttribute(LOGGED_USER, user);
        request.getSession().setAttribute(ROLE, user.getRole());
    }

    private void sendEmail(String code, String email) {
        String body = String.format(MESSAGE_VERIFY_EMAIL, code);
        new Thread(() -> emailSender.send(EMAIL_VERIFICATION, body, email)).start();
    }
}