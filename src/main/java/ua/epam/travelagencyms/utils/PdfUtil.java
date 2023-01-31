package ua.epam.travelagencyms.utils;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import com.itextpdf.kernel.colors.*;
import com.itextpdf.kernel.font.*;
import com.itextpdf.kernel.geom.*;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.borders.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.*;
import ua.epam.travelagencyms.dto.TourDTO;
import ua.epam.travelagencyms.dto.UserDTO;

import java.io.ByteArrayOutputStream;
import java.util.ResourceBundle;
import java.util.stream.Stream;
import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class PdfUtil {
    private static final Logger logger = LoggerFactory.getLogger(PdfUtil.class);
    private final ServletContext servletContext;
    private static final String FONT = "css/fonts/arial.ttf";
    private static final Color LIGHT_GREY = new DeviceRgb(220, 220, 220);
    private static final int TITLE_SIZE = 20;
    private static final Paragraph LINE_SEPARATOR = new Paragraph(new Text("\n"));
    private static final String USER_TITLE = "users";
    private static final String TOUR_TITLE = "tours";
    private static final String[] USER_CELLS = new String[]{"id", "email", "name", "surname", "role"};
    private static final String[] TOUR_CELLS = new String[]{"id", "title", "persons", "price", "type"};


    public PdfUtil(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

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
        Table table = new Table(new float[]{4, 12, 6, 6, 6});
        table.setWidth(UnitValue.createPercentValue(100));
        addTableHeader(table, USER_CELLS, resourceBundle);
        addUserTableRows(table, users);
        return table;
    }

    private Table getTourTable(List<TourDTO> tours, ResourceBundle resourceBundle) {
        Table table = new Table(new float[]{4, 12, 6, 6, 6});
        table.setWidth(UnitValue.createPercentValue(100));
        addTableHeader(table, TOUR_CELLS, resourceBundle);
        addTourTableRows(table, tours);
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

    private void addUserTableRows(Table table, List<UserDTO> users) {
        users.forEach(user ->
                {
                    table.addCell(String.valueOf(user.getId()));
                    table.addCell(user.getEmail());
                    table.addCell(user.getName());
                    table.addCell(user.getSurname());
                    table.addCell(user.getRole());
                }
        );
    }

    private void addTourTableRows(Table table, List<TourDTO> tours) {
        tours.forEach(tour ->
                {
                    table.addCell(String.valueOf(tour.getId()));
                    table.addCell(tour.getTitle());
                    table.addCell(String.valueOf(tour.getPersons()));
                    table.addCell(String.valueOf(tour.getPrice()));
                    table.addCell(tour.getType());
                }
        );
    }

    private PdfFont getPdfFont() {
        try {
            URL resource = servletContext.getResource(FONT);
            System.out.println("url is -> " + resource);
            String fontUrl = resource.getFile();
            return PdfFontFactory.createFont(fontUrl);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

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