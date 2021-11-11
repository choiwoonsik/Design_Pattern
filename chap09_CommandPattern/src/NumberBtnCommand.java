import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class NumberBtnCommand extends CommandButton implements Command {
    Calculator calculator;
    JLabel display;

    public NumberBtnCommand(
            String text, Dimension dimension,
            ActionListener listener, Calculator calculator, JLabel display)
    {
        super(text, dimension, listener);
        this.calculator = calculator;
        this.display = display;
    }

    @Override
    public void execute() {
        if (calculator.isOperand1Set() && calculator.isOperatorSet()) {
            System.out.println(getText());

            int tmp = calculator.getOperand2();
            tmp *= 10;
            tmp += Integer.parseInt(getText());
            display.setText(tmp+"");
            calculator.setOperand2(tmp);
            calculator.setOperand2Set(true);
        } else {
            System.out.println(getText());

            int tmp = calculator.getOperand1();
            tmp *= 10;
            tmp += Integer.parseInt(getText());
            display.setText(tmp+"");
            calculator.setOperand1(tmp);
            calculator.setOperand1Set(true);
        }
    }
}
