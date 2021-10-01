import Converter.DocConverter;

import java.util.HashMap;

public class WordProcessor {
    private final String fileName;
    private ISpellChecker spellChecker;
    private HashMap<String, DocConverter> docConverter = new HashMap<>();

    public WordProcessor(String fileName) {
        this.fileName = fileName;
    }

    public void setSpellChecker(ISpellChecker spellChecker) {
        this.spellChecker = spellChecker;
    }

    public void addDocConverter(DocConverter docConverter) {
        this.docConverter.put(docConverter.getExtension(), docConverter);
    }

    public void checkSpelling() {
        this.spellChecker.check();
    }

    public void convertDocTo(String extension) {
        if (docConverter.containsKey(extension)) {
            docConverter.get(extension).save(fileName);
        } else {
            System.out.println(extension+"파일 형식을 지원하는 DocConverter가 없습니다.");
        }
    }
}
