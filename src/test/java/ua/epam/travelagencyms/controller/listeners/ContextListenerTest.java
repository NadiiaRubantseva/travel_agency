package ua.epam.travelagencyms.controller.listeners;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.controller.context.AppContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ContextListenerTest {
    private static final ServletContextEvent sce = mock(ServletContextEvent.class);
    private static final ServletContext servletContext = mock(ServletContext.class);

    @Test
    void testContextInitialized() {
        when(sce.getServletContext()).thenReturn(servletContext);
        new ContextListener().contextInitialized(sce);
        assertNotNull(AppContext.getAppContext());
    }
}