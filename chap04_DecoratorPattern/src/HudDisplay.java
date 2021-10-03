import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class HudDisplay extends Display {
    JPanel panel;
    LabelPanel labelPanel;

    HudDisplay(int width, int height) {
        super(width, height);
    }

    /* create() 함수에서 실질적인 허드 디스플레이 패널이 생성됨. 여기서 생성되는 패널은 데코레이터 패턴의 기본 베이스에 해당됨. 
       유사한 형태로 데코레이터에서 사용되는 패널이 생성되고, 해당 패널의 일부로 여기서 생성되는 패널이 포함됨. 파워포인트 그림 참조 */
    @Override
    public JPanel create() {
        // 레이블을 담을 패널 생성
        panel = new JPanel();
        // 레이블 또는 다른 패널이 추가될 때 세로 방향으로 나열되도록 레이아웃 설정
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        // 최소 크기와 선호하는 크기를 동일하게 만들어서 패널 너비와 높이를 고정 시킴
        panel.setMinimumSize(new Dimension(getWidth(), getHeight()));
        panel.setPreferredSize(new Dimension(getWidth(), getHeight()));
        // 문자열을 보일 레이블 생성
        labelPanel = new LabelPanel();
        // 패널에 레이블을 붙임. 
        panel.add(labelPanel.createPanel(getWidth(), getHeight()));
        return panel;
    }

    @Override
    public void show() {
        // 패널에 보일 문자열
        labelPanel.updateText("Hud Display");
    }
}
