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

/**
 * This is SignInAction class. Accessible by any user. Allows to sign in web app. Implements PRG pattern
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class SignInAction implements Action {
    private final UserService userService;
    private final EmailSender emailSender;

    /**
     * @param appContext contains UserService and Email Service instances to use in action
     */
    public SignInAction(AppContext appContext) {
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
     * @param request to get email and error attribute from session and put it in request. Email for user to check
     * for mistakes
     * @return sign in page after failing to sign in
     */
    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, EMAIL);
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);
        return getPath(request);
    }

    /**
     * Called from doPost method in front-controller. Tries to sign in web app. If successful sets user to session and
     * redirects to profile page, if not sets error and email and redirects to executeGet
     *
     * @param request to get users email, password and set some attributes in session
     * @return profile page if successful, verify email page if email is not confirmed or path to redirect to executeGet method through front-controller if not
     */
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