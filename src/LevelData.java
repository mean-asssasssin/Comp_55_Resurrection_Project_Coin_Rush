import java.util.ArrayList;

public class LevelData {

    public String backgroundPath;
    public int startTime;
    public int levelIndex;

    public int playerStartX;
    public int playerStartY;

    public ArrayList<PlatformData> platforms = new ArrayList<>();
    public int coinCount;

    public ArrayList<EnemyData> enemies = new ArrayList<>();

    public DoorData door;
}