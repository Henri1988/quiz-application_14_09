package spi;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public interface Dao <T,I>{
    Optional<T> get(int id);
    Collection<T> getAll();
    void save(T t) throws SQLException;
    void update(T t);
    void delete(T t);

    Collection<T>  searchQuestionsByTopic(String topic) throws SQLException;

    void deleteAll();
}
