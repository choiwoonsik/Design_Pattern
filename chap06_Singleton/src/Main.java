import java.util.HashSet;

public class Main {
    public static void main(String[] args) {

        HashSet<String> set = new HashSet<>();

        for (int i = 0; i < 100; i++) {
            int tmp = i;
            new Thread(() -> {
                ChocolateBoiler instance = ChocolateBoiler.getInstance();
                set.add(instance.toString());
                System.out.println(tmp + ": " + System.currentTimeMillis() +" , "+ set.size());
            }).start();
        }
    }
}