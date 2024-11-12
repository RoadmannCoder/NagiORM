package main.java.Configuration;

import main.java.Annotation.Entity;
import main.java.Annotation.PrimaryKey;
import main.java.Database.ConnectionHandler;
import main.java.Database.LoadProperties;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class EntityManagerImpl implements EntityManager{

    private Connection connection;
    private Map<String,String> databaseDataTypeConverter = new HashMap<>();
    public EntityManagerImpl() throws ClassNotFoundException, SQLException, IOException {
        //Convert Some Java Datatype to Database Datatype Like String to Varchar etc.
        fillDatabaseDataTypeConverter();
        //Open Database Connection
        connection = ConnectionHandler.getInstance().getConnection();
        // Load Tables to Push it to database that have @Entity annotation
        LoadTables();
    }

    @Override
    public void persist(Object object) {

    }
    @Override
    public <T> T find(Class<T> clazz, Object val) {
        return null;
    }

    private void fillDatabaseDataTypeConverter(){
        databaseDataTypeConverter.put("Long","BIGINT(255)");
        databaseDataTypeConverter.put("String","VARCHAR(255)");
    }

    /**
     * loadTables
     * This function navigate to main private methods getEntities() and createTables()
     * function responsible for loading Tables and push it to database
     */
    private void LoadTables() throws ClassNotFoundException, SQLException, IOException {
        List<Class<?>> Entities = getEntities();
        List<String> tables = createTables(Entities);
        pushTablesToDatabase(tables);


    }



    /**
     * getEntities only using Reflection API and File Reader to read classes from Entities Package
     * @return list of class entities that have @Entity annotation
     */
    private List<Class<?>> getEntities() throws ClassNotFoundException {
        List<Class<?>> Entities = new ArrayList<>();
        String packageName = "main.java.Entities";
        String path = packageName.replace('.', '/');
        File directory = new File("src/" + path);
        if(directory.exists()){
            for (String file : directory.list()) {
                if (file.endsWith(".java")) {
                    String className = packageName + "." + file.replace(".java", "");
                    Class<?> clazz = Class.forName(className);
                    if (clazz.isAnnotationPresent(Entity.class)) {
                        Entities.add(clazz);
                        System.out.println("Found @Entity class: " + clazz.getName());
                    }
                }
            }
        }
        return Entities;
    }


    /**
     * create_table
     * @param Entities of classes that have annotation @Entity in Entities package to create a table of each class
     * @return list of tables that will be pushed in database like this structure for example
     * CREATE TABLE Person(
     * id  BIGINT(255) NOT NULL AUTO_INCREMENT,
     * firstName  VARCHAR(255),
     * lastName  VARCHAR(255),
     * PRIMARY KEY (id));
     */
    private List<String> createTables(List<Class<?>> Entities) throws ClassNotFoundException {
        List<String> tables = new ArrayList<>();
        for(Class<?> entity:Entities){
            String tableName = "CREATE TABLE IF NOT EXISTS "+entity.getSimpleName()+"( \n";
            String columns = getTableColumnfromClasses(entity);
            tables.add(tableName+columns);
        }
        return tables;
    }


    private String getTableColumnfromClasses(Class<?> entity) {
        StringJoiner columns = new StringJoiner(", \n");
        String primaryKey = "";
        Field[] fields = entity.getDeclaredFields();
        for(Field field:fields){
            if(field.isAnnotationPresent(PrimaryKey.class)){
                primaryKey = "PRIMARY KEY ("+field.getName()+"));";
                String colum = field.getName()+"  "+databaseDataTypeConverter.get(field.getType().getSimpleName())+" NOT NULL AUTO_INCREMENT";
                columns.add(colum);
            }else{
                String colum = field.getName()+"  "+databaseDataTypeConverter.get(field.getType().getSimpleName()).toString();
                columns.add(colum);
            }
        }
        columns.add(primaryKey);
        return columns.toString();
    }

    private void pushTablesToDatabase(List<String> tables) throws SQLException, IOException {
        Statement statement = connection.createStatement();
        String sql = "USE "+ LoadProperties.getInstance().getProperty().getProperty("dbname");
        statement.executeUpdate(sql);
        for(String table:tables){
            sql = table;
            statement.executeUpdate(sql);
        }
    }

}
