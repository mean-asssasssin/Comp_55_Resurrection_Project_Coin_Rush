import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileTest {
    public static void main(String[] args) {
    	String filePath = "TestFile/level1.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("First Line");
            writer.newLine();
            writer.write("Second Line");
            writer.newLine();
            writer.write("Third Line\n");
            writer.write("Forth line \n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}