import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CommandButton extends JButton {

    public CommandButton(String text, Dimension dimension, ActionListener listener) {
        super(text);
        addActionListener(listener);
        setMaximumSize(dimension);
        setMinimumSize(dimension);
        setPreferredSize(dimension);
    }
}
