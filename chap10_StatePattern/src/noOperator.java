public class noOperator implements STATE{
    Calculator calculator;

    public noOperator (Calculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public void decimalInput(String value) {
        System.out.println("연산 기호를 입력하세요.");
    }

    @Override
    public void operatorInput(String oper) {
        char tmp = oper.charAt(0);
        if (tmp == '+' || tmp == '-' || tmp == '*' || tmp == '/') {
            calculator.operator = oper.charAt(0);
            System.out.println("연산자 : " + calculator.operator);
            calculator.setState(calculator.noOperandTwo);
        } else {
            System.out.println("잘못된 연산 기호입니다. 다시 입력하세요.");
        }
    }
}
