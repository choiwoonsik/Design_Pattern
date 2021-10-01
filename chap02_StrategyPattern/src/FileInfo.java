import java.util.Date;

public class FileInfo {
    String name;
    String type;
    int size;
    Date modifiedDate;

    public FileInfo(String name, String type, int size, Date modifiedDate) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.modifiedDate = modifiedDate;
    }
}
