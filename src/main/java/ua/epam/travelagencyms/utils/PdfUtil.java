package ua.epam.travelagencyms.utils;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.epam.travelagencyms.dto.OrderDTO;
import ua.epam.travelagencyms.dto.TourDTO;
import ua.epam.travelagencyms.dto.UserDTO;

import javax.servlet.ServletContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static ua.epam.travelagencyms.controller.actions.constants.ParameterValues.*;
import static ua.epam.travelagencyms.utils.ResourceBundleUtil.getValue;

/**
 * Create required pdf docs with itext pdf library
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class PdfUtil {
    private static final Logger logger = LoggerFactory.getLogger(PdfUtil.class);
    private final ServletContext servletContext;

    /**
     * Use this font fo cyrillic
     */
    private static final String FONT = "css/fonts/arial.ttf";
    private static final Color LIGHT_GREY = new DeviceRgb(220, 220, 220);
    private static final int TITLE_SIZE = 20;
    private static final Paragraph LINE_SEPARATOR = new Paragraph(new Text("\n"));
    private static final String USER_TITLE = "users";
    private static final String TOUR_TITLE = "tours";
    private static final String ORDER_TITLE = "orders";
    private static final String[] USER_CELLS = new String[]{"id", "status", "email", "name", "surname", "discount", "role"};
    private static final String[] TOUR_CELLS = new String[]{"numeration", "id", "title", "tour.type", "hotel.type", "hot", "persons", "price.uah"};
    private static final String[] ORDER_CELLS = new String[]{"numeration","date", "id", "status", "user.pdf.id", "user.name", "surname", "tour.pdf.id", "tour.title", "tour.price", "order.pdf.discount", "order.total"};

    /**
     * @param servletContext to properly define way to font file
     */
    public PdfUtil(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    /**
     * Creates pdf document with Users' info. Creates resourceBundle to localize table fields
     *
     * @param users  - list of users to be placed in the document
     * @param locale - for localization purpose
     * @return outputStream to place in response
     */
    public ByteArrayOutputStream createUsersPdf(List<UserDTO> users, String locale) {
        ResourceBundle resourceBundle = getBundle(locale);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Document document = getDocument(output);
        document.add(getTableTitle(resourceBundle.getString(USER_TITLE).toUpperCase()));
        document.add(LINE_SEPARATOR);
        document.add(getUserTable(users, resourceBundle));
        document.close();
        return output;
    }

    /**
     * Creates pdf document with Tours' info. Creates resourceBundle to localize table fields
     *
     * @param tours  - list of tours to be placed in the document
     * @param locale - for localization purpose
     * @return outputStream to place in response
     */
    public ByteArrayOutputStream createToursPdf(List<TourDTO> tours, String locale) {
        ResourceBundle resourceBundle = getBundle(locale);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Document document = getDocument(output);
        document.add(getTableTitle(resourceBundle.getString(TOUR_TITLE).toUpperCase()));
        document.add(LINE_SEPARATOR);
        document.add(getTourTable(tours, resourceBundle));
        document.close();
        return output;
    }

    /**
     * Creates pdf document with Orders' info. Creates resourceBundle to localize table fields
     *
     * @param orders - list of orders to be placed in the document
     * @param locale - for localization purpose
     * @return outputStream to place in response
     */
    public ByteArrayOutputStream createOrdersPdf(List<OrderDTO> orders, String locale) {
        ResourceBundle resourceBundle = getBundle(locale);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Document document = getDocument(output);
        document.add(getTableTitle(resourceBundle.getString(ORDER_TITLE).toUpperCase()));
        document.add(LINE_SEPARATOR);
        document.add(getOrderTable(orders, resourceBundle));
        document.close();
        return output;
    }

    /**
     * Will create document with album orientation and font that supports cyrillic
     *
     * @param output to create Document based on output
     * @return Document
     */
    private Document getDocument(ByteArrayOutputStream output) {
        PdfWriter writer = new PdfWriter(output);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4.rotate());
        PdfFont font = getPdfFont();
        if (font != null) {
            document.setFont(font);
        }
        return document;
    }

    private static Paragraph getTableTitle(String tableTitle) {
        return new Paragraph(new Text(tableTitle))
                .setFontSize(TITLE_SIZE)
                .setTextAlignment(TextAlignment.CENTER);
    }

    private Table getUserTable(List<UserDTO> users, ResourceBundle resourceBundle) {
        Table table = new Table(new float[]{4, 6, 10, 8, 8, 4, 4});
        table.setWidth(UnitValue.createPercentValue(100));
        addTableHeader(table, USER_CELLS, resourceBundle);
        addUserTableRows(table, users, resourceBundle);
        return table;
    }

    private Table getTourTable(List<TourDTO> tours, ResourceBundle resourceBundle) {
        Table table = new Table(new float[]{1,1,4,2,2,2,2,2});
        table.setWidth(UnitValue.createPercentValue(100));
        addTableHeader(table, TOUR_CELLS, resourceBundle);
        addTourTableRows(table, tours, resourceBundle);
        return table;
    }

    private Table getOrderTable(List<OrderDTO> orders, ResourceBundle resourceBundle) {
        Table table = new Table(new float[]{1,1,1,1,1,1,1,1,1,1,1,1});
        table.setWidth(UnitValue.createPercentValue(100));
        addTableHeader(table, ORDER_CELLS, resourceBundle);
        addOrderTableRows(table, orders, resourceBundle);
        return table;
    }

    private void addTableHeader(Table table, String[] cells, ResourceBundle resourceBundle) {
        Stream.of(cells)
                .forEach(columnTitle -> {
                    Cell header = new Cell();
                    header.setBackgroundColor(LIGHT_GREY);
                    header.setBorder(new SolidBorder(1));
                    header.add(new Paragraph(resourceBundle.getString(columnTitle)));
                    table.addCell(header);
                });
    }

    private void addUserTableRows(Table table, List<UserDTO> users, ResourceBundle resourceBundle) {
        users.forEach(user ->
                {
                    table.addCell(String.valueOf(user.getId()));
                    table.addCell(getValue(resourceBundle, user.getIsBlocked()));
                    table.addCell(user.getEmail());
                    table.addCell(user.getName());
                    table.addCell(user.getSurname());
                    table.addCell(user.getDiscount() + "%");
                    table.addCell(getValue(resourceBundle, user.getRole()));
                }
        );
    }

    private void addTourTableRows(Table table, List<TourDTO> tours, ResourceBundle resourceBundle) {
        AtomicInteger iterator = new AtomicInteger();
        tours.forEach(tour ->
                {

                    table.addCell(String.valueOf(iterator.incrementAndGet()));
                    table.addCell(String.valueOf(tour.getId()));
                    table.addCell(tour.getTitle());
                    table.addCell(getValue(resourceBundle, tour.getType()));
                    table.addCell(getValue(resourceBundle, tour.getHotel()));
                    table.addCell(getValue(resourceBundle, tour.getHot().equals(TRUE) ? YES : NO));
                    table.addCell(String.valueOf(tour.getPersons()));
                    table.addCell(String.valueOf(tour.getPrice()));

                }
        );
    }

    private void addOrderTableRows(Table table, List<OrderDTO> orders, ResourceBundle resourceBundle) {
        AtomicInteger iterator = new AtomicInteger();
        orders.forEach(order ->
                {
                    table.addCell(String.valueOf(iterator.incrementAndGet()));
                    table.addCell(String.valueOf(order.getDate()));
                    table.addCell(String.valueOf(order.getId()));
                    table.addCell(getValue(resourceBundle,order.getOrderStatus()));
                    table.addCell(String.valueOf(order.getUserId()));
                    table.addCell(order.getUserName());
                    table.addCell(order.getUserSurname());
                    table.addCell(String.valueOf(order.getTourId()));
                    table.addCell(order.getTourTitle());
                    table.addCell(String.valueOf(order.getTourPrice()));
                    table.addCell(String.valueOf(order.getDiscount()));
                    table.addCell(String.valueOf(order.getTotalCost()));
                }
        );
    }

    /**
     * @return font that support cyrillic or null if not able to load it
     */
    private PdfFont getPdfFont() {
        try {
            URL resource = servletContext.getResource(FONT);
            String fontUrl = resource.getFile();
            return PdfFontFactory.createFont(fontUrl);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    /**
     * Obtains ResourceBundle based on locale. Works for any type - short - 'en', long - 'uk_UA'
     *
     * @param locale to set ResourceBundle
     * @return ResourceBundle
     */
    private ResourceBundle getBundle(String locale) {
        String resources = "resources";
        if (locale.contains("_")) {
            int index = locale.indexOf("_");
            String lang = locale.substring(0, index);
            String country = locale.substring(index + 1);
            return ResourceBundle.getBundle(resources, new Locale(lang, country));
        } else {
            return ResourceBundle.getBundle(resources, new Locale(locale));
        }
    }
}