package ua.epam.travelagencyms.model.services.implementation;

import lombok.RequiredArgsConstructor;
import ua.epam.travelagencyms.exceptions.DAOException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.dao.mysql.MySqlLoyaltyProgramDAO;
import ua.epam.travelagencyms.model.entities.LoyaltyProgram;

@RequiredArgsConstructor
public class LoyaltyProgramService {

    private final MySqlLoyaltyProgramDAO loyaltyProgramDAO;

    public void update(LoyaltyProgram lp) throws ServiceException {
        try {
            loyaltyProgramDAO.update(lp);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public LoyaltyProgram get() throws ServiceException {
        try {
            return loyaltyProgramDAO.get();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
