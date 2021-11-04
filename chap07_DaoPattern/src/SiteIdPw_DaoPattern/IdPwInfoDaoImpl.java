package SiteIdPw_DaoPattern;

import java.sql.*;

public class IdPwInfoDaoImpl extends DaoImpl<SiteIdPwInfo, String> {
    final static String DB_FILE_NAME = "IdPassword.db";

    Connection connection = null;
    Statement statement = null;

    public IdPwInfoDaoImpl(String dbTableName) {
        super(dbTableName);

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_FILE_NAME);
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            final String table = "(url text PRIMARY KEY, id text, password text)";
            statement.executeUpdate("DROP TABLE IF EXISTS " + dbTableName);
            statement.executeUpdate("CREATE TABLE " + dbTableName + table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Statement getStatement() {
        return statement;
    }

    @Override
    public String getInstanceValueQuery(SiteIdPwInfo data) {
        String format = "'%s', '%s', '%s'";

        return String.format(
                format,
                data.getKey(),
                data.getUserId(),
                data.getUserPw()
        );
    }

    @Override
    public String getUpdateInstanceValueQuery(SiteIdPwInfo data) {
        String format = "url='%s', id='%s', password='%s'";
        return String.format(
                format,
                data.getKey(),
                data.getUserId(),
                data.getUserPw()
        );
    }

    @Override
    public SiteIdPwInfo getInstance(ResultSet resultSet) {
        try {
            String url = resultSet.getString("url");
            String uid = resultSet.getString("id");
            String upw = resultSet.getString("password");
            return new SiteIdPwInfo(url, uid, upw);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getKeyColumnName() {
        return "URL";
    }
}
