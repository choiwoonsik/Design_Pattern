import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Calculator {
    int operand1; // 첫 번째 피 연산자값 저장
    int operand2; // 두 번째 피 연산자값 저장
    char operator; // 사칙 연산자 저장

    BufferedReader br;
    STATE noOperandOne;
    STATE noOperandTwo;
    STATE noOperator;
    STATE calculate;
    STATE state;

    public Calculator() {
        this.noOperandOne = new noOperandOne(this);
        this.noOperandTwo = new noOperandTwo(this);
        this.noOperator = new noOperator(this);
        this.calculate = new calculate(this);
        this.state = noOperandOne;
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    public void setState(STATE state) {
        this.state = state;
    }

    void printOutResult() {
        switch (operator) {
            case '+' -> System.out.printf("%d + %d = %d\n", operand1, operand2, operand1 + operand2);
            case '-' -> System.out.printf("%d - %d = %d\n", operand1, operand2, operand1 - operand2);
            case '*' -> System.out.printf("%d * %d = %d\n", operand1, operand2, operand1 * operand2);
            case '/' -> System.out.printf("%d / %d = %d\n", operand1, operand2, operand1 / operand2);
        }
    }

    boolean run() throws IOException {
        System.out.println("정수 또는 +, -, *, /, = 기호 중 한 개를 입력하세요. (종료 : q)");
        String input = br.readLine();
        char ch;
        if (input.equals("")) {
            ch = '=';
            input = "=";
        } else ch = input.charAt(0);

        if (ch == 'q' || ch == 'Q') {
            state.quitInput();
            return false;
        } else if (ch >= '0' && ch <= '9') {
            state.decimalInput(input);
        } else {
            state.operatorInput(input);
        }
        return true;
    }
}
