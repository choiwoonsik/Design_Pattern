import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class LoadHudDisplays implements LoadDisplayInterface{

    private static BufferedReader br;

    public LoadHudDisplays(String file) {
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
    }
    @Override
    public ArrayList<String> load() {
        ArrayList<String> list = new ArrayList<>();
        try {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return list;
    }
}
