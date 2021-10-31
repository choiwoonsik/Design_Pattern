import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDaoImpl implements PersonDao {
    final static String DB_FILE_NAME = "addressBook.db";
    final static String DB_TABLE_NAME = "persons";

    Statement statement = null;
    Connection connection = null;
    ResultSet rs = null;

    public PersonDaoImpl() {
        final String table = " (ID INTEGER PRIMARY KEY AUTOINCREMENT, name text, address text)";
        try {
            this.connection = DriverManager.getConnection(
                    "jdbc:sqlite:" + DB_FILE_NAME
            );
            this.statement = connection.createStatement();
            this.statement.setQueryTimeout(30);

            statement.executeUpdate(
                    "DROP TABLE IF EXISTS " + DB_TABLE_NAME
            );
            statement.executeUpdate(
                    "CREATE TABLE " + DB_TABLE_NAME + table
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Person person) {
        try {
            String format = "INSERT INTO %s VALUES(%d, '%s', '%s')";
            String query = String.format(format,
                    DB_TABLE_NAME, person.getId(), person.getName(), person.getAddress());
            statement.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Person> findAll() {
        ArrayList<Person> people = new ArrayList<>();
        try {
            String format = "SELECT * FROM %s";
            String query = String.format(format, DB_TABLE_NAME);
            rs = statement.executeQuery(query);

            while (rs.next()) {
                people.add(new Person(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address"))
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return people;
    }

    @Override
    public Person findById(int id) {
        Person person = null;
        try {
            String format = "SELECT * from %s where id=%d";
            String query = String.format(format, DB_TABLE_NAME, id);
            rs = statement.executeQuery(query);
            if (rs.next()) {
                person = new Person(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address")
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return person;
    }

    @Override
    public void update(int id, Person person) {
        Person p = findById(id);

        if (p != null) {
            try {
                String format = "UPDATE %s SET name='%s', address='%s' WHERE id=%d";
                String query = String.format(
                        format, DB_TABLE_NAME, person.getName(), person.getAddress(), person.getId()
                );
                statement.executeUpdate(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Person person) {
        deleteById(person.getId());
    }

    @Override
    public void deleteById(int id) {
        try {
            String format = "DELETE FROM %s where id=%d";
            String query = String.format(format, DB_TABLE_NAME, id);
            statement.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
