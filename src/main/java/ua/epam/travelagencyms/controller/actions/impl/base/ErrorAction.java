package ua.epam.travelagencyms.controller.actions.impl.base;

import ua.epam.travelagencyms.controller.actions.Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.epam.travelagencyms.controller.actions.constants.Pages.ERROR_PAGE;

/**
 * This is ErrorAction class.
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class ErrorAction implements Action {

    /**
     * @return error page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return ERROR_PAGE;
    }

}