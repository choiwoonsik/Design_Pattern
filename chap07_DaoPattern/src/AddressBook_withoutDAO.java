import java.sql.*;

public class AddressBook_withoutDAO {
    final static String DB_FILE_NAME = "addressBook.db";
    final static String DB_TABLE_NAME = "persons";

    public static void main(String[] args) {
        Connection connection = null;
        ResultSet rs = null;
        Statement statement = null;

        try {
            connection = DriverManager.getConnection(
                    "jdbc:sqlite:" + DB_FILE_NAME
            );
            statement = connection.createStatement();
            statement.setQueryTimeout(30);

            final String table = " (ID INTEGER PRIMARY KEY AUTOINCREMENT, name text, address text)";

            statement.executeUpdate(
                    "DROP TABLE IF EXISTS " + DB_TABLE_NAME
            );
            statement.executeUpdate(
                    "CREATE TABLE " + DB_TABLE_NAME + table
            );

            System.out.println("---inserting ...");
            statement.execute(
                    "INSERT INTO persons(name, address) VALUES('woonsik choi', '135 anyang')"
            );
            statement.execute(
                    "insert into persons(name, address) values('boonsik kim', '531 gangnam')"
            );

            System.out.println("---finding all ...");
            rs = statement.executeQuery(
                    "select * from persons where id < 4 order by id"
            );
            while (rs.next()) {
                System.out.println(rs.getInt("ID")
                        + " "
                        + rs.getString("name")
                        + " "
                        + rs.getString("address"));
            }

            System.out.println("---updating ...");
            statement.execute(
                    "update persons SET name = 'handsome guy woonsik' where id = 1"
            );

            System.out.println("---see if updated ...");
            rs = statement.executeQuery("" +
                    "select * from persons where id = 1"
            );
            while (rs.next()) {
                System.out.println(rs.getInt("ID")
                        + " "
                        + rs.getString("name")
                        + " "
                        + rs.getString("address"));
            }

            System.out.println("---deleting ...");
            statement.execute(
                    "delete from persons where id = 1"
            );

            System.out.println("---finding all after delete ...");
            rs = statement.executeQuery(
                    "select * from persons where id < 4 order by id"
            );
            while (rs.next()) {
                System.out.println(rs.getInt("ID")
                        + " "
                        + rs.getString("name")
                        + " "
                        + rs.getString("address"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
