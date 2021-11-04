import java.util.Iterator;

public interface DataCollection<T> {
    void put(T t);
    T get(int index);
    int length();
    Iterator<T> createIterator();
}
