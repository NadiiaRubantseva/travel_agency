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
import static ua.epam.travelagencyms.model.dao.mysql.constants.UserSQLQueries.NUMBER_OF_RECORDS;

public class MysqlUserDAO implements UserDAO {

    private final DataSource dataSource;

    public MysqlUserDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

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

    @Override
    public void setUserRole(String userEmail, Role role) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_ROLE)) {
            int k = 0;
            preparedStatement.setInt(++k, role.getValue());
            preparedStatement.setString(++k, userEmail);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

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

    @Override
    public boolean isEmailConfirmed(String email) throws DAOException {
        int res = 0;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("select email_verified from db.user where email='" + email + "'")) {
                if (resultSet.next()) {
                    res = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return res == 1;
    }

    @Override
    public void setVerificationCode(String email, String code) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("update user set verification_code='" + code + "' where email='" + email + "'");
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public String getVerificationCode(String email) throws DAOException {
        String code = "";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("select verification_code from db.user where email='" + email + "'")) {
                if (resultSet.next()) {
                    code = resultSet.getString(1);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return code;
    }

    @Override
    public void setEmailVerified(String email) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("update user set email_verified=1 where email='" + email + "'");
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

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

    @Override
    public byte isBlocked(long id) throws DAOException {
        byte res = 0;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("select blocked from db.user where id='" + id + "'")) {
                if (resultSet.next()) {
                    res = resultSet.getByte(1);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return res;
    }

    @Override
    public void setStatus(String email, byte statusId) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_STATUS)) {
            int k = 0;
            preparedStatement.setByte(++k, statusId);
            preparedStatement.setString(++k, email);
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
                .isBlocked(resultSet.getInt("blocked") != 0)
                .avatar(resultSet.getBytes(AVATAR))
                .build();
    }
}