package ua.epam.travelagencyms.model.dao.mysql;

import ua.epam.travelagencyms.model.dao.UserDAO;
import ua.epam.travelagencyms.exceptions.DAOException;
import ua.epam.travelagencyms.model.dao.mysql.constants.SQLFields;
import ua.epam.travelagencyms.model.entities.user.Role;
import ua.epam.travelagencyms.model.entities.user.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ua.epam.travelagencyms.model.dao.mysql.constants.SQLFields.*;
import static ua.epam.travelagencyms.model.dao.mysql.constants.UserSQLQueries.*;

/**
 * User DAO class for My SQL database. Match tables 'user' and 'user_has_event' in database.
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class MysqlUserDAO implements UserDAO {

    /**
     * An instance of datasource to provide connection to database
     */
    private final DataSource dataSource;

    public MysqlUserDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Inserts new user to database
     *
     * @param user - id will be generated by database. Email, name and surname should be not null
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public void add(User user) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER)) {
            int k = 0;
            preparedStatement.setString(++k, user.getEmail());
            preparedStatement.setString(++k, user.getPassword());
            preparedStatement.setString(++k, user.getName());
            preparedStatement.setString(++k, user.getSurname());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Obtains instance of User from database by id
     *
     * @param id - value of id field in database
     * @return Optional.ofNullable - user is null if there is no user
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public Optional<User> getById(long id) throws DAOException {
        User user = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_ID)) {
            int k = 0;
            preparedStatement.setLong(++k, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = createUser(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return Optional.ofNullable(user);
    }

    /**
     * Obtains instance of User from database by email
     *
     * @param email - value of email field in database
     * @return Optional.ofNullable - user is null if there is no user
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public Optional<User> getByEmail(String email) throws DAOException {
        User user = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_EMAIL)) {
            int k = 0;
            preparedStatement.setString(++k, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = createUser(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return Optional.ofNullable(user);
    }

    /**
     * Obtains list of all users from database
     *
     * @return users list
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public List<User> getAll() throws DAOException {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USERS)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    users.add(createUser(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return users;
    }

    /**
     * Updates user
     *
     * @param user should contain id, email, name and surname to be updated
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public void update(User user) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER)) {
            int k = 0;
            preparedStatement.setString(++k, user.getEmail());
            preparedStatement.setString(++k, user.getName());
            preparedStatement.setString(++k, user.getSurname());
            preparedStatement.setLong(++k, user.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Deletes user record in database
     *
     * @param id - value of id field in database
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public void delete(long id) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {
            int k = 0;
            preparedStatement.setLong(++k, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }


    /**
     * Updates user's password
     *
     * @param user should contain user id and password
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public void updatePassword(User user) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PASSWORD)) {
            int k = 0;
            preparedStatement.setString(++k, user.getPassword());
            preparedStatement.setLong(++k, user.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Sets new user's role
     *
     * @param userId - value of id field in database
     * @param role   - new role for user
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public void setUserRole(long userId, Role role) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_ROLE)) {
            int k = 0;
            preparedStatement.setInt(++k, role.getValue());
            preparedStatement.setLong(++k, userId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Obtains sorted and limited list of users from database
     *
     * @param query should contain filters (where), order (order field and type), limits for pagination
     * @return users list that matches demands. Will be empty if there are no users
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public List<User> getSorted(String query) throws DAOException {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(String.format(GET_SORTED, query))) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    users.add(createUser(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return users;
    }

    /**
     * Obtains number of all records matching filter
     *
     * @param filter should contain 'where' to specify query
     * @return number of records
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public int getNumberOfRecords(String filter) throws DAOException {
        int numberOfRecords = 0;
        String query = String.format(GET_NUMBER_OF_RECORDS, filter);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    numberOfRecords = resultSet.getInt(NUMBER_OF_RECORDS);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return numberOfRecords;
    }

    /**
     * Checks if user email is confirmed in database
     *
     * @param id - value of id field in database
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public boolean isEmailVerified(long id) throws DAOException {
        int res = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(IS_EMAIL_VERIFIED)) {
            int k = 0;
            preparedStatement.setLong(++k, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    res = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return res == 1;
    }

    /**
     * Sets verification code in database
     *
     * @param id   - value of id field in database
     * @param code - value of verification_code field in database
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public void setVerificationCode(long id, String code) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_VERIFICATION_CODE)) {
            int k = 0;
            preparedStatement.setString(++k, code);
            preparedStatement.setLong(++k, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Retrieves verification code from database
     *
     * @param id - value of id field in database
     * @return verificationCode - verification code value from database
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public String getVerificationCode(long id) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_VERIFICATION_CODE)) {
            int k = 0;
            preparedStatement.setLong(++k, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString(1);
                } else {
                    throw new DAOException("error retrieving verification code");
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Sets 1 in verification_code field in database since email is verified
     *
     * @param id - value of id field in database
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public void setEmailVerified(long id) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_EMAIL_VERIFIED)) {
            int k = 0;
            preparedStatement.setLong(++k, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Sets user's avatar in database
     *
     * @param userId - value of id field in database
     * @param avatar - new image of user
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public void setAvatar(long userId, byte[] avatar) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_AVATAR)) {
            int k = 0;
            preparedStatement.setBytes(++k, avatar);
            preparedStatement.setLong(++k, userId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Checks if user is blocked
     *
     * @param id user's id
     * @return 1 if blocked, 0 if not.
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public boolean isBlocked(long id) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(IS_USER_BLOCKED)) {
            int k = 0;
            preparedStatement.setLong(++k, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    byte isBlocked = resultSet.getByte(1);
                    return isBlocked == 1;
                } else {
                    throw new DAOException("error retrieving blocked value from database");
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Sets new user's role
     *
     * @param userId   - value of id field in database
     * @param statusId - new status for user
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public void setStatus(long userId, byte statusId) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_STATUS)) {
            int k = 0;
            preparedStatement.setByte(++k, statusId);
            preparedStatement.setLong(++k, userId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getLong(ID))
                .email(resultSet.getString(EMAIL))
                .password(resultSet.getString(PASSWORD))
                .name(resultSet.getString(NAME))
                .surname(resultSet.getString(SURNAME))
                .roleId(resultSet.getInt(SQLFields.ROLE_ID))
                .isBlocked(resultSet.getInt(BLOCKED) == 1)
                .avatar(resultSet.getBytes(AVATAR))
                .build();
    }
}