# 디자인 패턴 학습 정리

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

## 2. 전략 패턴 _ Strategy

- 같은 종류의 작업을 하는 알고리즘을 정의하고, 각 알고리즘을 캡슐화 하여 알고리즘들을 서로 바꿔 사용할 수 있도록 한다.
- strategy 패턴은 알고리즘을 사용하는 클라이언트로 부터 독립적으로 알고리즘을 바꿔서 적용할 수 있게 한다.

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

## 3. 옵저버 패턴 _ Observer

## 4. 데코레이터 패턴 _ Decorator
