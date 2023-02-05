package ua.epam.travelagencyms.controller.actions.impl.admin;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.entities.LoyaltyProgram;
import ua.epam.travelagencyms.model.services.implementation.LoyaltyProgramService;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.*;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.SEARCH_TOUR_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.*;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.SUCCEED_UPDATE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;


public class EditLoyaltyProgramAction implements Action {
    private final LoyaltyProgramService loyaltyProgramService;
    /**
     * @param appContext contains LoyaltyProgramService instance to use in action
     */
    public EditLoyaltyProgramAction(AppContext appContext) {
        loyaltyProgramService = appContext.getLoyaltyProgramService();
    }


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);

        return "viewLoyaltyProgram.jsp";
    }

    private String executePost(HttpServletRequest request) throws ServiceException {
        String path = "viewLoyaltyProgram.jsp";
        LoyaltyProgram loyaltyProgram = LoyaltyProgram.builder()
                .step(Integer.parseInt(request.getParameter("step")))
                .discount(Integer.parseInt(request.getParameter("discount")))
                .maxDiscount(Integer.parseInt(request.getParameter("max-discount")))
                .build();
        try {
            loyaltyProgramService.update(loyaltyProgram);
            request.getSession().setAttribute(MESSAGE, SUCCEED_UPDATE);
        } catch (ServiceException e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
            path = INDEX_PAGE;
        }
        request.getSession().setAttribute(CURRENT_PATH, path);
        return getActionToRedirect("search-loyalty-program");
    }
}