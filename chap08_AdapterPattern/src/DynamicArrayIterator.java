import java.util.Iterator;

public class DynamicArrayIterator implements Iterator {
    private DynamicArray array;
    private int index;

    public DynamicArrayIterator(DynamicArray arr) {
        array = arr;
        index = 0;
    }

    @Override
    public boolean hasNext() {
        return (index < array.length());
    }

    @Override
    public Object next() {
        Object obj = array.get(index);
        index++;
        return obj;
    }

    @Override
    public void remove() {
        //throw new UnsupportedOperationException();
    }
}
