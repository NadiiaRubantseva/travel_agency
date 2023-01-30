package ua.epam.travelagencyms.controller.context;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.epam.travelagencyms.model.connection.MyDataSource;
import ua.epam.travelagencyms.model.dao.DAOFactory;
import ua.epam.travelagencyms.model.services.OrderService;
import ua.epam.travelagencyms.model.services.ServiceFactory;
import ua.epam.travelagencyms.model.services.TourService;
import ua.epam.travelagencyms.model.services.UserService;
import ua.epam.travelagencyms.utils.EmailSender;
import ua.epam.travelagencyms.utils.PdfUtil;

import javax.servlet.ServletContext;
import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

import static ua.epam.travelagencyms.model.dao.constants.DbImplementations.MYSQL;

public class AppContext {
    private static final Logger logger = LoggerFactory.getLogger(AppContext.class);
    private static AppContext appContext;
    @Getter private final UserService userService;
    @Getter private final TourService tourService;
    @Getter private final OrderService orderService;
    @Getter private final EmailSender emailSender;
    @Getter private final PdfUtil pdfUtil;


    private AppContext(ServletContext servletContext, String propertiesFile) {
        pdfUtil = new PdfUtil(servletContext);
        Properties properties = getProperties(propertiesFile);
        emailSender = new EmailSender(properties);
        DataSource dataSource = MyDataSource.getDataSource(properties);
        DAOFactory daoFactory = DAOFactory.getInstance(MYSQL, dataSource);
        ServiceFactory serviceFactory = ServiceFactory.getInstance(daoFactory);
        userService = serviceFactory.getUserService();
        tourService = serviceFactory.getTourService();
        orderService = serviceFactory.getOrderService();
    }

    public static AppContext getAppContext() {
        return appContext;
    }

    public static void createAppContext(ServletContext servletContext, String propertiesFile) {
        appContext = new AppContext(servletContext, propertiesFile);
    }

    public static Properties getProperties(String propertiesFile) {
        Properties properties = new Properties();
        try (InputStream resource = AppContext.class.getClassLoader().getResourceAsStream(propertiesFile)){
            properties.load(resource);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return properties;
    }
}