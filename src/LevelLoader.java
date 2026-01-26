import java.io.*;
import java.util.*;

public class LevelLoader {

    public static List<String[]> loadLevel(String filePath) {
        List<String[]> commands = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                commands.add(line.split(" "));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return commands;
    }
}