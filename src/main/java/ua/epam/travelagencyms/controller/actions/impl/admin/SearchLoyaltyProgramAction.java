package ua.epam.travelagencyms.controller.actions.impl.admin;

import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.implementation.LoyaltyProgramService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.transferStringFromSessionToRequest;
import static ua.epam.travelagencyms.controller.actions.constants.Pages.*;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;


public class SearchLoyaltyProgramAction implements Action {
    private final LoyaltyProgramService loyaltyProgramService;

    public SearchLoyaltyProgramAction(AppContext appContext) {
        loyaltyProgramService = appContext.getLoyaltyProgramService();
    }


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        transferStringFromSessionToRequest(request, MESSAGE);


        String path = "viewLoyaltyProgram.jsp";
        String purpose = request.getParameter("purpose");
        if (purpose != null && purpose.equals("edit")) {
            path = "editLoyaltyProgram.jsp"; }
        try {
            request.setAttribute("loyaltyProgram", loyaltyProgramService.get());
        } catch (ServiceException e) {
            request.setAttribute(ERROR, e.getMessage());
            path = INDEX_PAGE;
        }
        return path;
    }
}