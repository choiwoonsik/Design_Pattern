public interface Sorter {

    int compare(Object i, Object j);
    default void bubbleSort(Object[] obj) {
        for (int i = 0; i < obj.length - 1; i++) {
            for (int j = i + 1; j < obj.length; j++) {
                if (compare(obj[i], obj[j]) < 0) {
                    Object tmp = obj[i];
                    obj[i] = obj[j];
                    obj[j] = tmp;
                }
            }
        }
    }
}
