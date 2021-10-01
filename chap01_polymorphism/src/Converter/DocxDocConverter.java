package Converter;

public class DocxDocConverter extends DocConverter {

    public DocxDocConverter() {
        super("docx");
    }

    @Override
    public void save(String fileName) {
        System.out.println(fileName +"."+super.getExtension()+"로 변환해서 저장합니다.");
    }
}
