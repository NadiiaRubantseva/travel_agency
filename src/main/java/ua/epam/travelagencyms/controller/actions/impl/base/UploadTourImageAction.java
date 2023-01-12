package ua.epam.travelagencyms.controller.actions.impl.base;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.TourService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.io.InputStream;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.*;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.*;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.EDIT_TOUR_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_DELETE;
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
        return EDIT_TOUR_PAGE;
    }

    private String executePost(HttpServletRequest request) throws ServiceException {
        try {
            Part part = request.getPart(IMAGE);
            try (InputStream inputStream = part.getInputStream()) {
                byte[] newImage = inputStream.readAllBytes();
                String tourId = request.getParameter(ID);
                boolean result = tourService.createImage(newImage, tourId);
                request.getSession().setAttribute("upload-result", result);
             }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getSession().setAttribute(MESSAGE, SUCCEED_DELETE);
        return getActionToRedirect(EDIT_TOUR_ACTION);
    }
}
