import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class FileTestReader {
	public static void main(String[]args) {
		try (BufferedReader br = new BufferedReader(new FileReader("TestFile/level1.txt"))){
			String line;
			while((line = br.readLine())!= null) {
				System.out.println(line);
			}
		}catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
	}
}
