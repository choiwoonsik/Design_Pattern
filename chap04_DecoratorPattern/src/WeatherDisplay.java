import javax.swing.*;
import java.awt.*;

public class WeatherDisplay extends DisplayDecorator {

    private final Display displayComponent;
    private LabelPanel labelPanel;

    public WeatherDisplay(Display display, int width, int height) {
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
        labelPanel.updateText("날씨: 매우 맑고 시원 선선함");
    }

    @Override
    public int getHeight() {
        return displayComponent.getHeight() + super.getHeight();
    }
}
