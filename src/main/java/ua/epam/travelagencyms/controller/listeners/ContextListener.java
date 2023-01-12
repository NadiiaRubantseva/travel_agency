package ua.epam.travelagencyms.controller.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.epam.travelagencyms.controller.context.AppContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(ContextListener.class);
    private static final String PROPERTIES_FILE = "context.properties";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        AppContext.createAppContext(sce.getServletContext(), PROPERTIES_FILE);
        logger.info("AppContext is set");
    }
}
