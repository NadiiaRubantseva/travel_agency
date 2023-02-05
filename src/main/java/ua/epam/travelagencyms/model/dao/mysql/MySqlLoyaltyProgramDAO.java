package ua.epam.travelagencyms.model.dao.mysql;

import ua.epam.travelagencyms.exceptions.DAOException;
import ua.epam.travelagencyms.model.entities.LoyaltyProgram;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static ua.epam.travelagencyms.model.dao.mysql.constants.LoyaltyProgramSQLQueries.GET;
import static ua.epam.travelagencyms.model.dao.mysql.constants.LoyaltyProgramSQLQueries.UPDATE;

public class MySqlLoyaltyProgramDAO {

    private final DataSource dataSource;

    public MySqlLoyaltyProgramDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public void update(LoyaltyProgram loyaltyProgram) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            int k = 0;
            preparedStatement.setInt(++k, loyaltyProgram.getStep());
            preparedStatement.setInt(++k, loyaltyProgram.getDiscount());
            preparedStatement.setInt(++k, loyaltyProgram.getMaxDiscount());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public LoyaltyProgram get() throws DAOException {
        LoyaltyProgram loyaltyProgram = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    loyaltyProgram = LoyaltyProgram.builder()
                            .step(resultSet.getInt(1))
                            .discount(resultSet.getInt(2))
                            .maxDiscount(resultSet.getInt(3))
                            .build();
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        if (loyaltyProgram == null) throw new DAOException("Loyalty Program: null");
        return loyaltyProgram;
    }
}
