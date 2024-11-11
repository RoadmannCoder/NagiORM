package main.java.Configuration;

import main.java.Annotation.Entity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EntityManagerImpl implements EntityManager{


    public EntityManagerImpl() throws ClassNotFoundException {
        initializeTables();
    }

    @Override
    public void persist(Object object) {

    }

    @Override
    public <T> T find(Class<T> clazz, Object val) {
        return null;
    }

    private void initializeTables() throws ClassNotFoundException {
        List<Class<?>> Entities = getEntities();

    }
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

}
