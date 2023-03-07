package ua.epam.travelagencyms.model.services.implementation;

import lombok.RequiredArgsConstructor;
import ua.epam.travelagencyms.dto.LoyaltyProgramDTO;
import ua.epam.travelagencyms.exceptions.DAOException;
import ua.epam.travelagencyms.exceptions.ServiceException;
import ua.epam.travelagencyms.model.dao.mysql.MysqlLoyaltyProgramDAO;
import ua.epam.travelagencyms.model.entities.loyaltyProgram.LoyaltyProgram;

import static ua.epam.travelagencyms.utils.ConvertorUtil.*;

@RequiredArgsConstructor
public class LoyaltyProgramService {

    private final MysqlLoyaltyProgramDAO loyaltyProgramDAO;

    public void update(LoyaltyProgramDTO lp) throws ServiceException {
//        validateLoyaltyProgram(lp);
         LoyaltyProgram loyaltyProgram = convertDTOToLoyaltyProgram(lp);
        try {
            loyaltyProgramDAO.update(loyaltyProgram);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public LoyaltyProgramDTO get() throws ServiceException {
        try {
            LoyaltyProgram loyaltyProgram = loyaltyProgramDAO.get();
            return convertLoyaltyProgramToDTO(loyaltyProgram);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
