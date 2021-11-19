public class calculate implements STATE{
    Calculator calculator;

    public calculate (Calculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public void decimalInput(String value) {
        System.out.println("잘못된 입력입니다. '='를 입력하세요.");
    }

    @Override
    public void operatorInput(String oper) {
        char c = oper.charAt(0);
        if (c == '=') {
            calculator.printOutResult();
            calculator.setState(calculator.noOperandOne);
        } else {
            System.out.println("잘못된 입력입니다. '='를 입력하세요.");
        }
    }
}
