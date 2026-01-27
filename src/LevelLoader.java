import java.io.*;
import java.util.*;

public class LevelLoader {

    public static LevelData load(String filePath) {
        LevelData data = new LevelData();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split("\\s+");
                String cmd = parts[0].toLowerCase();

                switch (cmd) {

                    case "background":
                        data.backgroundPath = parts[1];
                        break;

                    case "time":
                        data.startTime = Integer.parseInt(parts[1]);
                        break;

                    case "level":
                        data.levelIndex = Integer.parseInt(parts[1]);
                        break;

                    case "player":
                        data.playerStartX = Integer.parseInt(parts[1]);
                        data.playerStartY = Integer.parseInt(parts[2]);
                        break;

                    case "platform":
                        PlatformData p = new PlatformData();
                        p.x = Integer.parseInt(parts[1]);
                        p.y = Integer.parseInt(parts[2]);
                        p.w = Integer.parseInt(parts[3]);
                        p.h = Integer.parseInt(parts[4]);
                        p.type = parts[5];
                        p.dx = 0;
                        p.dy = 0;
                        p.moving = false;
                        data.platforms.add(p);
                        break;

                    case "coin":
                        data.coinCount = Integer.parseInt(parts[1]);
                        break;

                    case "enemy":
                        EnemyData e = new EnemyData();
                        e.platformIndex = Integer.parseInt(parts[1]);
                        data.enemies.add(e);
                        break;

                    case "door":
                        DoorData d = new DoorData();
                        d.requiredCoins = Integer.parseInt(parts[1]);
                        d.x = Integer.parseInt(parts[2]);
                        d.y = Integer.parseInt(parts[3]);
                        data.door = d;
                        break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}