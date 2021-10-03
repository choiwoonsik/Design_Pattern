import com.sun.security.jgss.GSSUtil;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MainWindow extends FrameWindow {
    private static final String MAIN_TITLE = "woonsik's Window";
    private static final int X = 250;
    private static final int Y = 100;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 100;

    private ArrayList<String> displayList;
    private JFrame frame;

    public MainWindow(String title, ArrayList<String> list) {
        displayList = list;
        frame = createWindow(title, X, Y, WIDTH, HEIGHT * (displayList.size() + 1));
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeWindow();
                System.exit(0);
            }
        });
    }

    public JFrame createWindow(String title, int x, int y, int width, int totalHeight) {
        JFrame frame;
        frame = new JFrame(title);
        frame.setBounds(x, y, width, totalHeight);

        JPanel panel = createPanel(width, totalHeight);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
        return frame;
    }

    public void closeWindow() {
        frame.setVisible(false);
        frame.dispose();
    }

    public JPanel createPanel(int width, int totalHeight) {
        // 제일 바탕에 놓일 패널 생성
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setMinimumSize(new Dimension(width, totalHeight));
        panel.setPreferredSize(new Dimension(width, totalHeight));

        Display display = new HudDisplay(WIDTH, HEIGHT);

        // 새로운 장식이 추가될 때마다 새로운 패널이 생성되고, 그 안에 기존 패널이 추가된다.
        for (String name : displayList) {
            if (name.equals("speed")) {
                display = new SpeedometerDisplay(display, WIDTH, HEIGHT);
            }
            else if (name.equals("time")) {
                display = new TimeDisplay(display, WIDTH, HEIGHT);
            }
            else if (name.equals("weather")) {
                display = new WeatherDisplay(display, WIDTH, HEIGHT);
            }
            else if (name.equals("message")) {
                display = new MessageDisplay(display, WIDTH, HEIGHT);
            }
        }
        // 장식이 모두 끝나면 최종 디스플레이 패널을 생성함.
        panel.add(display.create());

        // 높이를 출력
        System.out.println("disply.totalHeight = " + display.getHeight());

        // 디스플레이마다 각각의 내용을 화면에 보임
        display.show();
        return panel;
    }

    public static void main(String[] args) {
        final String displayFileName = "displays.txt";
        ArrayList<String> list;

        LoadHudDisplays loadDisplay = new LoadHudDisplays(displayFileName);
        list = loadDisplay.load();

        System.out.printf("display.size() = %d\n", list.size());
        for (String s : list) {
            System.out.println(s);
        }

        new MainWindow(MainWindow.MAIN_TITLE, list);
    }
}
