import java.util.Iterator;

//DynamicArray<String> strArray = new DynamicArray<String>();
//DynamicArray<Integer> intArray = new DynamicArray<Integer>();

public class DynamicArray<T> implements DataCollection<T> {
    final int SIZE = 3;
    private int length;
    private int capacity;
    private Object[] data;

    public DynamicArray() {
        data = new Object[SIZE];
        length = 0;
        capacity = SIZE;
    }

    public void put(T t) {
        if (length >= capacity) {
            boolean b = resizeArray(); // 재할당
            if (b) {
                data[length] = t;
                length++;
            }
        }
        else {
            data[length] = t;
            length++;
        }
    }

    public T get(int index) {
        if (index >= 0 && index < length) {
            return (T) data[index];
        }
        return null;
    }

    public int length() {
        return length;
    }

    public Iterator createIterator() {
        return new DynamicArrayIterator(this);
    }

    private boolean resizeArray() {
        Object[] newData = new Object[capacity + SIZE];
        if (newData == null) {
            return false;
        }
        for (int i = 0; i < capacity; i++) {
            newData[i] = data[i];
        }
        data = newData;
        capacity += SIZE;
        return true;
    }
}
