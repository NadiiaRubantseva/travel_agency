package ua.epam.travelagencyms.controller.actions.impl.base;

import javax.servlet.http.*;
import ua.epam.travelagencyms.controller.actions.Action;

import static ua.epam.travelagencyms.controller.actions.constants.Pages.SIGN_IN_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.LOGGED_USER;

public class SignOutAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession().getAttribute(LOGGED_USER) != null) {
            request.getSession().invalidate();
        }
        return SIGN_IN_PAGE;
    }
}