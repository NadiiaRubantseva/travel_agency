package ua.epam.travelagencyms.model.connection;

import com.zaxxer.hikari.*;
import javax.sql.DataSource;
import java.util.Properties;

import static ua.epam.travelagencyms.model.connection.ConnectionConstants.*;

/**
 * Class to configure and obtain HikariDataSource. Use it to connect to database
 *
 * @author Nadiia Rubantseva
 * @version 1.0
 */
public class MyDataSource {
    private static DataSource dataSource;

    public static synchronized DataSource getDataSource(Properties properties) {
        if (dataSource == null) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(properties.getProperty(URL_PROPERTY));
            config.setUsername(properties.getProperty(DB_USER));
            config.setPassword(properties.getProperty(DB_PASSWORD));
            config.setDriverClassName(properties.getProperty(DRIVER));
            config.setDataSourceProperties(properties);
            dataSource = new HikariDataSource(config);
        }
        return dataSource;
    }

    private MyDataSource() {
    }
}