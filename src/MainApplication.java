import acm.graphics.GObject;
import acm.program.GraphicsProgram;

import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;

public class MainApplication extends GraphicsProgram {

    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;

    private GraphicsPane currentPane;

    @Override
    public void init() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        addKeyListeners();
        addMouseListeners();
    }

    @Override
    public void run() {
        switchToWelcomeScreen();   // Start at the main menu
    }

  //loads the level

    public void switchToLevel(int index) {
        // Searches for level with the same syntax
        String filename = "levels/level" + index + ".txt";

        // where the level data gets stored
        LevelData data = LevelLoader.load(filename);

        // makes a pane
        FileLevelPane levelPane = new FileLevelPane(this, data, index);

        switchToPane(levelPane);
    }

    private void switchToPane(GraphicsPane newPane) {
        if (currentPane != null) {
            currentPane.hideContent();
        }
        currentPane = newPane;
        currentPane.showContent();
    }

    //scoreboard function

    public void switchToScoreboard(int timeLeft, int coinsCollected, int levelIndex) {
        if (currentPane != null) currentPane.hideContent();
        currentPane = new ScoreboardPane(this, levelIndex, timeLeft, coinsCollected);
        currentPane.showContent();
    }

   
    public void switchToWelcomeScreen() {
        if (currentPane != null) {
            currentPane.hideContent();
        }
        currentPane = new WelcomePane(this);
        currentPane.showContent();
    }

    public GObject getElementAtLocation(double x, double y) {
        return getElementAt(x, y);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (currentPane != null) currentPane.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (currentPane != null) currentPane.mouseReleased(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (currentPane != null) currentPane.mouseClicked(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (currentPane != null) currentPane.mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (currentPane != null) currentPane.mouseMoved(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (currentPane != null) currentPane.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (currentPane != null) currentPane.keyReleased(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (currentPane != null) currentPane.keyTyped(e);
    }

    public static void main(String[] args) {
        new MainApplication().start();
    }
}