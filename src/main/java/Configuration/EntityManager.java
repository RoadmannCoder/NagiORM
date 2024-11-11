package main.java.Configuration;
public interface EntityManager {
    void persist(Object object);
    <T> T find(Class<T> clazz, Object val);

}
