package ua.epam.travelagencyms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.actions.ActionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ua.epam.travelagencyms.controller.actions.constants.Pages.ERROR_PAGE;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.ACTION;
@MultipartConfig
public class Controller extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private static final ActionFactory ACTION_FACTORY = ActionFactory.getActionFactory();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(process(request, response)).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(process(request, response));
    }

    private String process(HttpServletRequest request, HttpServletResponse response) {
        Action action = ACTION_FACTORY.createAction(request.getParameter(ACTION));
        String path = ERROR_PAGE;
        try {
            path = action.execute(request, response);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return path;
    }
}
