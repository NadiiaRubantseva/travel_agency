package ua.epam.travelagencyms.controller.listeners;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.epam.travelagencyms.controller.context.AppContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * ContextListener  class.
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class ContextListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(ContextListener.class);

    /** Name of properties file to configure DataSource, EmailSender and Captcha */
    private static final String PROPERTIES_FILE = "context.properties";

    /**
     * creates AppContext and passes ServletContext and properties to initialize all required classes
     * @param sce passed by application
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        AppContext.createAppContext(sce.getServletContext(), PROPERTIES_FILE);
        logger.info("AppContext is set");
    }

    /**
     * closes mysql thread and deregister all drivers
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        AbandonedConnectionCleanupThread.checkedShutdown();
        deregisterDrivers();
    }

    private static void deregisterDrivers() {
        DriverManager.drivers().forEach(driver -> {try {
            DriverManager.deregisterDriver(driver);
        } catch (SQLException e) {
            logger.warn(String.format("Couldn't deregister %s", driver), e);
        }
        });
    }
}
