import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class TimeDisplay extends DisplayDecorator {

    private final Display displayComponent;
    private LabelPanel labelPanel;

    public TimeDisplay(Display display, int width, int height) {
        super(width, height);
        this.displayComponent = display;
    }

    @Override
    public JPanel create() {
        JPanel panel = new Panel().create(displayComponent, getWidth(), getHeight());
        labelPanel = new LabelPanel();
        panel.add(labelPanel.createPanel(getWidth(), super.getHeight()));
        return panel;
    }

    @Override
    public void show() {
        displayComponent.show();
        labelPanel.updateText("시간: " + LocalDateTime.now());
    }

    @Override
    public int getHeight() {
        return displayComponent.getHeight() + super.getHeight();
    }
}
