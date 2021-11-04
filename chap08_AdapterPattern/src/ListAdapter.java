import java.util.ArrayList;
import java.util.Iterator;

public class ListAdapter<T> implements DataCollection<T> {

    ArrayList<T> list;

    public ListAdapter() {
        list = new ArrayList<>();
    }

    @Override
    public void put(T t) {
        list.add(t);
    }

    @Override
    public T get(int index) {
        if (index < 0 || list.size() <= index) {
            throw new IndexOutOfBoundsException();
        }
        return list.get(index);
    }

    @Override
    public int length() {
        return list.size();
    }

    @Override
    public Iterator<T> createIterator() {
        return list.iterator();
    }
}
