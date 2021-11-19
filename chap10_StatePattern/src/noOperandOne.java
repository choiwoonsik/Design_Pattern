public class noOperandOne implements STATE {
    Calculator calculator;

    public noOperandOne(Calculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public void decimalInput(String value) {
        calculator.operand1 = Integer.parseInt(value);
        System.out.println("첫번째 인자 : " + calculator.operand1);
        calculator.setState(calculator.noOperator);
    }

    @Override
    public void operatorInput(String oper) {
        System.out.println("숫자를 입력하세요.");
    }
}
