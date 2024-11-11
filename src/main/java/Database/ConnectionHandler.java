package main.java.Database;

import javax.management.InstanceAlreadyExistsException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class ConnectionHandler {
    private static ConnectionHandler INSTANCE = null;
    private Connection connection = null;

    private ConnectionHandler() {
    }

    public static ConnectionHandler getInstance() throws SQLException, IOException {
        if (INSTANCE == null){
            INSTANCE = new ConnectionHandler();
            INSTANCE.getProperties();
        }
        return INSTANCE;
    }

    public Connection getConnection() throws IOException, SQLException {
            return connection;
    }
    private void getProperties() throws IOException, SQLException {
        if(connection==null){
            Properties properties = new Properties();
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/DBconfig.properties");
            properties.load(fileInputStream);
            connection = DriverManager.getConnection(properties.getProperty("url"),properties.getProperty("user"),properties.getProperty("password"));
            Statement statement = connection.createStatement();
            String sql = "CREATE DATABASE IF NOT EXISTS miniORM";
            statement.executeUpdate(sql);
        }
    }


}
