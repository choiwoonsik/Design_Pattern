package SiteIdPw_DaoPattern;

import java.util.List;

public interface DAO<D, K> {
    // crud
    void insert(D data);

    D findByKey(K key);

    List<D> findAll();

    void update(D data);

    void delete(D data);

    void deleteByKey(K key);
}
