import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class OperatorBtnCommand extends CommandButton implements Command {
    Calculator calculator;
    JLabel display;

    public OperatorBtnCommand(
            String text, Dimension dimension,
            ActionListener listener, Calculator calculator, JLabel display)
    {
        super(text, dimension, listener);
        this.calculator = calculator;
        this.display = display;
    }

    @Override
    public void execute() {
        String r = "0";
        if (calculator.isOperand1Set() && !calculator.isOperand2Set()
                && !getText().equals("c") && !getText().equals("=")) {
            calculator.setOperatorSet(true);
            calculator.setOperator(getText().charAt(0));
            return;
        } else if (calculator.isOperand1Set() && calculator.isOperatorSet() && calculator.isOperand2Set()) {
            if (getText().equals("=")) {
                int f = calculator.getOperand1();
                int b = calculator.getOperand2();
                System.out.println(f + " " + calculator.getOperator() + " " + b);
                switch (calculator.getOperator()) {
                    case '+':
                        r = (f + b) + "";
                        break;
                    case '-':
                        r = (f - b) + "";
                        break;
                    case '*':
                        r = (f * b) + "";
                        break;
                    case '/':
                        if (b == 0) {
                            r = null;
                            break;
                        } else r = (f / b) + "";
                }
            }
        }
        display.setText(r);
        calculator.setOperand1Set(false);
        calculator.setOperand2Set(false);
        calculator.setOperatorSet(false);
        calculator.setOperand1(0);
        calculator.setOperand2(0);
    }
}
