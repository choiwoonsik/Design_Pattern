public class noOperandTwo implements STATE {
    Calculator calculator;

    public noOperandTwo(Calculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public void decimalInput(String value) {
        calculator.operand2 = Integer.parseInt(value);
        System.out.println("두번째 인자 : " + calculator.operand2);
        calculator.setState(calculator.calculate);
    }

    @Override
    public void operatorInput(String oper) {
        System.out.println("숫자를 입력하세요.");
    }
}
