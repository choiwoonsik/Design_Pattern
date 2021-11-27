public class AgeSorter implements Sorter {
    @Override
    public int compare(Object i, Object j) {
        return ((Person)j).getAge() - ((Person)i).getAge();
    }
}
