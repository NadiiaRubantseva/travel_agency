package ua.epam.travelagencyms.controller.context;

import org.junit.jupiter.api.Test;

import javax.servlet.ServletContext;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class AppContextTest {
    private static final String PROPERTIES_FILE = "context.properties";
    private final ServletContext SERVLET_CONTEXT = mock(ServletContext.class);

    @Test
    void testRightFile() {
        assertDoesNotThrow(() -> AppContext.createAppContext(SERVLET_CONTEXT, PROPERTIES_FILE));
        AppContext appContext = AppContext.getAppContext();
        assertNotNull(appContext.getUserService());
        assertNotNull(appContext.getTourService());
        assertNotNull(appContext.getOrderService());
        assertNotNull(appContext.getLoyaltyProgramService());
        assertNotNull(appContext.getEmailSender());
        assertNotNull(appContext.getPdfUtil());
    }

    @Test
    void testWrongFile() {
        assertDoesNotThrow(() -> AppContext.createAppContext(SERVLET_CONTEXT, "wrong"));
        AppContext appContext = AppContext.getAppContext();
        assertNotNull(appContext.getUserService());
        assertNotNull(appContext.getTourService());
        assertNotNull(appContext.getOrderService());
        assertNotNull(appContext.getLoyaltyProgramService());
        assertNotNull(appContext.getEmailSender());
        assertNotNull(appContext.getPdfUtil());
    }
}
