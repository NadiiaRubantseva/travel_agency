package ua.epam.travelagencyms.controller.actions.impl.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ua.epam.travelagencyms.controller.actions.Action;

import static ua.epam.travelagencyms.controller.actions.constants.Pages.ERROR_PAGE;

public class ErrorAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return ERROR_PAGE;
    }

}