import java.awt.Color;
import java.util.ArrayList;

import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GLabel;

public class FileLevelPane extends BaseLevelPane {

    private LevelData data;
	private int levelIndex;

	 public FileLevelPane(MainApplication app, LevelData data, int levelIndex) {
	        super(app);
	        this.data = data;          // <-- REQUIRED
	        this.levelIndex = levelIndex;
	    }

    @Override
    protected String getBackgroundPath() {
        return data.backgroundPath;
    }

    @Override
    protected int getStartTime() {
        return data.startTime;
    }

    @Override
    protected int getLevelIndex() {
        return data.levelIndex;
    }

    @Override
    protected int getPlayerStartX() {
        return data.playerStartX;
    }

    @Override
    protected int getPlayerStartY() {
        return data.playerStartY;
    }

    @Override
    protected void setupPlatforms() {
        platform = new Platform();
        platform.setProgram(program);

        for (PlatformData p : data.platforms) {
            platform.addPlatform(
                p.x, p.y, p.w, p.h,
                Platform.PlatformTypes.valueOf(p.type),
                p.dx, p.dy,
                p.moving
            );
        }

        platform.addPlatformsToScreen();
    }

    @Override
    protected void setupCoins() {
        coin = new testCoin(data.coinCount);
        coin.setProgram(program);

        // Your engine spawns coins automatically on platforms
        coin.spawnCoinsToPlatforms(
            coin.getCoinsOnPlatforms(),
            platform.getPlatforms(),
            true
        );

        coin.init();
    }

    @Override
    protected void setupEnemies() {
        for (EnemyData e : data.enemies) {
            Enemy enemy = new Enemy();
            enemy.setProgram(program);

            // Your engine spawns enemies on platforms by index
            enemy.spawnEnemy(platform.getPlatforms().get(e.platformIndex));

            enemies.add(enemy);
        }
    }

    @Override
    protected void setupDoor() {
        door = new Door(
            data.door.requiredCoins,
            data.door.x,
            data.door.y
        );

        door.setProgram(program);
        door.init();
    }

    @Override
    protected void setupUI() {
        UI = new UI_Elements();
        UI.setProgram(program);
        UI.createUI(coin, player);
       }
    
    @Override
    protected void onPlayerDeath() {

        deathMenuCompound = new GCompound();

        // Background image for the death menu
        GImage deathMenu = new GImage("Media/death menu.png");
        deathMenu.scale(0.5);
        deathMenuCompound.add(deathMenu, 0, 0);

        // "You Died" text
        GLabel deathLabel = new GLabel("You Died");
        deathLabel.setFont("SansSerif-bold-30");
        deathLabel.setColor(Color.BLACK);
        deathMenuCompound.add(deathLabel, 90, 80);

        // Restart Button
        GImage restartButton = new GImage("CGB02-yellow_L_btn.png");
        restartButton.scale(0.3, 0.3);

        GLabel restartLabel = new GLabel("RESTART");
        restartLabel.setFont("SansSerif-bold-18");
        restartLabel.setColor(Color.BLACK);

        restartButtonCompound = new GCompound();
        restartButtonCompound.add(restartButton, 0, 0);

        double rLabelX = (restartButton.getWidth() - restartLabel.getWidth()) / 2;
        double rLabelY = (restartButton.getHeight() + restartLabel.getAscent()) / 2;
        restartButtonCompound.add(restartLabel, rLabelX, rLabelY);

        restartButtonCompound.setLocation(100, 140);
        deathMenuCompound.add(restartButtonCompound);

        // Exit Button
        GImage exitButton = new GImage("CGB02-yellow_L_btn.png");
        exitButton.scale(0.3, 0.3);

        GLabel exitLabel = new GLabel("EXIT");
        exitLabel.setFont("SansSerif-bold-18");
        exitLabel.setColor(Color.BLACK);

        exitButtonCompound = new GCompound();
        exitButtonCompound.add(exitButton, 0, 0);

        double eLabelX = (exitButton.getWidth() - exitLabel.getWidth()) / 2;
        double eLabelY = (exitButton.getHeight() + exitLabel.getAscent()) / 2;
        exitButtonCompound.add(exitLabel, eLabelX, eLabelY);

        exitButtonCompound.setLocation(100, 220);
        deathMenuCompound.add(exitButtonCompound);

        // Center the entire death menu
        double centerX = (MainApplication.WINDOW_WIDTH - deathMenuCompound.getWidth()) / 2;
        double centerY = (MainApplication.WINDOW_HEIGHT - deathMenuCompound.getHeight()) / 2;
        deathMenuCompound.setLocation(centerX, centerY);

        program.add(deathMenuCompound);
    }
}