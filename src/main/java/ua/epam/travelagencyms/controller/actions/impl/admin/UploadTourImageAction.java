package ua.epam.travelagencyms.controller.actions.impl.admin;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.TourDTO;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.TourService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.io.InputStream;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.*;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.*;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.*;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_UPDATE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

public class UploadTourImageAction implements Action {
    private final TourService tourService;

    public UploadTourImageAction(AppContext appContext) {
        tourService = appContext.getTourService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        transferTourDTOFromSessionToRequest(request);
        String image = (String) request.getSession().getAttribute(IMAGE);
        if (image != null) {
            request.setAttribute(IMAGE, image);
            request.getSession().removeAttribute(IMAGE);
        }
        return VIEW_TOUR_BY_ADMIN_PAGE;
    }

    private String executePost(HttpServletRequest request) throws ServiceException {
        String id = null;
        try {
            Part part = request.getPart(IMAGE);
            try (InputStream inputStream = part.getInputStream()) {
                byte[] newImage = inputStream.readAllBytes();
                String tourId = request.getParameter(ID);
                boolean result = tourService.createImage(newImage, tourId);
                TourDTO tour = tourService.getById(tourId);
                id = String.valueOf(tour.getId());
                request.getSession().setAttribute("upload-result", result);
                request.getSession().setAttribute(TOUR, tour);
                request.getSession().setAttribute(IMAGE, tourService.getById(String.valueOf(tour.getId())).getDecodedImage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getSession().setAttribute(MESSAGE, SUCCEED_UPDATE);
        request.getSession().setAttribute(CURRENT_PATH, TOUR_ADMIN_PAGE);
        return getActionToRedirect(VIEW_TOUR_ACTION, ID, id);
    }
}
