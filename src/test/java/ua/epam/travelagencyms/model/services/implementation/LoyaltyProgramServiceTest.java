package ua.epam.travelagencyms.model.services.implementation;

import org.junit.jupiter.api.Test;
import ua.epam.travelagencyms.dto.LoyaltyProgramDTO;
import ua.epam.travelagencyms.exceptions.DAOException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.dao.mysql.MysqlLoyaltyProgramDAO;
import ua.epam.travelagencyms.model.entities.loyaltyProgram.LoyaltyProgram;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static ua.epam.travelagencyms.TestUtils.getTestLoyaltyProgram;
import static ua.epam.travelagencyms.TestUtils.getTestLoyaltyProgramDTO;

class LoyaltyProgramServiceTest {

    private final MysqlLoyaltyProgramDAO loyaltyProgramDAO = mock(MysqlLoyaltyProgramDAO.class);
    private final LoyaltyProgramService loyaltyProgramService = new LoyaltyProgramService(loyaltyProgramDAO);

    @Test
    void testUpdateLoyaltyProgram() throws DAOException {
        doNothing().when(loyaltyProgramDAO).update(isA(LoyaltyProgram.class));
        assertDoesNotThrow(() -> loyaltyProgramService.update(getTestLoyaltyProgramDTO()));
    }

    @Test
    void testSQLErrorEditProfile() throws DAOException {

        Exception exception = new DAOException(new SQLException());
        doThrow(exception).when(loyaltyProgramDAO).update(isA(LoyaltyProgram.class));
        LoyaltyProgramDTO loyaltyProgramDTO = getTestLoyaltyProgramDTO();
        ServiceException e = assertThrows(ServiceException.class, () -> loyaltyProgramService.update(loyaltyProgramDTO));
        assertEquals(e.getCause(), exception);

    }

    @Test
    void testGetLoyaltyProgram() throws DAOException, ServiceException {
        when(loyaltyProgramDAO.get()).thenReturn(getTestLoyaltyProgram());
        assertEquals(getTestLoyaltyProgramDTO(), loyaltyProgramService.get());
    }

    @Test
    void testSQLErrorGetLoyaltyProgram() throws DAOException {
        when(loyaltyProgramDAO.get()).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, loyaltyProgramService::get);
    }
}