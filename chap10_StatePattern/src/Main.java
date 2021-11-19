import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Calculator calculator = new Calculator();
        boolean run = true;
        while (run) {
            run = calculator.run();
        }
    }
}
