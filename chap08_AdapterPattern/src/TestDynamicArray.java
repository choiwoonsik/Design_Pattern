import java.util.Iterator;

public class TestDynamicArray {
    public static void main(String[] args) {
//        DataCollection<String> arr = new DynamicArray<>();
        DataCollection<String> arr = new ListAdapter<>();
        arr.put("Seoul");
        arr.put("Busan");
        arr.put("Gwangju");
        arr.put("Daejeon");
        arr.put("Cheonan");
        arr.put("Ulsan");
        arr.put("Daegu");
        arr.put("Incheon");

        System.out.println("<index version>");
        for (int i = 0; i < arr.length(); i++) {
            String s = arr.get(i);
            System.out.println(s);
        }

        System.out.println("\n<iterator version>");
        Iterator<String> iter = arr.createIterator();
        while (iter.hasNext()) {
            String s = iter.next();
            System.out.println(s);
        }
    }
}
