package ua.epam.travelagencyms.controller.actions.impl.admin;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.IncorrectFormatException;
import ua.epam.travelagencyms.exceptions.NoSuchTourException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.TourService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.epam.travelagencyms.controller.actions.constants.Pages.*;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

public class ViewTourAction implements Action {
    private final TourService tourService;

    public ViewTourAction(AppContext appContext) {
        tourService = appContext.getTourService();
    }


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String path = VIEW_TOUR_BY_ADMIN_PAGE;
        try {
            request.setAttribute(TOUR, tourService.getById(request.getParameter(ID)));
            request.setAttribute(MESSAGE, request.getSession().getAttribute(MESSAGE));
            request.getSession().removeAttribute(MESSAGE);
            request.setAttribute(IMAGE, tourService.getById(request.getParameter(ID)).getDecodedImage());
        } catch (NoSuchTourException | IncorrectFormatException e) {
            request.setAttribute(ERROR, e.getMessage());
            path = SEARCH_TOUR_PAGE;
        }
        return path;
    }
}