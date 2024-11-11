package main.java;

import main.java.Configuration.EntityManager;
import main.java.Configuration.EntityManagerImpl;
import main.java.Database.ConnectionHandler;

import java.io.IOException;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {

        EntityManager entityManager = new EntityManagerImpl();
        Connection connection =  ConnectionHandler.getInstance().getConnection();
    }
}
