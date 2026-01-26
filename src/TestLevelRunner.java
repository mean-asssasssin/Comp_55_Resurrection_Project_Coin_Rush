import java.util.*;

public class TestLevelRunner {

    public static void main(String[] args) {

        List<String[]> commands = LevelLoader.loadLevel("levels/TestLevel.txt");

        System.out.println("Loaded level commands:");
        System.out.println("-----------------------");

        for (String[] cmd : commands) {
            System.out.print(cmd[0] + " -> ");

            for (int i = 1; i < cmd.length; i++) {
                System.out.print(cmd[i] + " ");
            }

            System.out.println();
        }
    }
}