package ua.epam.travelagencyms.model.dao.mysql;

import ua.epam.travelagencyms.exceptions.DAOException;
import ua.epam.travelagencyms.model.dao.TourDAO;
import ua.epam.travelagencyms.model.entities.tour.Tour;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ua.epam.travelagencyms.model.dao.mysql.constants.SQLFields.*;
import static ua.epam.travelagencyms.model.dao.mysql.constants.SQLFields.NUMBER_OF_RECORDS;
import static ua.epam.travelagencyms.model.dao.mysql.constants.TourSQLQueries.*;

public class MysqlTourDAO implements TourDAO {

    private final DataSource dataSource;

    public MysqlTourDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Tour tour) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_TOUR)) {
            int k = 0;
            preparedStatement.setString(++k, tour.getTitle());
            preparedStatement.setInt(++k, tour.getPersons());
            preparedStatement.setDouble(++k, tour.getPrice());
            preparedStatement.setByte(++k, tour.getHot());
            preparedStatement.setInt(++k, tour.getTypeId());
            preparedStatement.setInt(++k, tour.getHotelId());
            preparedStatement.setBytes(++k, tour.getImageContent());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Optional<Tour> getById(long id) throws DAOException {
        Tour tour = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_TOUR_BY_ID)) {
            int k = 0;
            preparedStatement.setLong(++k, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    tour = createTour(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return Optional.ofNullable(tour);
    }


    @Override
    public Optional<Tour> getByTitle(String title) throws DAOException {
        Tour tour = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_TOUR_BY_TITLE)) {
            int k = 0;
            preparedStatement.setString(++k, title);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    tour = createTour(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return Optional.ofNullable(tour);
    }

    @Override
    public List<Tour> getAll() throws DAOException {
        List<Tour> tours = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_TOURS)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    tours.add(createTour(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return tours;
    }

    @Override
    public void update(Tour tour) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TOUR)) {
            int k = 0;
            preparedStatement.setString(++k, tour.getTitle());
            preparedStatement.setInt(++k, tour.getPersons());
            preparedStatement.setDouble(++k, tour.getPrice());
            preparedStatement.setByte(++k, tour.getHot());
            preparedStatement.setInt(++k, tour.getTypeId());
            preparedStatement.setInt(++k, tour.getHotelId());
            preparedStatement.setBytes(++k, tour.getImageContent());
            preparedStatement.setLong(++k, tour.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(long id) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TOUR)) {
            int k = 0;
            preparedStatement.setLong(++k, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }


    @Override
    public void updateTitle(Tour tour) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TITLE)) {
            int k = 0;
            preparedStatement.setString(++k, tour.getTitle());
            preparedStatement.setLong(++k, tour.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Tour> getSorted(String query) throws DAOException {
        List<Tour> tours = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(String.format(GET_SORTED, query))) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    tours.add(createTour(resultSet));
                }
            }
        }catch (SQLException e) {
            throw new DAOException(e);
        }
        return tours;
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
        }catch (SQLException e) {
            throw new DAOException(e);
        }
        return numberOfRecords;
    }

    @Override
    public boolean createImageContent(byte[] image, int tourId) throws DAOException {
        int rows = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_IMAGE_CONTENT)) {
            preparedStatement.setBytes(1, image);
            preparedStatement.setLong(2, tourId);
            rows = preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new DAOException(e);
        }
        return rows == 1;
    }

    @Override
    public byte[] getImage(long id) throws DAOException {
        byte[] image = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_IMAGE_BY_ID)) {
            int k = 0;
            preparedStatement.setLong(++k, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    image = resultSet.getBytes(IMAGE);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return image;
    }


    private Tour createTour(ResultSet resultSet) throws SQLException {
        return Tour.builder()
                .id(resultSet.getInt(ID))
                .title(resultSet.getString(TITLE))
                .persons(resultSet.getInt(PERSONS))
                .price(resultSet.getDouble(PRICE))
                .hot(resultSet.getByte(HOT))
                .typeId(resultSet.getInt(TYPE_ID))
                .hotelId(resultSet.getInt(HOTEL_ID))
                .imageContent(resultSet.getBytes(IMAGE))
                .build();
    }


}
