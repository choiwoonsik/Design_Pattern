import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MainWindow extends FrameWindow implements ActionListener, Subject{
    private static final String MAIN_TITLE = "Main Window";
    private static final String TEXT_FIELD_WINDOW_TITLE = "TextField Window";
    private static final String LABEL_WINDOW_TITLE = "Label Window";
    private static final String START_TEXT_FIELD = "Start TextField Window";
    private static final String START_LABEL_FIELD = "Start Label Window";
    private static final String STOP_TEXT_FIELD = "Stop TextField Window";
    private static final String STOP_LABEL_FIELD = "Stop Label Window";
    private static final String STOP_THREAD_BUTTON_TITLE = "Stop Generating Prime Number";
    private static final int X = 250;
    private static final int Y = 100;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 200;
    private static final int GAP = 50;
    private static final ArrayList<Observer> observers = new ArrayList<>();

    private JButton stopButton;
    private JButton updateTextFieldObserverButton;
    private JButton updateLabelObserverButton;
    private PrimeObservableThread primeThread;
    private TextFieldWindow textFieldWindow;
    private LabelWindow labelWindow;

    public MainWindow() {}

    public MainWindow(String title) {
        super(title, X, Y, WIDTH, HEIGHT);
        textFieldWindow = new TextFieldWindow(TEXT_FIELD_WINDOW_TITLE, X, Y + HEIGHT + GAP, WIDTH, HEIGHT);
        labelWindow = new LabelWindow(LABEL_WINDOW_TITLE, X, Y + (HEIGHT + GAP) * 2, WIDTH, HEIGHT);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                primeThread.stopRunning();
                textFieldWindow.closeWindow();
                labelWindow.closeWindow();
                System.exit(0);
            }
        });

        subscribe(textFieldWindow);
        subscribe(labelWindow);
        primeThread = new PrimeObservableThread();
        primeThread.run();
    }

    public JPanel createPanel(int width, int height) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(width, height));

        updateTextFieldObserverButton = createButton(STOP_TEXT_FIELD, this, width, height);
        panel.add(updateTextFieldObserverButton);

        updateLabelObserverButton = createButton(STOP_LABEL_FIELD, this, width, height);
        panel.add(updateLabelObserverButton);

        stopButton = createButton(STOP_THREAD_BUTTON_TITLE, this, width, height);
        panel.add(stopButton);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == updateTextFieldObserverButton) {
            if (updateTextFieldObserverButton.getText().equals(START_TEXT_FIELD)) {
                subscribe(textFieldWindow);
                updateTextFieldObserverButton.setText(STOP_TEXT_FIELD);
            } else {
                unSubscribe(textFieldWindow);
                updateTextFieldObserverButton.setText(START_TEXT_FIELD);
            }
        }
        else if (e.getSource() == updateLabelObserverButton) {
            if (updateLabelObserverButton.getText().equals(START_LABEL_FIELD)) {
                subscribe(labelWindow);
                updateLabelObserverButton.setText(STOP_LABEL_FIELD);
            } else {
                unSubscribe(labelWindow);
                updateLabelObserverButton.setText(START_LABEL_FIELD);
            }
        } else if (e.getSource() == stopButton) {
            primeThread.stopRunning();
        }
    }

    private JButton createButton(String text, ActionListener listener, int width, int height) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        Dimension buttonDimension = new Dimension(width, height / 3);
        button.setMaximumSize(buttonDimension);
        button.setMinimumSize(buttonDimension);
        button.setPreferredSize(buttonDimension);
        return button;
    }

    public static void main(String[] args) {
        new MainWindow(MainWindow.MAIN_TITLE);
    }

    @Override
    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unSubscribe(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyWindow(String msg) {
        observers.forEach(observer -> observer.update(msg));
    }
}
