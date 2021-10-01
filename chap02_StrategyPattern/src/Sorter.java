import java.util.Arrays;

public class Sorter {

    private Comparable compare;

    public Sorter(Comparable comparable) {
        this.compare = comparable;
    }

    public void setComparable(Comparable comparable) {
        this.compare = comparable;
    }

    void bubbleSort(Object[] data) {
        for (int i = 0; i < data.length - 1; i++) {
            Object pre = data[i];
            for (int j = i + 1; j < data.length; j++) {
                Object post = data[j];

                Object tmp;
                if (compare.compareTo(pre, post) > 0) {
                    tmp = post;
                    post = pre;
                    pre = tmp;
                }
                data[i] = pre;
                data[j] = post;
            }
        }
    }

}
