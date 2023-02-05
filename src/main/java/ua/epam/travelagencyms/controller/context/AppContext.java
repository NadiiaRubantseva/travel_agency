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
import ua.epam.travelagencyms.model.services.implementation.LoyaltyProgramService;
import ua.epam.travelagencyms.utils.EmailSender;
import ua.epam.travelagencyms.utils.PdfUtil;

import javax.servlet.ServletContext;
import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

import static ua.epam.travelagencyms.model.dao.constants.DbImplementations.MYSQL;

/**
 * AppContext  class. Contains all required to correct application work objects
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
@Getter
public class AppContext {
    private static final Logger logger = LoggerFactory.getLogger(AppContext.class);
    private static AppContext appContext;
    private final UserService userService;
    private final TourService tourService;
    private final OrderService orderService;
    private final LoyaltyProgramService loyaltyProgramService;
    private final EmailSender emailSender;
    private final PdfUtil pdfUtil;


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
        loyaltyProgramService = serviceFactory.getLoyaltyProgramService();
    }


    /**
     * @return instance of AppContext
     */
    public static AppContext getAppContext() {
        return appContext;
    }

    /**
     * Creates instance of AppContext to use in Actions. Configure all required classes. Loads properties
     * @param servletContext - to use relative address in classes
     * @param propertiesFile - to configure DataSource, EmailSender and Captcha
     */
    public static void createAppContext(ServletContext servletContext, String propertiesFile) {
        appContext = new AppContext(servletContext, propertiesFile);
    }

    private static Properties getProperties(String propertiesFile) {
        Properties properties = new Properties();
        try (InputStream resource = AppContext.class.getClassLoader().getResourceAsStream(propertiesFile)){
            properties.load(resource);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return properties;
    }
}