import javax.swing.*;

public class MessageDisplay extends DisplayDecorator {

    Display displayComponent;
    LabelPanel labelPanel;

    public MessageDisplay(Display display, int width, int height) {
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
        labelPanel.updateText("메시지: "+ "안녕하세요. 누구세요?");
    }

    @Override
    public int getHeight() {
        return displayComponent.getHeight() + super.getHeight();
    }
}
