package ua.epam.travelagencyms.controller.actions;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import ua.epam.travelagencyms.controller.actions.impl.base.DefaultAction;
import ua.epam.travelagencyms.controller.actions.impl.base.SignInAction;
import ua.epam.travelagencyms.controller.context.AppContext;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static ua.epam.travelagencyms.controller.actions.ActionFactory.getActionFactory;
import static ua.epam.travelagencyms.controller.actions.constants.ActionNames.SIGN_IN_ACTION;

class ActionFactoryTest {
    private final AppContext appContext = mock(AppContext.class);

    @Test
    void testCreateAction() {
        try (MockedStatic<AppContext> mocked = mockStatic(AppContext.class)) {
            mocked.when(AppContext::getAppContext).thenReturn(appContext);
            ActionFactory actionFactory = getActionFactory();
            Action action = actionFactory.createAction(SIGN_IN_ACTION);
            assertInstanceOf(SignInAction.class, action);
        }
    }

    @Test
    void testDefaultAction() {
        try (MockedStatic<AppContext> mocked = mockStatic(AppContext.class)) {
            mocked.when(AppContext::getAppContext).thenReturn(appContext);
            ActionFactory actionFactory = getActionFactory();
            Action action = actionFactory.createAction("wrongName");
            assertInstanceOf(DefaultAction.class, action);
        }
    }
}