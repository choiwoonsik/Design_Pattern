public class NameSorter implements Sorter {
    @Override
    public int compare(Object i, Object j) {
        return ((Person)j).getName().compareTo(((Person)i).getName());
    }
}
