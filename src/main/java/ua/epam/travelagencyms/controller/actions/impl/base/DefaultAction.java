package ua.epam.travelagencyms.controller.actions.impl.base;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.exceptions.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.epam.travelagencyms.controller.actions.constants.Pages.INDEX_PAGE;

public class DefaultAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return INDEX_PAGE;
    }
}
