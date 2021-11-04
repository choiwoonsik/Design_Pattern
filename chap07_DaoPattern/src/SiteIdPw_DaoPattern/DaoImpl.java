package SiteIdPw_DaoPattern;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class DaoImpl<D extends DbData<K>, K> implements DAO<D, K> {

    String dbTableName;
    public abstract Statement getStatement();
    public abstract String getInstanceValueQuery(D data);
    public abstract String getUpdateInstanceValueQuery(D data);
    public abstract D getInstance(ResultSet resultSet);
    public abstract String getKeyColumnName();

    public DaoImpl(String dbTableName) {
        this.dbTableName = dbTableName;
    }

    @Override
    public void insert(D data) {
        try {
            String format = "INSERT INTO %s VALUES(%s)";
            String query = String.format(
                    format, dbTableName, getInstanceValueQuery(data)
            );
            getStatement().executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public D findByKey(K key) {
        try {
            ResultSet rs;
            String format = "SELECT * from %s where %s = '%s'";
            String query = String.format(format, dbTableName, getKeyColumnName() , key.toString());
            rs = getStatement().executeQuery(query);
            if (rs.next()) {
                return getInstance(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<D> findAll() {
        try {
            List<D> dataList = new ArrayList<>();
            ResultSet rs;

            String format = "SELECT * from %s";
            String query = String.format(format, dbTableName);
            rs = getStatement().executeQuery(query);

            while (rs.next()) {
                dataList.add(getInstance(rs));
            }
            return dataList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(D data) {
        if (findByKey(data.getKey()) != null) {
            try {
                String format = "update %s SET %s where '%s' = '%s'";
                String query = String.format(
                        format, dbTableName, getUpdateInstanceValueQuery(data),
                        getKeyColumnName(), data.getKey().toString()
                );
                getStatement().executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(D data) {
        deleteByKey(data.getKey());
    }

    @Override
    public void deleteByKey(K key) {
        try {
            String format = "delete from %s where %s = '%s'";
            String query = String.format(
                    format, dbTableName, getKeyColumnName(), key
            );
            getStatement().executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
