package ua.epam.travelagencyms.controller.actions.impl.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.TourDTO;
import ua.epam.travelagencyms.dto.UserDTO;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.services.TourService;
import ua.epam.travelagencyms.utils.PdfUtil;
import ua.epam.travelagencyms.utils.query.QueryBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import static ua.epam.travelagencyms.controller.actions.ActionUtil.getActionToRedirect;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.VIEW_USERS_ACTION;
import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.DESCENDING_ORDER;
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;
import static ua.epam.travelagencyms.utils.QueryBuilderUtil.userQueryBuilder;


public class ToursToPdfAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(ToursToPdfAction.class);
    private final TourService tourService;
    private final PdfUtil pdfUtil;

    public ToursToPdfAction(AppContext appContext) {
        tourService = appContext.getTourService();
        pdfUtil = appContext.getPdfUtil();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        QueryBuilder queryBuilder = getQueryBuilder(request);
        List<TourDTO> tours = tourService.getSortedTours(queryBuilder.getQuery());
        String locale = (String) request.getSession().getAttribute(LOCALE);
        ByteArrayOutputStream toursPdf = pdfUtil.createToursPdf(tours, locale);
        setResponse(response, toursPdf);
        return getActionToRedirect(VIEW_USERS_ACTION);
    }

    private QueryBuilder getQueryBuilder(HttpServletRequest request) {
        String zero = "0";
        String max = String.valueOf(Integer.MAX_VALUE);
        return userQueryBuilder()
                .setTypeFilter(request.getParameter(TYPE))
                .setHotelFilter(request.getParameter(HOTEL))
                .setPriceFilter(request.getParameter(MIN_PRICE), request.getParameter(MAX_PRICE))
                .setPersonsFilter(request.getParameter(PERSONS))
                .setSortField(HOT)
                .setOrder(DESCENDING_ORDER)
                .setSortField(request.getParameter(SORT))
                .setOrder(request.getParameter(ORDER))
                .setLimits(zero, max);
    }

    private void setResponse(HttpServletResponse response, ByteArrayOutputStream output) {
        response.setContentType("application/pdf");
        response.setContentLength(output.size());
        response.setHeader("Content-Disposition", "attachment; filename=\"tours.pdf\"");
        try (OutputStream outputStream = response.getOutputStream()) {
            output.writeTo(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}