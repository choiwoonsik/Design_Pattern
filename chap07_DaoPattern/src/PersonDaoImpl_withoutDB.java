import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PersonDaoImpl_withoutDB implements PersonDao {
    List<Person> personList;

    public PersonDaoImpl_withoutDB() {
        this.personList = new ArrayList<>();
    }

    @Override
    public void insert(Person person) {
        personList.add(person);
    }

    @Override
    public List<Person> findAll() {
        return personList;
    }

    @Override
    public Person findById(int id) {
        return personList.stream()
                .filter(p -> p.getId() == id)
                .collect(Collectors.toList()).get(0);
    }

    @Override
    public void update(int id, Person person) {
        personList.stream()
                .filter(o -> o.getId() == id)
                .findFirst().ifPresent(p -> {
                    p.setName(person.getName());
                    p.setAddress(person.getAddress());
                });
    }

    @Override
    public void delete(Person person) {
        deleteById(person.getId());
    }

    @Override
    public void deleteById(int id) {
        personList.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .map(o -> personList.remove(o));
    }
}
