# 디자인 패턴 학습 정리

### 목차

1. [다형성](#1-polymorphism)
2. [Strategy Pattern](#2-strategy-pattern)
3. [Observer Pattern](#3-observer-pattern)
4. [Decorator Pattern](#4-decorator-pattern)
5. [Factory Pattern](#5-factory-method-pattern)
6. [Singleton Pattern](#6-singleton-pattern)
7. [DAO Pattern](#7-dao-pattern)
8. [DAO Pattern Refactoring](#7-2-dao-pattern-refactoring-with-generic)
9. [Adapter Pattern](#8-adapter-pattern)
10. [Facade Pattern](#9-facade-pattern)
11. [Command Pattern](#10-command-pattern)
12. [State Pattern](#11-state-pattern)
13. []()
14. []()

## 1. Polymorphism

- 하나의 객체가 여러개의 타입을 가질 수 있는것을 말한다.
- 부모 클래스 타입의 참조변수로 여러 자식 클래스 타입의 인스턴스를 참조할 수 있다.
- 인스턴스를 이용한 상속관계 및 오버라이딩을 사용하여 구현한다.

#### Main.java _ [코드](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap01_polymorphism/src/Main.java)

```java
import Converter.DocxDocConverter;
import Converter.OdtDocConverter;
import Converter.PdfDocConverter;

public class Main {
    public static void main(String[] args) {
        WordProcessor wp = new WordProcessor("new doc");
        wp.setSpellChecker(new EngSpellChecker());

        wp.addDocConverter(new DocxDocConverter());
        wp.addDocConverter(new PdfDocConverter());
        wp.addDocConverter(new OdtDocConverter());

        wp.checkSpelling();

        wp.convertDocTo("odt");
        wp.convertDocTo("pdf");
        wp.convertDocTo("docx");
        wp.convertDocTo("wps");
    }
}
```

#### 설명

- WordProcessor클래스는 문서 변환기, 맞춤법 검사기를 등록할 수 있다. 이를 사용하여 맞춤범을 검사하고 문서를 변한한다.
  [코드](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap01_polymorphism/src/WordProcessor.java)
- EngSpellChecker클래스는 구현 클래스로 ISpellChecker 인터페이스를 상속받아서 구현한 클래스이다. 영어 맞춤법을 검사한다.
  [코드](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap01_polymorphism/src/ISpellChecker.java)
- DocxDocConverter, PdfDocConverter, OdtDocConverter클래스는 DocConveter Abstract 클래스를 상속받아서 구현한 구현한 클래스이다.
  [코드](https://github.com/choiwoonsik/Design_Pattern/tree/main/chap01_polymorphism/src/Converter)

#### 로직

> main클래스에서 문서 처리기를 만들고, 해당 문서 처리기에 EngSpellChecker를 추가한다. EngSpellChecker는 IspellChecker 인터페이스를 구현한 클래스로 영어 스펠링 체크를 담당하여 구현한 클래스이다. addDocConverter()를 통해 문서 별 DocConverter를 생성하여 추가해 준다. 이때 각 문서 Converter들은 추상클래스 DocConverter를 상속받아서 구현한다.

#### 결과

```
Checking English Spelling...
new doc.odt로 변환해서 저장합니다.
new doc.pdf로 변환해서 저장합니다.
new doc.docx로 변환해서 저장합니다.
wps파일 형식을 지원하는 DocConverter가 없습니다.
```

---

## 2. Strategy Pattern

- 같은 종류의 작업을 하는 알고리즘을 정의하고, 각 알고리즘을 캡슐화 하여 알고리즘들을 서로 바꿔 사용할 수 있도록 한다.
- strategy 패턴은 알고리즘을 사용하는 클라이언트로 부터 독립적으로 알고리즘을 바꿔서 적용할 수 있게 한다.

### 클래스 다이어그램

<img width="898" alt="스크린샷 2021-10-01 오후 8 02 46" src="https://user-images.githubusercontent.com/42247724/135609885-8247fcfa-e59a-44fd-9a67-531c34e14755.png">

### Main.java _ [코드](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap02_StrategyPattern/src/Main.java)

```java
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Arrays;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Main {
    String[] names = {"CompareModifiedDate", "CompareFileType", "CompareFileName", "Main", "CompareSize"};
    String[] types = {"java", "java", "class", "java", "Class"};
    String[] dateStrings = {"2020-09-13T21:59:00", "2020-09-12T21:59:00", "2020-09-13T16:54:00", "2020-09-12T21:54:00", "2020-09-13T11:59:00"};
    int[] sizes = {120, 80, 150, 85, 100};

    public FileInfo[] createFileInfoArrays() {
        FileInfo[] fileLists = new FileInfo[names.length];
        SimpleDateFormat dateTimeInstance = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        for (int i = 0; i < fileLists.length; i++) {
            Date date = dateTimeInstance.parse(dateStrings[i], new ParsePosition(0));
            fileLists[i] = new FileInfo(names[i], types[i], sizes[i], date);
        }
        return fileLists;
    }

    public void printFileLists(FileInfo[] fileLists) {
        for (FileInfo fi : fileLists) {
            System.out.printf("이름 : %s, 타입 : %s, 크기 : %d, 수정날짜 : %s\n", fi.name, fi.type, fi.size, fi.modifiedDate);
        }
    }

    public static void main(String[] args) {
        Main m = new Main();
        FileInfo[] fileLists = m.createFileInfoArrays();
        System.out.println("원본 리스트");
        m.printFileLists(fileLists);

        System.out.println("\n\n파일 이름으로 정렬된 리스트");
        Sorter sorter = new Sorter(new CompareFileName());
        sorter.bubbleSort(fileLists);
        m.printFileLists(fileLists);

        System.out.println("\n\n파일 종류로 정렬된 리스트");
        sorter.setComparable(new CompareFileType());
        sorter.bubbleSort(fileLists);
        m.printFileLists(fileLists);

        System.out.println("\n\n파일 크기로 정렬된 리스트");
        sorter.setComparable(new CompareSize());
        sorter.bubbleSort(fileLists);
        m.printFileLists(fileLists);

        System.out.println("\n\n파일 수정 시간으로 정렬된 리스트");
        sorter.setComparable(new CompareModifiedDate());
        sorter.bubbleSort(fileLists);
        m.printFileLists(fileLists);
    }
}
```

### 설명

현재 Main 클래스에서 파일 목록을 생성하고 있다. 이 파일 목록을 정렬하려고 하는데 동일한 정렬 알고리즘으로 여러 기준에 대해 정렬을 하려고 한다.

기준으로는 파일이름, 파일 종류, 파일 크기, 파일 수정 시간 4가지가 존재한다. 이 기준별로 정렬 알고리즘을 각각 짜는것은 매우 비효율적이므로 하나의 정렬 알고리즘을 구현하고, 전달받은 기준으로 정렬하도록 한다.

- Sorter 클래스는 Object 타입에 대해 bubbleSort()를 하는 메소드를 갖고 있으며 setComparable()을 통해 원하는 Comparable을 받을 수 있게
  되어있다. [코드](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap02_StrategyPattern/src/Sorter.java)
- [CompareFileName](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap02_StrategyPattern/src/CompareFileName.java)
  , [CompareFileType](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap02_StrategyPattern/src/CompareFileType.java)
  , [CompareSize](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap02_StrategyPattern/src/CompareSize.java)
  , [CompareModifiedDate](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap02_StrategyPattern/src/CompareModifiedDate.java)
  클래스들은
  전부 [Comparable 인터페이스](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap02_StrategyPattern/src/Comparable.java)를
  상속받아서 구현한 클래스이다. 즉, 각자의 조건에 맞게 Comparable을 구현하여 알고리즘을 캡슐화 할 수 있게 한다.

### 로직

> 원하는 조건으로 setComparable()을 하고 Object 타입으로 받은 객체를 bubbleSort()하므로서 알고리즘을 캡슐화하고 원하는 기준만 적절히 바꿔서 정렬한다.
>

### 결과

```
원본 리스트
이름 : CompareModifiedDate, 타입 : java, 크기 : 120, 수정날짜 : Sun Sep 13 21:59:00 KST 2020
이름 : CompareFileType, 타입 : java, 크기 : 80, 수정날짜 : Sat Sep 12 21:59:00 KST 2020
이름 : CompareFileName, 타입 : class, 크기 : 150, 수정날짜 : Sun Sep 13 16:54:00 KST 2020
이름 : Main, 타입 : java, 크기 : 85, 수정날짜 : Sat Sep 12 21:54:00 KST 2020
이름 : CompareSize, 타입 : Class, 크기 : 100, 수정날짜 : Sun Sep 13 11:59:00 KST 2020

파일 이름으로 정렬된 리스트
이름 : CompareFileName, 타입 : class, 크기 : 150, 수정날짜 : Sun Sep 13 16:54:00 KST 2020
이름 : CompareFileType, 타입 : java, 크기 : 80, 수정날짜 : Sat Sep 12 21:59:00 KST 2020
이름 : CompareModifiedDate, 타입 : java, 크기 : 120, 수정날짜 : Sun Sep 13 21:59:00 KST 2020
이름 : CompareSize, 타입 : Class, 크기 : 100, 수정날짜 : Sun Sep 13 11:59:00 KST 2020
이름 : Main, 타입 : java, 크기 : 85, 수정날짜 : Sat Sep 12 21:54:00 KST 2020

파일 종류로 정렬된 리스트
이름 : CompareSize, 타입 : Class, 크기 : 100, 수정날짜 : Sun Sep 13 11:59:00 KST 2020
이름 : CompareFileName, 타입 : class, 크기 : 150, 수정날짜 : Sun Sep 13 16:54:00 KST 2020
이름 : CompareModifiedDate, 타입 : java, 크기 : 120, 수정날짜 : Sun Sep 13 21:59:00 KST 2020
이름 : CompareFileType, 타입 : java, 크기 : 80, 수정날짜 : Sat Sep 12 21:59:00 KST 2020
이름 : Main, 타입 : java, 크기 : 85, 수정날짜 : Sat Sep 12 21:54:00 KST 2020

파일 크기로 정렬된 리스트
이름 : CompareFileType, 타입 : java, 크기 : 80, 수정날짜 : Sat Sep 12 21:59:00 KST 2020
이름 : Main, 타입 : java, 크기 : 85, 수정날짜 : Sat Sep 12 21:54:00 KST 2020
이름 : CompareSize, 타입 : Class, 크기 : 100, 수정날짜 : Sun Sep 13 11:59:00 KST 2020
이름 : CompareModifiedDate, 타입 : java, 크기 : 120, 수정날짜 : Sun Sep 13 21:59:00 KST 2020
이름 : CompareFileName, 타입 : class, 크기 : 150, 수정날짜 : Sun Sep 13 16:54:00 KST 2020

파일 수정 시간으로 정렬된 리스트
이름 : Main, 타입 : java, 크기 : 85, 수정날짜 : Sat Sep 12 21:54:00 KST 2020
이름 : CompareFileType, 타입 : java, 크기 : 80, 수정날짜 : Sat Sep 12 21:59:00 KST 2020
이름 : CompareSize, 타입 : Class, 크기 : 100, 수정날짜 : Sun Sep 13 11:59:00 KST 2020
이름 : CompareFileName, 타입 : class, 크기 : 150, 수정날짜 : Sun Sep 13 16:54:00 KST 2020
이름 : CompareModifiedDate, 타입 : java, 크기 : 120, 수정날짜 : Sun Sep 13 21:59:00 KST 2020

Process finished with exit code 0
```

---

## 3. Observer Pattern

- 옵저버 패턴은 객체의 상태 변화를 관찰하는 관찰자들, 즉 옵저버들의 목록을 객체에 등록하고 상태의 변화가 있을 때 마다 메서드 등을 통해 객체가 직접 목록의 각 옵저버에게 통지하도록 하는 패턴이다.
- 정리하면 변화가 발생될 것이 예상되는 객체에 옵저버를 보내놓고 변화가 일어나면 옵저버들을 통해 알림을 보내는 것이다.

### 다이어그램

<img width="1153" alt="스크린샷 2021-10-01 오후 8 03 12" src="https://user-images.githubusercontent.com/42247724/135609893-53e05591-091c-4595-83e7-4d03ef1a7d1d.png">

### Main.java _ [코드](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap03_ObserverPattern/src/MainWindow.java#L31)

```java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MainWindow extends FrameWindow implements ActionListener, Subject {
    // 생략
    private static final ArrayList<Observer> observers = new ArrayList<>();
    private PrimeObservableThread primeThread;
    private TextFieldWindow textFieldWindow;
    private LabelWindow labelWindow;

    public MainWindow() {
    }

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

        // 옵저버들을 붙인 클래스들을 구독한다.
        subscribe(textFieldWindow);
        subscribe(labelWindow);
        primeThread = new PrimeObservableThread();
        primeThread.run();
    }

    public JPanel createPanel(int width, int height) {
        /*
            판넬 생성 코드
        */
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == updateTextFieldObserverButton) {
            // start 버튼 누르면 해당 window 옵저버 구독
            if (updateTextFieldObserverButton.getText().equals(START_TEXT_FIELD)) {
                subscribe(textFieldWindow);
                updateTextFieldObserverButton.setText(STOP_TEXT_FIELD);
            } else {
                // stop 구독 취소
                unSubscribe(textFieldWindow);
                updateTextFieldObserverButton.setText(START_TEXT_FIELD);
            }
        } else if (e.getSource() == updateLabelObserverButton) {
            // start 버튼 누르면 해당 window 옵저버 구독	
            if (updateLabelObserverButton.getText().equals(START_LABEL_FIELD)) {
                subscribe(labelWindow);
                updateLabelObserverButton.setText(STOP_LABEL_FIELD);
            } else {
                // stop 구독 취소
                unSubscribe(labelWindow);
                updateLabelObserverButton.setText(START_LABEL_FIELD);
            }
        } else if (e.getSource() == stopButton) {
            primeThread.stopRunning();
        }
    }

    private JButton createButton(String text, ActionListener listener, int width, int height) {
        /*
            버튼 생성 로직
	*/
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
```

### 설명

- MainWindow 클래스에서 창을 생성한다. 이때 구독/취소를 위한 버튼창 & TextFieldWindow & LableWindow 세개의 창이 생긴다. 또한 Subject 인터페이스를 상속받아서 옵저버을
  등록하고 취소하고
  통보하는 [구현](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap03_ObserverPattern/src/MainWindow.java#L108)을
  한다.
- [Lable](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap03_ObserverPattern/src/LabelWindow.java)과 [TextField](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap03_ObserverPattern/src/TextFieldWindow.java)클래스는 [Observer 인터페이스](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap03_ObserverPattern/src/Observer.java)를
  상속받아서 옵저버가 붙게 된다.
- 소수
  생성기인 [PrimeObserableThread클래스](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap03_ObserverPattern/src/PrimeObservableThread.java#L27)에서
  소수를 생성하고 옵저버에게 통보를 한다.

### 로직

- Observer를 상태 변화를 감시할 LableWindow와 TextFieldWindow에 붙여놓는다.
- 해당 옵저버를 구독한다.
- 소수를 계속 생성하고 이를 생성할 때 마다 notifyWindow()를 통해 TextFieldWindow와 LableWindow에 통보한다.
- 통보를 받은 두 윈도우는 update()를 수행한다.
- stop 버튼을 누르면 옵저버를 제거해서 더이상 변화를 통보받지 않는다. 다시 start 버튼을 누르면 다시 변화를 통보받게 된다.
- 이후 변화가 감지되면 notifyWindow()를 통해 변화를 옵저버들에게 통보한다.

### 결과

![ezgif com-gif-maker](https://user-images.githubusercontent.com/42247724/135633274-bca0a406-60be-48bf-85a7-14feb2025d67.gif)

- start를 누르면 구독 : 소수가 생성되는 통보를 계속 받고 화면에 나타낸다.
- stop을 누르면 구독취소 : 더이상 화면의 소수를 업데이트하지 않는다.

---

## 4. Decorator Pattern

- 객체에 추가적인 요건을 동적으로 추가해주는 패턴으로, 서브 클래스를 만들지 않고 기능을 유연하게 확장할 수 있게 한다.
- 데코레이터 패턴에서의 상속은 공통적인 타입으로 추상화하기 위한 용도가 주 목적이다.
- 데코레이터는 인터페이스/추상 클래스로서 역활을 하고 이를 상속받은 클래스들은 컴포넌트를 꾸미기 위한 구현을 갖게 된다.
- 컴포넌트는 바로 사용되거나 데코레이터레가 붙어서 사용될 수 있다.

#### 예시

- 데코레이터 패턴을 일상생활의 예시로 커피를 들 수 있다.
- 커피를 생각해보면 에스프레소, 콜드블루는 컴포넌트가 될 수 있고 얼음, 시나몬 가루, 우유, 두유, 샷, 자바칩 등은 모두 데코레이터 클래스를 상속받은 데코를 위한 클래스가 될 수 있다.
- 이때, 최상위 추상 클래스/인터페이스를 Beverage로 두고 에스프레소, 콜드블루 컴포넌트는 Beverage를 상속받는다.
- 데코들의 추상화를 위해 Beverage를 상속받은 BeverageDecorator를 선언하고, 모든 데코 용 클래스들은 BeverageDecorator를 상속받는다.
- 형태 → 자바칩 ( 우유 ( 얼음 ( 얼음 ( 샷 ( 에스프레소 ) ) ) ) ), 이때 순서는 상관이 없다.

### 다이어그램

<img width="746" alt="스크린샷 2021-10-03 오후 5 42 36" src="https://user-images.githubusercontent.com/42247724/135746322-2f966aae-9257-42aa-a3f5-6365ca2c6ac4.png">

- Display 추상 클래스를 최상위 클래스로 두고 기본 컴포넌트로 HudDisplay를 갖는다.
- HudDisplay를 꾸밀 수 있는 Deco용 클래스들의 추상화 용도로 Display를 상속한 추상 클래스 DisplayDecorator를 둔다.
- Message, Time, Weater, Speed 클래스들은 DisplayDecorator를 상속받아서 구현한다.
- 결국 데코 클래스도 Display 클래스의 하위 클래스로서 데코클래스에서 반환한 클래스에 대해 데코를 반복할 수 있게 된다.

### Main.java _ [코드](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap04_DecoratorPattern/src/MainWindow.java#L22)

```java
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
            } else if (name.equals("time")) {
                display = new TimeDisplay(display, WIDTH, HEIGHT);
            } else if (name.equals("weather")) {
                display = new WeatherDisplay(display, WIDTH, HEIGHT);
            } else if (name.equals("message")) {
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
```

### 설명

main() → MainWindow() → createWindow() → createPanel()

- createPanel 에서 기본 컴포넌트로서 HudDisplay를 만든다.
  _ [코드](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap04_DecoratorPattern/src/MainWindow.java#L58)
- 이후 display.txt파일에 작성된 순서대로 디스플레이에 기능 목록을 추가한다.
- 데코 용
  클래스 :  [MessageDisplay](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap04_DecoratorPattern/src/MessageDisplay.java)
  , [SpeedmeterDisplay](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap04_DecoratorPattern/src/SpeedmeterDisplay.java)
  , [TimeDisplay](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap04_DecoratorPattern/src/TimeDisplay.java)
  , [WeatherDisplay](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap04_DecoratorPattern/src/WeatherDisplay.java)

### 로직

> Display 클래스를 상속한 DisplayDecorator 클래스는 데코 들의 추상 클래스. 이 추상 클래스를 여러 데코 용 클래스들이 상속받아서 데코를 하게 된다.
>
>display.txt파일을 읽고 해당 순서대로 Deco클래스들을 감싸게 되고 마지막에 감싼 구현 클래스의 display.create()를 호출하게 된다.
>
>해당 구현 클래스의 create 메소드를 들어가면 전달 받은 클래스의 create()를 전달받아서 처리한다. 따라서 최초의 컴포넌트 create()까지 들어가고 반환되면서 최종 create()까지 호출이 된다.
>

### 결과

displays.txt

```
time
weather
speed
speed
time
message
```

형태 → message ( time ( speed ( speed ( weather ( time ( HudDisplay ) ) ) ) ) )
<img width="712" alt="스크린샷 2021-10-03 오후 6 22 13" src="https://user-images.githubusercontent.com/42247724/135747719-5154b0bc-e98f-4f66-a89b-2a4b401de262.png">

displays.txt

```
message
speed
time
weather
time
```

형태 → time ( weather ( time ( speed ( message ( HudDisplay ) ) ) ) )
<img width="712" alt="스크린샷 2021-10-03 오후 6 23 27" src="https://user-images.githubusercontent.com/42247724/135747724-2609983d-5212-48a9-8a4e-3ee1d08dd608.png">
---

## 5. Factory Method Pattern

- 부모(상위) 클래스에 알려지지 않은 구체 클래스를 생성하는 패턴이며, 자식(하위) 클래스가 어떤 객체를 생성할지를 결정하도록 하는 패턴.
- 객체 생성용 인터페이스를 정의하고 해당 인스턴스를 상속한 서브클래스가 어떤 클래스를 인스턴스화(객체 생성) 할 지 결정할 수 있도록 한다.
- 팩토리 메소드는 객체 생성을 서브 클래스에서 할 수 있도록 미룰 수 있게 한다.

>
> #### 왜 굳이 자식 클래스에서 객체를 생성하도록 미루는 것이고 얻을 수 있는 장점이 뭘까?
실제 객체를 생성하기 위해서 new를 사용한다. new를 호출해서 객체를 생성하게 되는데 생성할 객체가 다양하고 추가/삭제 등 변경이 잦다면 어떻게 될까?

클라이언트 단의 잦은 코드 수정이 불가피하게 되고 이는 객체지향 설계 원칙인 OCP (변경에는 닫히고 확장에는 열려야 함)를 위반하게 될 것이다. 따라서, 생성을 구현과 분리해서 캡슐화 시키므로서 수정이 일어나는
곳을 최소화 하고 변경이 확산되는 것을 방지할 수 있다.

> #### 팩토리 메소드 패턴의 구조
<img width="500" src="https://user-images.githubusercontent.com/42247724/136726116-a3c04b05-94fe-4f87-933b-e17712387f28.png" alt="팩토리 메소드 패턴">

- Product : 팩토리 메소드 패턴으로 생성될 객체들의 공통 인터페이스.
    - 다이어그램에서 Shape Abstract Class가 담당하는 부분이다.
- ConcreteProduct : 구체적으로 객체가 생성되는 클래스.
    - Rectangle, Triangle ... 등 실제 생성되는 객체들이다. Product 인터페이스를 상속받는다.
    - 여기서 상속은 확장에 의의가 있는 것이 아니라 상위 클래스로 캡슐화 하는 용도이다.
- Creator : 팩토리 메소드를 갖는 클래스.
    - ShpaeFactory Interface가 해당 역활을 한다.
- ConcreteCreator : 팩토리 메소드를 구현하는 클래스이다. 해당 클래스에서 ConcreteProduct 객체를 생성한다.

### 다이어그램

<img width="1226" alt="스크린샷 2021-10-10 오후 11 18 08" src="https://user-images.githubusercontent.com/42247724/136699671-b8900067-4414-40ac-b0e4-a2101170fdc6.png">

### Main.java _ [코드](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap05_FactoryPattern/src/Main.java#L36)

```java
import java.awt.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        final Point[] RECTANGLE_POINTS = {
                new Point(100, 150),
                new Point(150, 100)
        };
        final Point[] TRAPEZOID_POINTS = {
                new Point(200, 150),
                new Point(280, 100),
                new Point(170, 100),
                new Point(250, 150)
        };
        final Point[] PARALLELOGRAM_POINTS = {
                new Point(330, 150),
                new Point(400, 100),
                new Point(300, 100),
                new Point(430, 150)
        };
        final Point[] TRIANGLE_POINTS = {
                new Point(225, 300),
                new Point(200, 250),
                new Point(250, 250)
        };
        final Point[] RIGHT_TRIANGLE_POINTS = {
                new Point(350, 300),
                new Point(300, 250),
                new Point(350, 250)
        };

        ArrayList<Shape> shapeList = new ArrayList<>();
        ShapeFactory factory;

        factory = new RectangularShapeFactory();
        shapeList.add(factory.create(Type.Rectangle.type, RECTANGLE_POINTS));
        shapeList.add(factory.create(Type.Trapezoid.type, TRAPEZOID_POINTS));
        shapeList.add(factory.create(Type.Parallelogram.type, PARALLELOGRAM_POINTS));

        factory = new TriangularShapeFactory();
        shapeList.add(factory.create(Type.Triangle.type, TRIANGLE_POINTS));
        shapeList.add(factory.create(Type.RightTriangle.type, RIGHT_TRIANGLE_POINTS));

        for (Shape s : shapeList) {
            System.out.println(s);
        }
    }
}
```

### RectangularShapeFactory.java _ [코드](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap05_FactoryPattern/src/RectangularShapeFactory.java#L3)

```java
import java.awt.*;

public class RectangularShapeFactory implements ShapeFactory {
    @Override
    public Shape create(String type, Point[] points) {
        if (type.equals(Type.Rectangle.type)) {
            return new Rectangle(type, points);
        } else if (type.equals(Type.Parallelogram.type)) {
            return new Parallelogram(type, points);
        } else if (type.equals(Type.Trapezoid.type)) {
            return new Trapezoid(type, points);
        }
        return null;
    }
}
```

- 팩토리 메소드 인터페이스를 상속받아서 팩토리 메소드를 구현한 클래스
- 여기서 타입에 맞는 객체를 생성하도록 한다. -> Rectangular 관련 타입만 생성함.

### 설명

다양한 도형 별 크기를 계산하기 위해 도형별 객체가 존재하고 도형별로 별도의 계산법을 사용해야하는 상황이다. 클라이언트에서 각 타입별로 객체를 선언해서 계산하지 않고 factory 패턴에 이를 맡긴다. 메인에서는
계산을 원하는 도형을 삼각형, 사각형에 따라 그에 맞는 factory에 보내서 만들도록 한다.

만약 다른 종류의 사각형이나 삼각형이 추가되더라도 클라이언트 코드는 변경없이 바로 추가만 해주면 된다. 그에 맞는 생성 로직은 Factory 패턴에서 담당한다.

각 도형들은
모두 [Shape 추상 클래스](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap05_FactoryPattern/src/Shape.java#L12) 를
상속하고, 공통으로 갖는 기능 및 변수를 선언하고 도형별로 다른 계산 법을 가지는 calcArea()는 추상 메소드로 선언한다. 이부분은 상속한 도형에서 override하여 구현한다.

[도형 객체](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap05_FactoryPattern/src/Triangle.java) 를 선언하는
Factory들은 [ShapeFactory 인터페이스](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap05_FactoryPattern/src/ShapeFactory.java)
를 상속받고 객체를 생성하는 create() 메소드를 구현한다.

Factory 내부에서 전달받는 타입에 맞게 객체를 생성하는 책임을 갖는다. (Ex. RectangularShapeFactory)

### 결과

```
Rectangle
P0: java.awt.Point[x=100,y=150]
P1: java.awt.Point[x=150,y=100]
area: 2500.0

Trapezoid
P0: java.awt.Point[x=200,y=150]
P1: java.awt.Point[x=280,y=100]
P2: java.awt.Point[x=170,y=100]
P3: java.awt.Point[x=250,y=150]
area: 4000.0

Parallelogram
P0: java.awt.Point[x=330,y=150]
P1: java.awt.Point[x=400,y=100]
P2: java.awt.Point[x=300,y=100]
P3: java.awt.Point[x=430,y=150]
area: 5000.0

Triangle
P0: java.awt.Point[x=225,y=300]
P1: java.awt.Point[x=200,y=250]
P2: java.awt.Point[x=250,y=250]
area: 1250.0

RightTriangle
P0: java.awt.Point[x=350,y=300]
P1: java.awt.Point[x=300,y=250]
P2: java.awt.Point[x=350,y=250]
area: 1250.0
```

---

## 6. Singleton Pattern

- 여러 객체가 생성되면 상태 관리가 어렵다.
- 이를 해결하기 위해 객체 생성자를 중앙에서 관리하는 방법.
- 객체가 한개이므로 항상 일관된 상태이다.
- **멀티 쓰레드에서는 문제가 될 수 있다. (해결방법 존재)**

#### 싱글톤 패턴 구현 방법

- private 디폴트 생성자를 만든다.
- 싱글톤 인스턴스를 저장하는 정적 멤버 변수를 생성한다.
- 싱글톤 인스턴스를 반환하는 정적 팩토리 메소드를 구현한다.

### ChocolateBoiler.java _ [코드](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap06_Singleton/src/ChocolateBoiler.java#L11)

```java
public class ChocolateBoiler {
    private static ChocolateBoiler instance = null;
    private boolean empty;
    private boolean boiled;

    private ChocolateBoiler() {
        empty = true;
        boiled = false;
    }

    public static ChocolateBoiler getInstance() {
        if (instance == null) {
            instance = new ChocolateBoiler();
        }
        return instance;
    }

    public boolean isEmpty() {
        return empty;
    }

    public boolean isBoiled() {
        return boiled;
    }

    public void fill() {
        if (isEmpty()) {
            empty = false;
            boiled = false;
        }
    }

    public void drain() {
        if (!isEmpty() && isBoiled()) {
            empty = true;
            boiled = false;
        }
    }

    public void boil() {
        if (!isEmpty() && !isBoiled()) {
            boiled = true;
        }
    }
}
```

### 멀티 쓰레드 환경에서의 문제점

- 싱글톤 인스턴스를 만드는 메소드 : getInstance()는 Critical Section으로서 멀티 쓰레드가 해당 구역에 대해 race condition 이 발생하게 되면 의도치 않은 결과가 발생될 수 있다.

#### 문제 코드 예시

```java
public class Main {
    public static void main(String[] args) {

        HashSet<String> set = new HashSet<>();

        for (int i = 0; i < 100; i++) {
            int tmp = i;
            new Thread(() -> {
                ChocolateBoiler instance = ChocolateBoiler.getInstance();
                set.add(instance.toString());
                System.out.println(tmp + ": " + System.currentTimeMillis() + " , " + set.size());
            }).start();
        }
    }
}
```

<img width="637" alt="스크린샷 2021-10-15 오전 11 47 51" src="https://user-images.githubusercontent.com/42247724/137424490-4e49ac51-e09f-449d-8f47-8b8c0177e3ab.png">

- 실제로 객체가 여러개 생성되어 Set에 저장된 것을 확인할 수 있다.

#### 해결방법 (1) - 동기화

- 쓰레드들이 해당 구역을 동시에 접근할 수 없도록, lock을 사용한다. -> Synchronize
- 문제점
    - 단, 이렇게 하면 해당 구역에 대해서는 순차적으로 수행되므로 비효율적일 수 있다.
    - 해당 Critical Section에 무거운 로직이 있다면 더욱 좋지 않다.

```java
// getInstance() 메소드 동기화
public synchronized static ChocolateBoiler getInstance(){
        if(instance==null){
        instance=new ChocolateBoiler();
        }
        return instance;
        }
```

<img width="637" alt="스크린샷 2021-10-15 오전 11 58 08" src="https://user-images.githubusercontent.com/42247724/137425339-c8dbfce3-7343-429c-a027-91b7320c902b.png">

- 객체의 개수가 하나만 생성된 것을 확인할 수 있다.

#### 해결방법 (2) - 미리 생성

- Synchronized를 사용하지 않고 싱글톤 인스턴스를 프로그램이 생성될 때 같이 생성되도록 한다.
- 미리 싱글톤 객체를 생성해 버리므로 null 체크 부분이 사라지게 되고 동기화의 필요성도 없어진다.
- 문제점
    - 단, 사용하지 않을 수도 있는 인스턴스를 프로그램 시작시 생성하므로 메모리 낭비가 일어날 수 있다.
    - 객체를 생성하는데 시간을 소요하므로 프로그램의 시작이 느려질 수 있다.

```java
private static ChocolateBoiler instance=new ChocolateBoiler();

public static ChocolateBoiler getInstance(){
        return instance;
        }
```

<img width="637" alt="스크린샷 2021-10-15 오후 12 01 11" src="https://user-images.githubusercontent.com/42247724/137425593-bbd8902c-1084-4173-bb79-4e4abf036660.png">

- 객체가 하나만 생성되는 것을 확인할 수 있다.

#### 해결방법 (3) - DCL & volatile

- Double Checking Locking을 사용해서 동기화 되는 부분을 줄임으로서 동기화를 개선한 방법이다.
- 인스턴스가 생성되어 있는지 아닌지를 확인해서 생성되있지 않은 경우에만 동기화를 한다.
- 생성 여부를 확인하고 되어있지 않을 때만 Lock을 걸게되므로 속도를 개선할 수 있다.

```java
private static volatile ChocolateBoiler instance=null;

public static ChocolateBoiler getInstance(){
        if(instance==null){
synchronized (ChocolateBoiler.class){
        if(instance==null)
        instance=new ChocolateBoiler();
        }
        }
        return instance;
        }
```

<img width="637" alt="스크린샷 2021-10-15 오후 12 03 38" src="https://user-images.githubusercontent.com/42247724/137425749-4628c12d-7a89-4283-85f2-b782b331fe5e.png">

- 객체가 하나만 생성되는 것을 확인할 수 있다.

> ### Volatile이란?
>
> 멀티 프로세스 환경에서 멀티 쓰레드가 구동되면 각각의 프로세스에 쓰레드가 수행될 수 있다.
> 이때 각 프로세스에는 속도가 상대적으로 느린 메모리를 보충하기 위해 캐시메모리가 존재한다.
>
> 하지만 캐시메모리는 프로세스 간 값이 다를 수 있다는 단점이 존재한다.
> 또한 메모리에 저장된 값이랑 다를 가능성 또한 존재한다.
>
> 이를 보완하기 위해 **CPU 캐시메모리에 저장하지 않고 메모리에서 값을 읽고 저장해 사용하도록 명시하는 것**이 "volatile"이다.

### 결과

- 싱글톤 패턴을 이용하여 무분별한 객체 생성을 방지할 수 있다. 하지만 멀티 프로세스 & 멀티 쓰레드 환경에서 동기화 문제가 발생할 수 있어서 이에 대한 해결방법이 필요했다.
- 해결방법으로는 1. synchronized, 2. 선언과 동시에 생성, 3. DCL & volatile 이 있었다. 각 해결방법에는 장단점이 존재하므로 적절하게 상황에 맞게 사용하면 되겠다.

---

## 7. DAO Pattern

> ### DAO 패턴의 사용 목적
> 비지니스 로직과 DB를 분리하기 위해서 사용하는 패턴이다. DAO는 Data Access Object의 약자로 DB의 접근을 전담하게 된다.
>
> DB를 사용하는 방법이 변경되더라도 클라이언트 로직이 변경되지 않도록 DB 로직을 캡슐화 하여 분리하는 방법.

> ### 설계
> DAO :  기본적인 CRUD 인터페이스를 제공한다.
>
> DaoImpl : DAO 인터페이스를 구현한 구체 클래스이다.
>
> Value Object : DAO를 사용하여 데이터를 저장하는 POJO (plain old java obj)

> ### JDBC (Java Database Connectivity)
> 데이터베이스 비종속적 표준 자바 API이다. 다양한 DB를 동일한 인터페이스로 사용할 수 있도록 추상화 해준다.
> 각종 DB는 JDBC Driver를 통해서 연결하고 사용하게 된다.
>
> ### ODBC
> 예전 다양한 DB (oracle, msSql, mySql, h2 등)이 존재했을 때 각기 다른 인터페이스를 갖고 있었다. 이를 하나의
> 인터페이스로 통합하여 사용하기 위해 나온 것이 ODBC이다. windows 환경에서 DB들에 대해 공통의 인터페이스를 제공하기 위해 나온
> 것이라면 JDBC는 java 환경에서 DB들을 하나의 API로 접근하기 위해 개발된 것이다.
>
> ![IMG_A26350259277-1](https://user-images.githubusercontent.com/42247724/139358120-931f0f4b-9ad0-4e06-8cd3-3439aa6ca90e.jpeg)

### JDBC

1. **DB에 연결**
    - DriverManager.getConnection("DB URL") 을 통해서 DB에 연결한다.
    - Connection 인터페이스를 반환 받는다.
    - DB URL이 필요하다
        - MySql : "jdbc:mysql://localhost:3306/" + "db_name"
        - Oracle : "jdcc:oracle:thin:@//localhost:1521/" + "db_name"
2. **Connection 인터페이스**
    - 자바 Application과 DB를 연결한 **세션 유지 및 쿼리를 실행**한다.
    - 쿼리를 실행시킬 수 있는 Statement, PreparedStatement를 생성하는 Factory 이다.
    - createStatement() : SQL 쿼리문을 실행할 수 있는 Statement 인터페이스 객체를 생성한다.
    - close() : DB 연결 세션을 종료한다.
3. **Statement 인터페이스**
    - SQL 쿼리를 DB에서 실행시킨다.
    - SELECT 쿼리 수행 시 ResultSet 인터페이스 객체를 생성하는 Factory이다.
    - executeQuery("sql") : SELECT 쿼리를 수행하고 ResultSet을 반환한다.
    - executeUpdate("sql") : 그 외 쿼리를 수행한다. 반환값은 따로 없다고 봐도 무방.
4. **ResultSet**
    - 테이블의 한 행을 가리키는 커서이다.
    - 커서는 첫 번째 행 이전을 가리키고 있고 next()를 이용해서 다음 데이터가 존재하는지 확인하고 접근해야한다.
    - next() : 현재 위치에서 다음 행으로 이동한다. 가능 여부에 따라 T/F를 반환한다.
    - getInt("colIndex" or "colName") : 컬럼의 인덱스 또는 이름을 주면 해당 컬럼 데이터를 정수로 반환한다.
    - getString("colIndex" or "colName") : 똑같은데 문자열로 반환한다.

### 3가지 방식으로 구현

1. AddressBook_withoutDao
    - DB는 사용하지만 DAO 없이 구현
    - DB는 Sqlite 사용
2. AddressBook_withoutDB
    - DB는 사용하지않고 DAO 를 사용하여 구현
    - DB 대신 자료구조 ArrayList 사용
3. AddressBook_withDaoDB
    - DB & DAO 둘다 사용하여 구현

## 1. AddressBook_withoutDao (DAO x, DB o)

- **DB는 사용하지만 DAO 패턴은 사용하지 않는** 경우

#### Main.java _ [링크](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap07_DaoPattern/src/AddressBook_withoutDAO.java)

```java
import java.sql.*;

public class AddressBook_withoutDAO {
    final static String DB_FILE_NAME = "addressBook.db";
    final static String DB_TABLE_NAME = "persons";

    public static void main(String[] args) {
        Connection connection = null;
        ResultSet rs = null;
        Statement statement = null;

        try {
            connection = DriverManager.getConnection(
                    "jdbc:sqlite:" + DB_FILE_NAME
            );
            statement = connection.createStatement();
            statement.setQueryTimeout(30);

            final String table = "(ID INTEGER PRIMARY KEY AUTOINCREMENT, name text, address text)";

            statement.executeUpdate(
                    "DROP TABLE IF EXISTS " + DB_TABLE_NAME
            );
            statement.executeUpdate(
                    "CREATE TABLE " + DB_TABLE_NAME + table
            );

            System.out.println("---inserting ...");
            statement.execute(
                    "INSERT INTO persons(name, address) VALUES('woonsik choi', '135 anyang')"
            );
            statement.execute(
                    "insert into persons(name, address) values('boonsik kim', '531 gangnam')"
            );

            System.out.println("---finding all ...");
            rs = statement.executeQuery(
                    "select * from persons where id < 4 order by id"
            );
            while (rs.next()) {
                System.out.println(rs.getInt("ID")
                        + " "
                        + rs.getString("name")
                        + " "
                        + rs.getString("address"));
            }

            System.out.println("---updating ...");
            statement.execute(
                    "update persons SET name = 'handsome guy woonsik' where id = 1"
            );

            System.out.println("---see if updated ...");
            rs = statement.executeQuery("" +
                    "select * from persons where id = 1"
            );
            while (rs.next()) {
                System.out.println(rs.getInt("ID")
                        + " "
                        + rs.getString("name")
                        + " "
                        + rs.getString("address"));
            }

            System.out.println("---deleting ...");
            statement.execute(
                    "delete from persons where id = 1"
            );

            System.out.println("---finding all after delete ...");
            rs = statement.executeQuery(
                    "select * from persons where id < 4 order by id"
            );
            while (rs.next()) {
                System.out.println(rs.getInt("ID")
                        + " "
                        + rs.getString("name")
                        + " "
                        + rs.getString("address"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
```

> #### 결과
> <img width="417" alt="스크린샷 2021-10-29 오전 10 37 29" src="https://user-images.githubusercontent.com/42247724/139359144-4aa7d723-2001-4dbe-baac-68c3b79b1ab0.png">
>
> <img width="417" alt="스크린샷 2021-10-29 오전 10 39 15" src="https://user-images.githubusercontent.com/42247724/139359163-90ed6ab7-6bc5-4460-aba7-2cef048e0bfb.png">
> persons 테이블에 person 정보가 정상적으로 Create, Read, Update, Delete 되는것을 확인할 수 있다.
>
> #### 단점
> DB 접근 쿼리문과 비지니스 로직이 하나로 강하게 결합되어있는 것을 확인할 수 있다. DB에서 데이터를 가져오는 과정에 변경이 생기면 자연스럽게
> 클라이언트 코드도 변경이 불가피하고, 쿼리문이 반복되면 코드의 중복이 발생하게 된다.
>
> DB에 접근해서 값을 가져오는 코드와 클라이언트 코드를 분리할 필요성이 보인다.

## 2. AddressBook_withoutDB (DAO o, DB x)

- **DB는 사용하지 않고 DAO 패턴은 사용**하는 경우
- DB에 넣었던 튜플을 하나의 객체화하여 관리한다.
- DB대신 자료구조 List를 사용하여 객체에 대해 CRUD한다.

#### Main.java _ [링크](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap07_DaoPattern/src/AddressBook_withoutDB.java)

#### Person 객체 클래스 (POJO)

```java
public class Person {
    private static int count = 0;
    private int id;
    private String name;
    private String address;

    public Person(String name, String address) {
        this.name = name;
        this.address = address;
        this.id = ++count;
    }

    public Person(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person> " +
                "id=" + id +
                ", name='" + name + "'" +
                ", address='" + address + "'";
    }
    /*
        getter, setter 생략
     */
}
```

#### PersonDao Interface 클래스 (DAO)

```java
import java.util.List;

public interface PersonDao {
    void insert(Person person);

    List<Person> findAll();

    Person findById(int id);

    void update(int id, Person person);

    void delete(Person person);

    void deleteById(int id);
}
```

#### PersonDaoImpl_withoutDB 클래스 (DAO 구체 클래스 with List)

```java
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PersonDaoImpl_withoutDB implements PersonDao {
    List<Person> personList;

    public PersonDaoImpl_withoutDB() {
        this.personList = new ArrayList<>();
    }

    @Override
    public void insert(Person person) {
        personList.add(person);
    }

    @Override
    public List<Person> findAll() {
        return personList;
    }

    @Override
    public Person findById(int id) {
        return personList.stream()
                .filter(p -> p.getId() == id)
                .collect(Collectors.toList()).get(0);
    }

    @Override
    public void update(int id, Person person) {
        personList.stream()
                .filter(o -> o.getId() == id)
                .findFirst().ifPresent(p -> {
                    p.setName(person.getName());
                    p.setAddress(person.getAddress());
                });
    }

    @Override
    public void delete(Person person) {
        deleteById(person.getId());
    }

    @Override
    public void deleteById(int id) {
        personList.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .map(o -> personList.remove(o));
    }
}
```

## 3. AddressBook_withDaoDB (DAO o, DB o)

- DB도 사용하고, DAO도 사용하는 방식
- 구체 클래스의 구현 코드만 변경하면 된다.
- Person 객체를 그대로 사용한다.
- PersonDao 인터페이스도 그대로 사용한다.
- PersonDaoImpl 클래스만 DB를 사용하도록 변경한다.

#### Main.java _ [링크](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap07_DaoPattern/src/AddressBook_withDaoDB.java)

#### PersonDaoImpl 클래스 (DAO 구체 클래스 with DB)

```java
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDaoImpl implements PersonDao {
    final static String DB_FILE_NAME = "addressBook.db";
    final static String DB_TABLE_NAME = "persons";

    Statement statement = null;
    Connection connection = null;
    ResultSet rs = null;

    public PersonDaoImpl() {
        final String table = " (ID INTEGER PRIMARY KEY AUTOINCREMENT, name text, address text)";
        try {
            this.connection = DriverManager.getConnection(
                    "jdbc:sqlite:" + DB_FILE_NAME
            );
            this.statement = connection.createStatement();
            this.statement.setQueryTimeout(30);

            statement.executeUpdate(
                    "DROP TABLE IF EXISTS " + DB_TABLE_NAME
            );
            statement.executeUpdate(
                    "CREATE TABLE " + DB_TABLE_NAME + table
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Person person) {
        try {
            String format = "INSERT INTO %s VALUES(%d, '%s', '%s')";
            String query = String.format(format,
                    DB_TABLE_NAME, person.getId(), person.getName(), person.getAddress());
            statement.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Person> findAll() {
        ArrayList<Person> people = new ArrayList<>();
        try {
            String format = "SELECT * FROM %s";
            String query = String.format(format, DB_TABLE_NAME);
            rs = statement.executeQuery(query);

            while (rs.next()) {
                people.add(new Person(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address"))
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return people;
    }

    @Override
    public Person findById(int id) {
        Person person = null;
        try {
            String format = "SELECT * from %s where id=%d";
            String query = String.format(format, DB_TABLE_NAME, id);
            rs = statement.executeQuery(query);
            if (rs.next()) {
                person = new Person(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address")
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return person;
    }

    @Override
    public void update(int id, Person person) {
        Person p = findById(id);

        if (p != null) {
            try {
                String format = "UPDATE %s SET name='%s', address='%s' WHERE id=%d";
                String query = String.format(
                        format, DB_TABLE_NAME, person.getName(), person.getAddress(), person.getId()
                );
                statement.executeUpdate(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Person person) {
        deleteById(person.getId());
    }

    @Override
    public void deleteById(int id) {
        try {
            String format = "DELETE FROM %s where id=%d";
            String query = String.format(format, DB_TABLE_NAME, id);
            statement.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
```

> #### 결과
> <img width="598" alt="스크린샷 2021-10-31 오후 12 42 27" src="https://user-images.githubusercontent.com/42247724/139566406-261e7849-a06d-4708-89de-a03f956f1438.png">

### 다이어그램

<img width="585" alt="스크린샷 2021-10-31 오후 12 32 02" src="https://user-images.githubusercontent.com/42247724/139566090-9a50d163-9a3c-4a83-a5cd-2854371e568b.png">

### 설명

- 기존 Main 클래스에서 DB에 직접 CRUD하던 튜플을 Person 객체로 변환했다.
- DB에 Person 객체를 CRUD하기 위해 Person DAO 인터페이스를 만들었다.
- PersonDao 인터페이스의 구체 클래스 PersonDaoImpl을 구현했다.

### 결과

- DAO 인터페이스를 사용함으로서 비지니스 로직과 CRUD 구현 로직을 별도로 분리할 수 있었다.
- 어떤 DB를 사용하는지가 애플리케이션 코드에 영향을 주지 않도록 했다.
- DI를 이용하면 생성자 부분도 필요하지 않으므로 완전한 캡슐화를 이루고 분리원칙을 지킬 수 있다.

---

## 7-2 DAO Pattern Refactoring with Generic

- Generic을 사용하여 DAO 인터페이스와 DaoImpl 클래스를 구현 클래스와 분리한다.
- DAO 인터페이스와 DaoImpl 클래스를 **일반화**하여 사용할 수 있다. 따라서 다른 클래스에서도 상속하여 원하는 대로 함수를 오버라이드하여 사용할 수 있다.
- DAO 인터페이스 <- DaoImpl 클래스 <- CustomClassUseDAO 클래스로 상속관계가 이뤄진다.
- DAO에 사용될 Data와 Key의 타입을 CustomClassUseDao에서 정의한다.
    - 즉 자신에게 맞게 저장한 객체의 클래스와 Key 자료형을 선언해주면 된다.

### 다이어그램

<img width="520" alt="스크린샷 2021-11-04 오후 12 08 20" src="https://user-images.githubusercontent.com/42247724/140251377-f450a98e-eb69-4760-ba03-8ab1172a36b0.png">

### WebSitedIdManage 클래스 _ [Main 코드](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap07_DaoPattern/src/SiteIdPw_DaoPattern/WebSiteIdPwManage.java#L5)

```java
class WebSitedIdManage {
    public static void main(String[] args) {
        // 클래스에 맞게 구현한 구체 클래스를 구현체로 사용
        DAO<SiteIdPwInfo, String> userSiteIdPwInfoDAO = new IdPwInfoDaoImpl("idPasswordTable");

        // insert
        userSiteIdPwInfoDAO.insert(
                new SiteIdPwInfo("https://www.smu.ac.kr", "smu", "abcde")
        );

        // findByKey
        System.out.println(userSiteIdPwInfoDAO.findByKey("https://www.smu2.ac.kr"));
        // 그외 생략..
    }
}
```

### DAO Interface _ [코드](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap07_DaoPattern/src/SiteIdPw_DaoPattern/DAO.java)
```java
import java.util.List;

public interface DAO<D, K> {
    // crud
    void insert(D data);

    D findByKey(K key);

    List<D> findAll();

    void update(D data);

    void delete(D data);

    void deleteByKey(K key);
}
```

### DaoImpl _ [코드](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap07_DaoPattern/src/SiteIdPw_DaoPattern/DaoImpl.java)

- 일반화가 가능하도록 구현 코드를 작성한다.
- 일반화가 불가능한 부분은 하위 클래스에게 구현을 맡긴다. -> abstract 메소드를 사용한다.
- 하위 클래스와 철저하게 분리하여 작성해야한다.

```java
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class DaoImpl<D extends DbData<K>, K> implements DAO<D, K> {

    String dbTableName;
    public abstract Statement getStatement();
    public abstract String getInstanceValueQuery(D data);
    public abstract String getUpdateInstanceValueQuery(D data);
    public abstract D getInstance(ResultSet resultSet);
    public abstract String getKeyColumnName();

    public DaoImpl(String dbTableName) {
        this.dbTableName = dbTableName;
    }

    @Override
    public void insert(D data) {
        try {
            String format = "INSERT INTO %s VALUES(%s)";
            String query = String.format(
                    format, dbTableName, getInstanceValueQuery(data)
            );
            getStatement().executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public D findByKey(K key) {
        try {
            ResultSet rs;
            String format = "SELECT * from %s where %s = '%s'";
            String query = String.format(format, dbTableName, getKeyColumnName() , key.toString());
            rs = getStatement().executeQuery(query);
            if (rs.next()) {
                return getInstance(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<D> findAll() {
        try {
            List<D> dataList = new ArrayList<>();
            ResultSet rs;

            String format = "SELECT * from %s";
            String query = String.format(format, dbTableName);
            rs = getStatement().executeQuery(query);

            while (rs.next()) {
                dataList.add(getInstance(rs));
            }
            return dataList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(D data) {
        if (findByKey(data.getKey()) != null) {
            try {
                String format = "update %s SET %s where '%s' = '%s'";
                String query = String.format(
                        format, dbTableName, getUpdateInstanceValueQuery(data),
                        getKeyColumnName(), data.getKey().toString()
                );
                getStatement().executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(D data) {
        deleteByKey(data.getKey());
    }

    @Override
    public void deleteByKey(K key) {
        try {
            String format = "delete from %s where %s = '%s'";
            String query = String.format(
                    format, dbTableName, getKeyColumnName(), key
            );
            getStatement().executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

### IdPwInfoDaoImpl _ [코드](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap07_DaoPattern/src/SiteIdPw_DaoPattern/IdPwInfoDaoImpl.java)

- DAO를 사용할 Object 클래스에 맞게 abstract class를 오버라이딩 하는 클래스이다.
- 클래스 명은 사용자가 원하는대로 수정하게 된다.
- DaoImpl에서 구체화하지 못한 메소드를 impl한다.

```java
import java.sql.*;

public class IdPwInfoDaoImpl extends DaoImpl<SiteIdPwInfo, String> {
    final static String DB_FILE_NAME = "IdPassword.db";

    Connection connection = null;
    Statement statement = null;

    public IdPwInfoDaoImpl(String dbTableName) {
        super(dbTableName);

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_FILE_NAME);
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            final String table = "(url text PRIMARY KEY, id text, password text)";
            statement.executeUpdate("DROP TABLE IF EXISTS " + dbTableName);
            statement.executeUpdate("CREATE TABLE " + dbTableName + table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Statement getStatement() {
        return statement;
    }

    @Override
    public String getInstanceValueQuery(SiteIdPwInfo data) {
        String format = "'%s', '%s', '%s'";

        return String.format(
                format,
                data.getKey(),
                data.getUserId(),
                data.getUserPw()
        );
    }

    @Override
    public String getUpdateInstanceValueQuery(SiteIdPwInfo data) {
        String format = "url='%s', id='%s', password='%s'";
        return String.format(
                format,
                data.getKey(),
                data.getUserId(),
                data.getUserPw()
        );
    }

    @Override
    public SiteIdPwInfo getInstance(ResultSet resultSet) {
        try {
            String url = resultSet.getString("url");
            String uid = resultSet.getString("id");
            String upw = resultSet.getString("password");
            return new SiteIdPwInfo(url, uid, upw);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getKeyColumnName() {
        return "URL";
    }
}
```

### DbData 인터페이스

- 실제 CRUD에 사용되는 Object 클래스가 implement하는 인터페이스이다.
- Object 클래스의 key 타입을 통일하기 위해 사용한다.
- DaoImpl의 Data가 이 인터페이스를 상속하고 있으므로 Object 클래스도 똑같이 상속하여
Data로서 사용할 수 있게 한다.

```java
public interface DbData<K> {
    K getKey();
}
```

### SiteIdPwInfo 클래스 _ [코드](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap07_DaoPattern/src/SiteIdPw_DaoPattern/SiteIdPwInfo.java#L3)

- CRUD의 대상이 되는 Objcet 클래스이다.
- DbData를 implements하여 DAO의 Data로 사용할 수 있게한다.
- 필요한 정보 및 Getter/Setter를 갖고 있다.

```java
public class SiteIdPwInfo implements DbData<String>{
    private String url;
    private String userId;
    private String userPw;

    public SiteIdPwInfo(String url, String userId, String userPw) {
        this.url = url;
        this.userId = userId;
        this.userPw = userPw;
    }

    @Override
    public String getKey() {
        return this.url;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    @Override
    public String toString() {
        return "UserSiteIdPwInfo{" +
                "url='" + url + '\'' +
                ", userId='" + userId + '\'' +
                ", userPw='" + userPw + '\'' +
                '}';
    }
}
```

### 설명

위와 같이 DAO <- DaoImpl <- IdPwInfoDaoImpl 관계를 둠으로서 
DAO가 필요한 객체마다 동일한 인터페이스를 상속 받아서 필요한 부분만 Override하여 사용할
수 있다.

### 결과

- 한 층 더 추상화가 되었다.
- Dao, DaoImpl이 일반화 되어 더욱 효과적으로 코드의 중복을 제거해 사용할 수 있다.

---

## 8. Adapter Pattern

- 클래스의 인터페이스를 클라이언트가 원하는 형태의 또 다른 인터페이스로 변환한다.
- Adapter는 호환되지 않는 인터페이스 때문에 동작하지 않는 클래스를 동작할 수 있게 만들어준다.
- Object Wrapping의 역활을 해서 서로 호환되지 않는 인터페이스를 연결할 수 있게 해준다.

### 설명

- 문제: 사용 객체의 API가 서로 다른 경우
- 해결: 함수를 변환하는 객체를 중간에 넣어줌으로서 변경을 최소화한다.

### 예시
- 배열과 리스트에 대한 다른 사용 메소드가 있다.
- 배열:
  - arr[10]으로 선언
  - arr[0]로 접근
  - length()로 크기 얻음
  - 중간 삽입 시 새로운 배열 선언 후 앞 뒤에 기존 값을 넣어줌
  - 중간 값 삭제 시 새로운 배열 선언 후 제거한 값을 제외하고 넣어줌
- 리스트
  - LinkedList(), ArrayList()로 선언
  - get(), remove(), size(), add()로 조회, 삭제, 크기 얻기, 추가를 한다. 

위와 같이 서로 다른 인터페이스를 갖고 있을 때 ListAdapter 클래스를 만들어서 배열이 List 
인터페이스를 통해 사용될 수 있게 할 수 있다.

---

## 9. Facade Pattern

- 서브 시스템에 있는 여러개의 인터페이스를 통합하여 하나의 인터페이스를 제공하는 패턴이다.
- 퍼사드는 서브 시스템을 쉽게 사용할 수 있도록 해주는 고급 수준의 인터페이스를 정의한다.

### 설명

- 문제: 서브 시스템이 너무 많고 순서 등에 있어 사용하기가 복잡한 경우
- 설명: 
  - 단순한 인터페이스를 제공하는 객체를 중간에 넣어준다. 
  - 서브시스템의 일련의 인터페이스에 대한 통합된 인터페이스를 제공한다.

### 예시
- 커피 머신의 내부 인터페이스
  - 원두 넣기
  - 원두 갈기
  - 원두 추출
  - 뜨거운 물 붓기
  - 종료 알림
- 위 와 같은 커피 머신 내부 인터페이스를 하나의 인터페이스로 통합 제공한다.
  - 아메리카노 커피 뽑기 (위의 모든 인터페이스를 호출해서 진행함)
  - 이와 동시에 내부 인터페이스에도 접근 가능하다.
    - 샷 추가 (원두 추출)

---

## 10. Command Pattern

- 요구사항(요청, 명령)을 객체로 캡슐화 시킨다. 이렇게 함으로써 주어진 여러 기능을 실행할 수 있는 재사용성
이 높은 클래스를 설계할 수 있다.  
  - 이벤트가 발생했을 때 실행될 기능이 다양하면서도 변경이 필요한 경우에 이벤트를 발생시키는 클래스를
  변경하지 않고 재사용할 때 유용하다.
- 요구사항 (기능)을 캡슐화 함으로써 기능의 실행을 요구하는 호출자 클래스와
실제 기능을 실행하는 수신자 클래스 간의 의존성을 분리할 수 있다.
- 결과적으로 **다양한 기능이 추가되어도 호출자는 수정없이 기능을 사용**할 수 있다.

#### 예시

- 계산기
  - 숫자 버튼과 연산자 버튼, = 버튼 등 버튼마다 다른 기능을 갖고 있다.
- 통합 리모콘
  - 티비 on/off, 전등 on/off, 에어컨 on/off 을 통합으로 관리하는 
  리모콘의 경우 다 같은 버튼이지만 기능이 다르다.

#### 정리

즉, 각기 다른 기능들을 하나로 캡슐화 하여 버튼이라는 기능과 커플링이 가능하게 하는
것이 목적이다. 버튼은 기능이 무엇인지 신경쓰지 않고 수행만 하게 하면 된다.

사용하는 객체마다 다른 api를 하나의 인터페이스로 캡슐화 하여 실행과 요청을 분리한다.

## 계산기 코드

### 설명

- 숫자, 연산자, =, c (클리어) 등 다른 기능들에 대해서 하나의 버튼으로 
수행할 수 있게한다.
- 숫자는 NumberBtnCommand, 그외 연산자는 OperatorBtnCommand 로 기능수행
- 둘다 Command 라는 인터페이스를 implements하여 캡슐화 한다. 이제 기능 수행은
execute()라는 단일 메소드를 통해서만 접근하게 된다.
- CommandButton을 상속하여 버튼 역활을 갖게 한다.
- Main에서는 어떤 버튼이건 Command 인터페이스라면 execute()하게 한다.
구현체가 알아서 적절한 기능을 하게 된다.

### 다이어그램
<img width="646" alt="스크린샷 2021-11-12 오전 11 45 26" src="https://user-images.githubusercontent.com/42247724/141401563-dcf39138-bb1a-4cde-96f7-56aa32974b4c.png">

### Command Interface [코드](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap09_CommandPattern/src/Command.java)

```java
public interface Command {
    void execute();
}
```
- 다양한 기능들을 하나의 커맨드로 추상화 시키기 위한 인터페이스이다.

### NumberBtnCommand class [코드](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap09_CommandPattern/src/NumberBtnCommand.java#L19) 

```java
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
            int tmp = calculator.getOperand2();
            tmp *= 10;
            tmp += Integer.parseInt(getText());
            display.setText(tmp+"");
            calculator.setOperand2(tmp);
            calculator.setOperand2Set(true);
        } else {
            int tmp = calculator.getOperand1();
            tmp *= 10;
            tmp += Integer.parseInt(getText());
            display.setText(tmp+"");
            calculator.setOperand1(tmp);
            calculator.setOperand1Set(true);
        }
    }
}
```
- 숫자가 왔을 때 Command 인터페이스의 execute()를 구현하여 적절한 수행을 한다.

### OperatorBtnCommand class [코드](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap09_CommandPattern/src/OperatorBtnCommand.java#L19)

```java
public class OperatorBtnCommand extends CommandButton implements Command {
    Calculator calculator;
    JLabel display;

    public OperatorBtnCommand(
            String text, Dimension dimension,
            ActionListener listener, Calculator calculator, JLabel display)
    {
        super(text, dimension, listener);
        this.calculator = calculator;
        this.display = display;
    }

    @Override
    public void execute() {
        String r = "0";
        if (calculator.isOperand1Set() && !calculator.isOperand2Set()
                && !getText().equals("c") && !getText().equals("=")) {
            calculator.setOperatorSet(true);
            calculator.setOperator(getText().charAt(0));
            return;
        } else if (calculator.isOperand1Set() && calculator.isOperatorSet() && calculator.isOperand2Set()) {
            if (getText().equals("=")) {
                int f = calculator.getOperand1();
                int b = calculator.getOperand2();
                System.out.println(f + " " + calculator.getOperator() + " " + b);
                switch (calculator.getOperator()) {
                    case '+':
                        r = (f + b) + "";
                        break;
                    case '-':
                        r = (f - b) + "";
                        break;
                    case '*':
                        r = (f * b) + "";
                        break;
                    case '/':
                        if (b == 0) {
                            r = null;
                            break;
                        } else r = (f / b) + "";
                }
            }
        }
        display.setText(r);
        calculator.setOperand1Set(false);
        calculator.setOperand2Set(false);
        calculator.setOperatorSet(false);
        calculator.setOperand1(0);
        calculator.setOperand2(0);
    }
}
```
- 숫자 입력 상황과 연산자 입력 상황에 따라 적절히 계산한다.
- +, -, *, /, =, c 를 처리하게 된다.

### Main class [코드](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap09_CommandPattern/src/CalcGUIV1.java#L70)

```java
public class CalcGUIV1 extends JFrame implements ActionListener {

    // ~ 생략
    String[] buttonText = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", " ", " ", "+", "-", "*", "/", "c" , "=" };
    JLabel display = new JLabel();

    CalcGUIV1() {
        super("calculator");
        calculator = new Calculator();
        // display 설정 생략
        clear();
    }

    public JPanel getDisplayPanel() {
        // 생략
        return displayPanel;
    }
    public JPanel getButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5,3,10,5));

        /* 
        숫자용 버튼과 연산자용 버튼을 만든다. 이렇게 함으로서 execute() 명령어 하나로
        필요한 기능을 수행하게 할 수 있다. 
         */
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

    // 이곳에서 버튼이 눌리면 execute() 명령을 수행한다. 기능 별로 구현코드가 작동된다.
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
```
- Command 패턴을 사용함으로서 실행 코드와 요청을 분리할 수 있다.
- 버튼이 눌렸을 때 수행되어야 할 기능은 Command 인터페이스를 구현한 클래스에서 작성하므로
기능이 수정/추가 되어도 서비스 로직에서는 수정이 필요 없고 구현 클래스만 건들면 된다.

### 결과
![ezgif com-gif-maker](https://user-images.githubusercontent.com/42247724/141403689-c0ea9b22-41c2-488a-abf5-4883cd8a3bd2.gif)

---

## 11. State Pattern

#### 상태(State)란?
하나의 오브젝트가 시점에 따라 특정 상태에 있어야 한다. 처음에 가지게 되는 
초기 상태 또는 상황에 따라 여러 상태 중 하나의 상태를 가질 수 있다.

한 상태에서 다른 상태로 전환하는 것을 전이(Transition) 이라고 한다. 예를
들어 게임 캐릭터의 경우 걷기, 뛰기, 멈추기, 공격하기, 방어하기 등이 있고, 가전제품의 경우
on, off, sleep 등이 있을 수 있다.

이러한 다양한 상태를 if 문으로 상태를 통제하는 방식은 추가적인 상태가 생기거나
구현의 변경이 발생했을 때 여러곳의 코드가 변경될 수 있다는 문제가 있다.

이를 해결하기 위해 **상태를 한 곳에서 관리하기 위한 패턴**이 상태 패턴이다.

### 설명

두개의 인자와 한개의 연산자를 받고 "="을 입력 받으면 계산을 하는 Calculator를 구현한다. 이때 계산기의
상태로는 첫번째 인자 없음, 연산자 없음, 두번째 인자 없음, 계산("=") 인자 없음으로 총 4개의 상태가 존재하게 된다.

또한, 각 상태 별로 숫자 입력, 연산자 입력, 종료 입력의 3가지 전이(Transition)가 발생할 수 있다.

따라서, 모든 상태들을 하나의 상태 인터페이스를 상속하게 하여 각 상태 별로 전이에 대해 적절히 구현하도록 한다. 

### 다이어그램

<img width="908" alt="스크린샷 2021-11-19 오후 3 00 56" src="https://user-images.githubusercontent.com/42247724/142573226-7ca99bf7-2055-4ffb-9ce4-4ab854484580.png">

- 모든 상태들은 STATE 인터페이스를 implements 하도록 한다.
- 공통 기능인 quitInput()은 default 메소드로 만들었다.
- 상태 간 상태가 변경 되는 Transition을 인터페이스에 작성하고 이를 각자 구현한다.

#### Calculator.class [코드](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap10_StatePattern/src/Calculator.java#L50)

```java
public class Calculator {
    int operand1; // 첫 번째 피 연산자값 저장
    int operand2; // 두 번째 피 연산자값 저장
    char operator; // 사칙 연산자 저장

    BufferedReader br;
    STATE noOperandOne;
    STATE noOperandTwo;
    STATE noOperator;
    STATE calculate;
    STATE state;

    public Calculator() {
        this.noOperandOne = new noOperandOne(this);
        this.noOperandTwo = new noOperandTwo(this);
        this.noOperator = new noOperator(this);
        this.calculate = new calculate(this);
        this.state = noOperandOne;
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    public void setState(STATE state) {
        this.state = state;
    }

    void printOutResult() {
        switch (operator) {
            case '+' -> System.out.printf("%d + %d = %d\n", operand1, operand2, operand1 + operand2);
            case '-' -> System.out.printf("%d - %d = %d\n", operand1, operand2, operand1 - operand2);
            case '*' -> System.out.printf("%d * %d = %d\n", operand1, operand2, operand1 * operand2);
            case '/' -> System.out.printf("%d / %d = %d\n", operand1, operand2, operand1 / operand2);
        }
    }

    boolean run() throws IOException {
        System.out.println("정수 또는 +, -, *, /, = 기호 중 한 개를 입력하세요. (종료 : q)");
        String input = br.readLine();
        char ch;
        if (input.equals("")) {
            ch = '=';
            input = "=";
        } else ch = input.charAt(0);

        if (ch == 'q' || ch == 'Q') {
            state.quitInput();
            return false;
        } else if (ch >= '0' && ch <= '9') {
            state.decimalInput(input);
        } else {
            state.operatorInput(input);
        }
        return true;
    }
}
```
- 현재 state를 if문 등을 통해 확인하지 않고 입력 값에 따라 decimalInput(), operatorInput(),
quitInput()을 수행하도록 한다.
- STATE 인터페이스를 implements 한 각 상태 구현 코드에서 주어진 전이 값에 따라 적절한 수행을 한다.

#### noOperandOne.class [코드](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap10_StatePattern/src/noOperandOne.java#L9)

```java
public class noOperandOne implements STATE {
    Calculator calculator;

    public noOperandOne(Calculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public void decimalInput(String value) {
        calculator.operand1 = Integer.parseInt(value);
        System.out.println("첫번째 인자 : " + calculator.operand1);
        calculator.setState(calculator.noOperator);
    }

    @Override
    public void operatorInput(String oper) {
        System.out.println("숫자를 입력하세요.");
    }
}
```
- 첫번째 인자값을 받아야 하는 상태

#### noOperator.class [코드](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap10_StatePattern/src/noOperator.java#L14)

```java
public class noOperator implements STATE{
    Calculator calculator;

    public noOperator (Calculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public void decimalInput(String value) {
        System.out.println("연산 기호를 입력하세요.");
    }

    @Override
    public void operatorInput(String oper) {
        char tmp = oper.charAt(0);
        if (tmp == '+' || tmp == '-' || tmp == '*' || tmp == '/') {
            calculator.operator = oper.charAt(0);
            System.out.println("연산자 : " + calculator.operator);
            calculator.setState(calculator.noOperandTwo);
        } else {
            System.out.println("잘못된 연산 기호입니다. 다시 입력하세요.");
        }
    }
}
```
- 연산기호를 받아야 하는 상태

#### noOperandTwo.class [코드](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap10_StatePattern/src/noOperandTwo.java#L9)

```java
public class noOperandTwo implements STATE {
    Calculator calculator;

    public noOperandTwo(Calculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public void decimalInput(String value) {
        calculator.operand2 = Integer.parseInt(value);
        System.out.println("두번째 인자 : " + calculator.operand2);
        calculator.setState(calculator.calculate);
    }

    @Override
    public void operatorInput(String oper) {
        System.out.println("숫자를 입력하세요.");
    }
}
```
- 두번째 인자값을 받아야 하는 상태

#### calculate.class [코드](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap10_StatePattern/src/calculate.java#L14)

```java
public class calculate implements STATE{
    Calculator calculator;

    public calculate (Calculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public void decimalInput(String value) {
        System.out.println("잘못된 입력입니다. '='를 입력하세요.");
    }

    @Override
    public void operatorInput(String oper) {
        char c = oper.charAt(0);
        if (c == '=') {
            calculator.printOutResult();
            calculator.setState(calculator.noOperandOne);
        } else {
            System.out.println("잘못된 입력입니다. '='를 입력하세요.");
        }
    }
}
```
- 결과를 내기 위해 '='를 받아야 하는 상태

### 결과

- StatePattern을 사용함으로서 상태애 대해 일일히 if 조건문을 통해 현재 상태를 확인하지 않고도 전이에 대해 수행을 하도록 
할 수 있다.
- 추가적인 상태가 발생하더라도 코드의 변경을 최소화 할 수 있다.

![ezgif com-gif-maker](https://user-images.githubusercontent.com/42247724/142576503-8a7bbace-62fd-4075-84cc-ea1616a1c09f.gif)

---

## 12. Pattern

### 코드

### 다이어그램

### 설명

### 결과