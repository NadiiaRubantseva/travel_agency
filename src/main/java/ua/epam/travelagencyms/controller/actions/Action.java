package ua.epam.travelagencyms.controller.actions;

import ua.epam.travelagencyms.exceptions.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {

    String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException;

}
