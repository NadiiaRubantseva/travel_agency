package ua.epam.travelagencyms.controller.actions.impl.admin;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.TourDTO;
import ua.epam.travelagencyms.exceptions.DuplicateTitleException;
import ua.epam.travelagencyms.exceptions.IncorrectFormatException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.TourService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.*;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.*;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.*;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_UPDATE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

public class EditTourAction implements Action {
    private final TourService tourService;

    public EditTourAction(AppContext appContext) {
        tourService = appContext.getTourService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);
        transferTourDTOFromSessionToRequest(request);
        String image = (String) request.getSession().getAttribute(IMAGE);
        if (image != null) {
            request.setAttribute(IMAGE, image);
            request.getSession().removeAttribute(IMAGE);
        }
        return EDIT_TOUR;
    }

    private String executePost(HttpServletRequest request) throws ServiceException {
        String path = VIEW_TOUR_BY_ADMIN_PAGE;
        TourDTO tour = getTourDTO(request);
        request.getSession().setAttribute(TOUR, tour);
        request.getSession().setAttribute(IMAGE, tourService.getById(String.valueOf(tour.getId())).getDecodedImage());
        try {
            tourService.update(tour);
            request.getSession().setAttribute(MESSAGE, SUCCEED_UPDATE);
        } catch (IncorrectFormatException | DuplicateTitleException e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
            path = EDIT_TOUR;
        }
        request.getSession().setAttribute(CURRENT_PATH, path);
        return getActionToRedirect(VIEW_TOUR_ACTION, ID, String.valueOf(tour.getId()));
    }

    private TourDTO getTourDTO(HttpServletRequest request) throws ServiceException {
        String hot = null;
        if (request.getParameter(HOT) != null) {
            hot = HOT;
        }

        return TourDTO.builder()
                .id(Long.parseLong(request.getParameter(ID)))
                .title(request.getParameter(TITLE))
                .persons(Integer.parseInt(request.getParameter(PERSONS)))
                .price(Double.parseDouble(request.getParameter(PRICE)))
                .hot(hot)
                .type(request.getParameter(TYPE))
                .hotel(request.getParameter(HOTEL))
                .image(tourService.getById(request.getParameter(ID)).getImage())
                .decodedImage(tourService.getById(request.getParameter(ID)).getDecodedImage())
                .build();
    }
}