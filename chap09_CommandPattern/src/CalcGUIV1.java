import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class CalcGUIV1 extends JFrame implements ActionListener {
    final static int WINDOW_WIDTH = 400;
    final static int WINDOW_HEIGHT = 300;
    final static int COMPONENT_HEIGHT = 40;
    final static int BUTTON_WIDTH = 50;

    String[] buttonText = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", " ", " ", "+", "-", "*", "/", "c" , "=" };
    Calculator calculator;

    Dimension displayDimension = new Dimension(WINDOW_WIDTH - 20, COMPONENT_HEIGHT);
    Dimension buttonDimension = new Dimension(BUTTON_WIDTH, COMPONENT_HEIGHT);

    JLabel display = new JLabel(); // 숫자 값 및 결과 출력 화면

    CalcGUIV1() {
        super("calculator");
        calculator = new Calculator();

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        Font labelFont = display.getFont();
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setFont(new Font(labelFont.getName(), Font.PLAIN, COMPONENT_HEIGHT - 5));
        display.setPreferredSize(new Dimension(displayDimension));

        setResizable(false);
        setLayout(new BorderLayout());

        add(getDisplayPanel(), BorderLayout.NORTH);
        add(getButtonPanel(), BorderLayout.CENTER);
        clear();
    }

    public JPanel getDisplayPanel() {
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        displayPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        displayPanel.setPreferredSize(displayDimension);
        displayPanel.add(display);
        return displayPanel;
    }
    public JPanel getButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5,3,10,5));

        CommandButton numberBtn;
        CommandButton operBtn;

        for (int i = 0; i < 10; i++) {
            numberBtn = new NumberBtnCommand(buttonText[i], buttonDimension, this, calculator, display);
            buttonPanel.add(numberBtn);
        }
        for (int i = 10; i < buttonText.length; i++) {
            operBtn = new OperatorBtnCommand(buttonText[i], buttonDimension, this, calculator, display);
            buttonPanel.add(operBtn);
        }
        return buttonPanel;
    }

    public void clear() {
        display.setText("0");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Command) {
            ((Command) e.getSource()).execute();
        }
    }

    public static void main(String[] args) {
        CalcGUIV1 c = new CalcGUIV1();
        c.setDefaultCloseOperation(EXIT_ON_CLOSE);
        c.setVisible(true);
    }
}

