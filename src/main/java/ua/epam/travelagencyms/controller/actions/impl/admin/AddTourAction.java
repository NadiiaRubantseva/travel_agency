package ua.epam.travelagencyms.controller.actions.impl.admin;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.TourDTO;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.TourService;
import ua.epam.travelagencyms.utils.ImageEncoder;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import static ua.epam.travelagencyms.controller.actions.ActionUtil.*;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.*;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.*;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.*;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

public class AddTourAction implements Action {
    private final TourService tourService;

    public AddTourAction(AppContext appContext) {
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
        return getPath(request);
    }

    private String executePost(HttpServletRequest request) throws ServiceException {
        String path = VIEW_TOUR_BY_ADMIN_PAGE;
        TourDTO tour = getTourDTO(request);
        request.getSession().setAttribute(TOUR, tour);
        try {
            tourService.add(tour);
            tour.setId(Long.parseLong(String.valueOf(tourService.getByTitle(tour.getTitle()).getId())));
            request.getSession().setAttribute(MESSAGE, SUCCEED_ADDED);
        } catch (Exception e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
            path = ADD_TOUR_PAGE;
        }
        request.getSession().setAttribute(CURRENT_PATH, path);
        return getActionToRedirect(ADD_TOUR_ACTION);
    }

    private TourDTO getTourDTO(HttpServletRequest request) {
        return TourDTO.builder()
                .title(request.getParameter(TITLE))
                .persons(Integer.parseInt(request.getParameter(PERSONS)))
                .price(Double.parseDouble(request.getParameter(PRICE)))
                .hot(request.getParameter(HOT) == null ? null : HOT)
                .type(request.getParameter(TYPE))
                .hotel(request.getParameter(HOTEL))
                .image(ImageEncoder.getImage(request))
                .decodedImage(ImageEncoder.encode(ImageEncoder.getImage(request)))
                .build();
    }

}