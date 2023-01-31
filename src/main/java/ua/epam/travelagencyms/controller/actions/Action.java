package ua.epam.travelagencyms.controller.actions;

import ua.epam.travelagencyms.exceptions.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action interface. Implement it to create new actions
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public interface Action {

    /**
     * Obtains path to sendRedirect or forward in front-controller. Edits request and response if needed.
     *
     * @param request passed by controller
     * @param response passed by controller
     * @return path to return to front-controller
     * @throws ServiceException - any unhandled exception. Will cause front-controller to redirect to error page
     */
    String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException;

}
