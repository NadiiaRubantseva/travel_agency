package ua.epam.travelagencyms.controller.actions;

import ua.epam.travelagencyms.dto.TourDTO;
import ua.epam.travelagencyms.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;

import java.util.StringJoiner;

import static ua.epam.travelagencyms.controller.actions.constants.Pages.CONTROLLER_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;

public class ActionUtil {

    public static boolean isPostMethod(HttpServletRequest request) {
        return request.getMethod().equals("POST");
    }

    public static void transferStringFromSessionToRequest(HttpServletRequest request, String attributeName) {
        String attributeValue = (String) request.getSession().getAttribute(attributeName);
        if (attributeValue != null) {
            request.setAttribute(attributeName, attributeValue);
            request.getSession().removeAttribute(attributeName);
        }
    }

    public static void transferUserDTOFromSessionToRequest(HttpServletRequest request) {
        UserDTO user = (UserDTO) request.getSession().getAttribute(USER);
        if (user != null) {
            request.setAttribute(USER, user);
            request.getSession().removeAttribute(USER);
        }
    }

    public static void transferTourDTOFromSessionToRequest(HttpServletRequest request) {
        TourDTO tour = (TourDTO) request.getSession().getAttribute(TOUR);
        if (tour != null) {
            request.setAttribute(TOUR, tour);
            request.getSession().removeAttribute(TOUR);
        }
    }

    public static String getPath(HttpServletRequest request){
        return (String) request.getSession().getAttribute(CURRENT_PATH);
    }

    public static String getActionToRedirect(String action, String... parameters) {
        String base = CONTROLLER_PAGE + "?" + ACTION + "=" + action;
        StringJoiner stringJoiner = new StringJoiner("&", "&", "");
        for (int i = 0; i < parameters.length; i+=2) {
            stringJoiner.add(parameters[i] + "=" + parameters[i + 1]);
        }
        return base + (parameters.length > 0 ? stringJoiner : "");
    }

    public static String getURL(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        String requestURL = request.getRequestURL().toString();
        return requestURL.replace(servletPath, "");
    }
}
