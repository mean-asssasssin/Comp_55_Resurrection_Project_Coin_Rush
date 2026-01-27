import java.awt.Color;
import java.awt.event.*;
import javax.swing.Timer;
import acm.graphics.*;

public abstract class BaseLevelPane extends GraphicsPane {

    protected MainApplication program;

    protected Player player;
    protected Platform platform;
    protected testCoin coin;
    protected Door door;
    protected UI_Elements UI;
    protected java.util.ArrayList<Enemy> enemies = new java.util.ArrayList<>();

    protected GImage background;
    protected hitBox playerHitbox;

    protected boolean isPaused = false;
    protected Timer gameTimer;
    protected Timer countdownTimer;
    protected int timeLeft;

    protected boolean isDead = false;
    protected GCompound deathMenuCompound;
    protected GCompound restartButtonCompound;
    protected GCompound exitButtonCompound;
    protected GCompound pauseButtonCompound;

    public BaseLevelPane(MainApplication app) {
        this.program = app;
    }

    // ---------- ABSTRACT METHODS ----------
    protected abstract String getBackgroundPath();
    protected abstract int getStartTime();
    protected abstract int getLevelIndex();
    protected abstract int getPlayerStartX();
    protected abstract int getPlayerStartY();

    protected abstract void setupPlatforms();
    protected abstract void setupCoins();
    protected abstract void setupEnemies();
    protected abstract void setupDoor();
    protected abstract void setupUI();
    protected abstract void onPlayerDeath();

    // ---------- SHOW CONTENT ----------
    @Override
    public void showContent() {

        // Background
        background = new GImage(getBackgroundPath());
        program.add(background);
        background.setSize(MainApplication.WINDOW_WIDTH, MainApplication.WINDOW_HEIGHT);
        background.sendToBack();

        // Platforms
        setupPlatforms();

        // Coins
        setupCoins();

        // Door
        setupDoor();

        // Player
        player = new Player(getPlayerStartX(), getPlayerStartY());
        player.setProgram(program);
        player.spawn(getPlayerStartX(), getPlayerStartY());

        // Hitbox
        playerHitbox = new hitBox();
        playerHitbox.createHitbox(
            player.getX(), player.getY(),
            player.getBounds().getWidth(),
            player.getBounds().getHeight(),
            20, 3
        );
        program.add(playerHitbox.getHitbox());

        // Enemies
        setupEnemies();

        // UI
        setupUI();

        // Timers
        setupCountdownTimer(getStartTime());
        setupGameLoop();

        // Pause button
        addPauseButton();

        // Listeners
        program.addKeyListeners();
        program.addMouseListeners();
        program.requestFocus();
    }

    // ---------- GAME LOOP ----------
    private void setupGameLoop() {
        gameTimer = new Timer(16, new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (isPaused) return;

                player.update();
                playerHitbox.updateHitbox(player.getX(), player.getY(), 20, 3);

                if (!isDead && player.getHP() <= 0) {
                    killPlayer();
                    return;
                }

                platform.handlePlatformInteraction(player, playerHitbox);
                coin.update(playerHitbox);

                for (Enemy enemy : enemies) {
                    enemy.update(playerHitbox, player);
                }

                door.checkIfplayerCanExit(coin.getCoinsCollected());
                UI.init(door, coin, player);

                if (door.isOpen() && door.playerTouchingDoor(playerHitbox)) {
                    finishLevel();
                }
            }
        });

        gameTimer.start();
    }

    // ---------- COUNTDOWN TIMER ----------
    private void setupCountdownTimer(int startTime) {
        timeLeft = startTime;
        UI.createTimer(timeLeft);

        countdownTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                UI.updateTimer(timeLeft);

                if (timeLeft <= 0) {
                    countdownTimer.stop();
                    gameTimer.stop();
                    showTimesUp();
                }
            }
        });

        countdownTimer.start();
    }

    // ---------- PAUSE BUTTON ----------
    private void addPauseButton() {
        GImage pauseButton = new GImage("CGB02-yellow_M_btn.png");
        pauseButton.scale(0.3, 0.3);

        GLabel label = new GLabel("PAUSE");
        label.setFont("SansSerif-bold-18");
        label.setColor(Color.WHITE);

        pauseButtonCompound = new GCompound();
        pauseButtonCompound.add(pauseButton, 0, 0);

        double lx = (pauseButton.getWidth() - label.getWidth()) / 2;
        double ly = (pauseButton.getHeight() + label.getAscent()) / 2;
        pauseButtonCompound.add(label, lx, ly);

        pauseButtonCompound.setLocation(1120, 0);
        program.add(pauseButtonCompound);
    }

    // ---------- DEATH ----------
    private void killPlayer() {
        gameTimer.stop();
        countdownTimer.stop();
        isDead = true;
        onPlayerDeath();
    }

    // ---------- LEVEL COMPLETE ----------
    private void finishLevel() {
        gameTimer.stop();
        countdownTimer.stop();

        program.switchToScoreboard(timeLeft, coin.getCoinsCollected(), getLevelIndex());
    }

    // ---------- TIMES UP ----------
    private void showTimesUp() {
        GLabel gameOver = new GLabel("Time's Up!", 500, 300);
        gameOver.setFont("SansSerif-bold-32");
        gameOver.setColor(Color.RED);
        program.add(gameOver);
    }

    // ---------- KEYBOARD INPUT ----------
    @Override
    public void keyPressed(KeyEvent e) {
        if (!isDead && player != null) player.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!isDead && player != null) player.keyReleased(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (!isDead && player != null) player.keyTyped(e);
    }

    // ---------- HIDE CONTENT ----------
    @Override
    public void hideContent() {
        if (gameTimer != null) gameTimer.stop();
        if (countdownTimer != null) countdownTimer.stop();
        program.clear();
    }
}