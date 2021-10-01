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

## 3. 옵저버 패턴 _ Observer

## 4. 데코레이터 패턴 _ Decorator
