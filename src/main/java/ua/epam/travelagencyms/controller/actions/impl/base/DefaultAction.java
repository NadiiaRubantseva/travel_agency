package ua.epam.travelagencyms.controller.actions.impl.base;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.exceptions.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.epam.travelagencyms.controller.actions.constants.Pages.INDEX_PAGE;

/**
 * This is DefaultAction class. Usually called if there is a mistake in action name.
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class DefaultAction implements Action {

    /**
     * @return index page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return INDEX_PAGE;
    }
}
