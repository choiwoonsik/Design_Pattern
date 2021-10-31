import java.util.List;

public interface PersonDao {
    void insert(Person person);

    List<Person> findAll();

    Person findById(int id);

    void update(int id, Person person);

    void delete(Person person);

    void deleteById(int id);
}
