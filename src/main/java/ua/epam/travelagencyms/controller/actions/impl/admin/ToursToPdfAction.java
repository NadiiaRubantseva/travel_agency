package ua.epam.travelagencyms.controller.actions.impl.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.epam.travelagencyms.controller.actions.Action;
import ua.epam.travelagencyms.controller.context.AppContext;
import ua.epam.travelagencyms.dto.TourDTO;
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
import static ua.epam.travelagencyms.controller.actions.constants.Parameters.*;
import static ua.epam.travelagencyms.utils.QueryBuilderUtil.tourQueryBuilder;

/**
 * This is ToursToPdfAction class. Accessible by admin. Allows to download list of all users that match demands
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */

public class ToursToPdfAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(ToursToPdfAction.class);
    private final TourService tourService;
    private final PdfUtil pdfUtil;

    /**
     * @param appContext contains TourService and PdfUtil instances to use in action
     */
    public ToursToPdfAction(AppContext appContext) {
        tourService = appContext.getTourService();
        pdfUtil = appContext.getPdfUtil();
    }

    /**
     * Builds required query for service, sets tours list in response to download. Checks for locale to set up
     * locale for pdf document
     *
     * @param request  to get queries parameters
     * @param response to set tours list there
     */
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
        return tourQueryBuilder()
                .setTypeFilter(request.getParameter(TYPE))
                .setHotelFilter(request.getParameter(HOTEL))
                .setPriceFilter(request.getParameter(MIN_PRICE), request.getParameter(MAX_PRICE))
                .setPersonsFilter(request.getParameter(PERSONS))
                .setSortField(request.getParameter(SORT))
                .setOrder(request.getParameter(ORDER))
                .setLimits(zero, max);
    }

    /**
     * Sets tours list in response to download. Configure response to download pdf document
     *
     * @param response to set tours list there
     * @param output   - output stream that contains pdf document
     */
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