# 디자인 패턴 학습 정리

### 목차

1. [다형성](#1-다형성)
2. [Strategy Pattern](#2-전략-패턴-_-strategy)
3. [Observer Pattern](#3-옵저버-패턴-_-observer)
4. [Decorator Pattern](#4-데코레이터-패턴-_-decorator)
5. [Factory Pattern](#5-팩토리-메소드-패턴-_-Factory-Method)
6. []()
7. []()

## 1. 다형성
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
>main클래스에서 문서 처리기를 만들고, 해당 문서 처리기에 EngSpellChecker를 추가한다.
EngSpellChecker는 IspellChecker 인터페이스를 구현한 클래스로 영어 스펠링 체크를 담당하여 구현한 클래스이다.
addDocConverter()를 통해 문서 별 DocConverter를 생성하여 추가해 준다.
이때 각 문서 Converter들은 추상클래스 DocConverter를 상속받아서 구현한다.

#### 결과
```
Checking English Spelling...
new doc.odt로 변환해서 저장합니다.
new doc.pdf로 변환해서 저장합니다.
new doc.docx로 변환해서 저장합니다.
wps파일 형식을 지원하는 DocConverter가 없습니다.
```
---
## 2. 전략 패턴 _ Strategy

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
    String[] names = { "CompareModifiedDate", "CompareFileType", "CompareFileName", "Main", "CompareSize"  };
    String[] types = { "java", "java", "class", "java", "Class" };
    String[] dateStrings = { "2020-09-13T21:59:00", "2020-09-12T21:59:00", "2020-09-13T16:54:00", "2020-09-12T21:54:00", "2020-09-13T11:59:00" };
    int[] sizes = { 120, 80, 150, 85, 100 };

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

현재 Main 클래스에서 파일 목록을 생성하고 있다. 이 파일 목록을 정렬하려고 하는데 동일한 정렬 
알고리즘으로 여러 기준에 대해 정렬을 하려고 한다.

기준으로는 파일이름, 파일 종류, 파일 크기, 파일 수정 시간 4가지가 존재한다. 이 기준별로 정렬 알고리즘을
각각 짜는것은 매우 비효율적이므로 하나의 정렬 알고리즘을 구현하고, 전달받은 기준으로 정렬하도록 한다.

- Sorter 클래스는 Object 타입에 대해 bubbleSort()를 하는 메소드를 갖고 있으며 setComparable()을 통해 원하는 Comparable을 받을 수 있게 되어있다. [코드](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap02_StrategyPattern/src/Sorter.java)
- [CompareFileName](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap02_StrategyPattern/src/CompareFileName.java), [CompareFileType](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap02_StrategyPattern/src/CompareFileType.java), [CompareSize](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap02_StrategyPattern/src/CompareSize.java), [CompareModifiedDate](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap02_StrategyPattern/src/CompareModifiedDate.java) 클래스들은
전부 [Comparable 인터페이스](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap02_StrategyPattern/src/Comparable.java)를 상속받아서 구현한 클래스이다.
즉, 각자의 조건에 맞게 Comparable을 구현하여 알고리즘을 캡슐화 할 수 있게 한다.

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
## 3. 옵저버 패턴 _ Observer

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

public class MainWindow extends FrameWindow implements ActionListener, Subject{
    // 생략
    private static final ArrayList<Observer> observers = new ArrayList<>();
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
        }
        else if (e.getSource() == updateLabelObserverButton) {
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

- MainWindow 클래스에서 창을 생성한다. 이때 구독/취소를 위한 버튼창 & TextFieldWindow & LableWindow 세개의 창이 생긴다. 또한 Subject 인터페이스를 상속받아서 옵저버을 등록하고 취소하고 통보하는 [구현](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap03_ObserverPattern/src/MainWindow.java#L108)을 한다.
- [Lable](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap03_ObserverPattern/src/LabelWindow.java)과 [TextField](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap03_ObserverPattern/src/TextFieldWindow.java)클래스는 [Observer 인터페이스](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap03_ObserverPattern/src/Observer.java)를 상속받아서 옵저버가 붙게 된다.
- 소수 생성기인 [PrimeObserableThread클래스](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap03_ObserverPattern/src/PrimeObservableThread.java#L27)에서 소수를 생성하고 옵저버에게 통보를 한다.

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

## 4. 데코레이터 패턴 _ Decorator

- 객체에 추가적인 요건을 동적으로 추가해주는 패턴으로, 서브 클래스를 만들지 않고
기능을 유연하게 확장할 수 있게 한다.
- 데코레이터 패턴에서의 상속은 공통적인 타입으로 추상화하기 위한 용도가 주 목적이다.
- 데코레이터는 인터페이스/추상 클래스로서 역활을 하고 이를 상속받은 클래스들은 
컴포넌트를 꾸미기 위한 구현을 갖게 된다.
- 컴포넌트는 바로 사용되거나 데코레이터레가 붙어서 사용될 수 있다.

#### 예시
- 데코레이터 패턴을 일상생활의 예시로 커피를 들 수 있다. 
- 커피를 생각해보면 에스프레소, 콜드블루는 컴포넌트가 될 수 있고
얼음, 시나몬 가루, 우유, 두유, 샷, 자바칩 등은 모두 데코레이터 클래스를 상속받은
데코를 위한 클래스가 될 수 있다.
- 이때, 최상위 추상 클래스/인터페이스를 Beverage로 두고 에스프레소, 콜드블루 컴포넌트는 Beverage를 상속받는다.
- 데코들의 추상화를 위해 Beverage를 상속받은 BeverageDecorator를 선언하고, 
모든 데코 용 클래스들은 BeverageDecorator를 상속받는다.
- 형태 → 자바칩 ( 우유 ( 얼음 ( 얼음 ( 샷 ( 에스프레소 ) ) ) ) ), 이때 순서는 상관이 없다.

### 다이어그램

<img width="746" alt="스크린샷 2021-10-03 오후 5 42 36" src="https://user-images.githubusercontent.com/42247724/135746322-2f966aae-9257-42aa-a3f5-6365ca2c6ac4.png">

- Display 추상 클래스를 최상위 클래스로 두고 기본 컴포넌트로 HudDisplay를 갖는다.
- HudDisplay를 꾸밀 수 있는 Deco용 클래스들의 추상화 용도로 Display를 상속한 추상 클래스 DisplayDecorator를 둔다.
- Message, Time, Weater, Speed 클래스들은 DisplayDecorator를 상속받아서 구현한다.
- 결국 데코 클래스도 Display 클래스의 하위 클래스로서 데코클래스에서 반환한 클래스에 대해
 데코를 반복할 수 있게 된다.

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
```

### 설명

main() → MainWindow() → createWindow() → createPanel()

- createPanel 에서 기본 컴포넌트로서 HudDisplay를 만든다. _ [코드](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap04_DecoratorPattern/src/MainWindow.java#L58)
- 이후 display.txt파일에 작성된 순서대로 디스플레이에 기능 목록을 추가한다.
- 데코 용 클래스 :  [MessageDisplay](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap04_DecoratorPattern/src/MessageDisplay.java), [SpeedmeterDisplay](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap04_DecoratorPattern/src/SpeedmeterDisplay.java), [TimeDisplay](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap04_DecoratorPattern/src/TimeDisplay.java), [WeatherDisplay](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap04_DecoratorPattern/src/WeatherDisplay.java)

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

## 5. 팩토리 메소드 패턴 _ Factory Method
- 부모(상위) 클래스에 알려지지 않은 구체 클래스를 생성하는 패턴이며, 자식(하위) 클래스가 어떤 객체를 생성할지를 결정하도록 하는 패턴.
- 객체 생성용 인터페이스를 정의하고 해당 인스턴스를 상속한 서브클래스가 어떤 클래스를 인스턴스화(객체 생성) 할 지 결정할 수 있도록 한다.
- 팩토리 메소드는 객체 생성을 서브 클래스에서 할 수 있도록 미룰 수 있게 한다.

>
> #### 왜 굳이 자식 클래스에서 객체를 생성하도록 미루는 것이고 얻을 수 있는 장점이 뭘까?
실제 객체를 생성하기 위해서 new를 사용한다. new를 호출해서 객체를 생성하게 되는데 생성할 객체가 다양하고 추가/삭제 등 변경이 잦다면 어떻게 될까? 

클라이언트 단의 잦은 코드 수정이 불가피하게 되고 이는 객체지향 설계 원칙인 OCP (변경에는 닫히고 확장에는 열려야 함)를 위반하게 될 것이다.
따라서, 생성을 구현과 분리해서 캡슐화 시키므로서 수정이 일어나는 곳을 최소화 하고 변경이 확산되는 것을 방지할 수 있다.

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
다양한 도형 별 크기를 계산하기 위해 도형별 객체가 존재하고 도형별로 별도의 계산법을 사용해야하는 상황이다.
클라이언트에서 각 타입별로 객체를 선언해서 계산하지 않고 factory 패턴에 이를 맡긴다. 메인에서는 계산을 원하는 도형을 삼각형, 사각형에 따라
그에 맞는 factory에 보내서 만들도록 한다.

만약 다른 종류의 사각형이나 삼각형이 추가되더라도 클라이언트 코드는 변경없이 바로 추가만 해주면 된다. 그에 맞는 생성 로직은 Factory 패턴에서 담당한다.

각 도형들은 모두 [Shape 추상 클래스](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap05_FactoryPattern/src/Shape.java#L12) 를 상속하고, 
공통으로 갖는 기능 및 변수를 선언하고 도형별로 다른 계산 법을 가지는 calcArea()는 추상 메소드로 선언한다. 이부분은 상속한 도형에서 override하여 구현한다.

[도형 객체](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap05_FactoryPattern/src/Triangle.java) 를 선언하는 Factory들은 [ShapeFactory 인터페이스](https://github.com/choiwoonsik/Design_Pattern/blob/main/chap05_FactoryPattern/src/ShapeFactory.java) 를
상속받고 객체를 생성하는 create() 메소드를 구현한다.

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