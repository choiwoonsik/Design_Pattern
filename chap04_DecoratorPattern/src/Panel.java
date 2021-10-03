import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Panel extends JPanel {

    public JPanel create(Display display, int width, int height) {
        JPanel panel = display.create();
        panel.setMinimumSize(new Dimension(width, height));
        panel.setPreferredSize(new Dimension(width, height));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
        return panel;
    }
}
