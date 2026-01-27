import java.awt.Color;
import java.awt.event.MouseEvent;
import acm.graphics.*;

public class ScoreboardPane extends GraphicsPane {

    private MainApplication program;
    private int timeLeft;
    private int coinsCollected;
    private int finalScore;
    private int currentLevelIndex;
    
    private GCompound nextLevelButtonCompound;
    private GCompound restartButtonCompound;
    private GCompound menuButtonCompound;
    private GRect overlay;

    public ScoreboardPane(MainApplication app, int levelIndex, int timeLeft, int coinsCollected) {
        this.program = app;
        this.currentLevelIndex = levelIndex;
        this.timeLeft = timeLeft;
        this.coinsCollected = coinsCollected;

        finalScore = (timeLeft * 500) + (coinsCollected * 250);
    }

    @Override
    public void showContent() {

        // Dark overlay
        overlay = new GRect(0, 0, MainApplication.WINDOW_WIDTH, MainApplication.WINDOW_HEIGHT);
        overlay.setFilled(true);
        overlay.setFillColor(new Color(0, 0, 0, 150));
        overlay.setColor(new Color(0, 0, 0, 0));
        program.add(overlay);
        contents.add(overlay);

        // Title
        GLabel titleLabel = new GLabel("LEVEL COMPLETE!");
        titleLabel.setFont("SansSerif-bold-48");
        titleLabel.setColor(Color.WHITE);
        titleLabel.setLocation((MainApplication.WINDOW_WIDTH - titleLabel.getWidth()) / 2, 100);
        program.add(titleLabel);
        contents.add(titleLabel);

        // Time bonus
        GLabel timeBonusLabel = new GLabel("Time Bonus: " + timeLeft + " × 500 = " + (timeLeft * 500));
        timeBonusLabel.setFont("SansSerif-bold-24");
        timeBonusLabel.setColor(Color.YELLOW);
        timeBonusLabel.setLocation((MainApplication.WINDOW_WIDTH - timeBonusLabel.getWidth()) / 2, 200);
        program.add(timeBonusLabel);
        contents.add(timeBonusLabel);

        // Coin bonus
        GLabel coinBonusLabel = new GLabel("Coins Bonus: " + coinsCollected + " × 250 = " + (coinsCollected * 250));
        coinBonusLabel.setFont("SansSerif-bold-24");
        coinBonusLabel.setColor(Color.YELLOW);
        coinBonusLabel.setLocation((MainApplication.WINDOW_WIDTH - coinBonusLabel.getWidth()) / 2, 250);
        program.add(coinBonusLabel);
        contents.add(coinBonusLabel);

        // Final score
        GLabel finalScoreLabel = new GLabel("Final Score: " + finalScore);
        finalScoreLabel.setFont("SansSerif-bold-32");
        finalScoreLabel.setColor(Color.CYAN);
        finalScoreLabel.setLocation((MainApplication.WINDOW_WIDTH - finalScoreLabel.getWidth()) / 2, 320);
        program.add(finalScoreLabel);
        contents.add(finalScoreLabel);

        createNextLevelButton();
        createRestartButton();
        createMenuButton();

    }
    private void createNextLevelButton() {
        GImage buttonImage = new GImage("CGB02-yellow_L_btn.png");
        buttonImage.scale(0.4, 0.4);

        GLabel label = new GLabel("NEXT LEVEL");
        label.setFont("SansSerif-bold-18");
        label.setColor(Color.WHITE);

        nextLevelButtonCompound = new GCompound();
        nextLevelButtonCompound.add(buttonImage, 0, 0);

        double labelX = (buttonImage.getWidth() - label.getWidth()) / 2;
        double labelY = (buttonImage.getHeight() + label.getAscent()) / 2;
        nextLevelButtonCompound.add(label, labelX, labelY);

        nextLevelButtonCompound.setLocation(
            (MainApplication.WINDOW_WIDTH - buttonImage.getWidth()) / 2,
            360
        );

        program.add(nextLevelButtonCompound);
        contents.add(nextLevelButtonCompound);
    }


    private void createRestartButton() {
        GImage buttonImage = new GImage("CGB02-yellow_L_btn.png");
        buttonImage.scale(0.4, 0.4);

        GLabel label = new GLabel("RESTART LEVEL");
        label.setFont("SansSerif-bold-18");
        label.setColor(Color.WHITE);

        restartButtonCompound = new GCompound();
        restartButtonCompound.add(buttonImage, 0, 0);

        double labelX = (buttonImage.getWidth() - label.getWidth()) / 2;
        double labelY = (buttonImage.getHeight() + label.getAscent()) / 2;
        restartButtonCompound.add(label, labelX, labelY);

        restartButtonCompound.setLocation((MainApplication.WINDOW_WIDTH - buttonImage.getWidth()) / 2, 420);
        program.add(restartButtonCompound);
        contents.add(restartButtonCompound);
    }

    private void createMenuButton() {
        GImage buttonImage = new GImage("CGB02-yellow_L_btn.png");
        buttonImage.scale(0.4, 0.4);

        GLabel label = new GLabel("MAIN MENU");
        label.setFont("SansSerif-bold-18");
        label.setColor(Color.WHITE);

        menuButtonCompound = new GCompound();
        menuButtonCompound.add(buttonImage, 0, 0);

        double labelX = (buttonImage.getWidth() - label.getWidth()) / 2;
        double labelY = (buttonImage.getHeight() + label.getAscent()) / 2;
        menuButtonCompound.add(label, labelX, labelY);

        menuButtonCompound.setLocation((MainApplication.WINDOW_WIDTH - buttonImage.getWidth()) / 2, 500);
        program.add(menuButtonCompound);
        contents.add(menuButtonCompound);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        double x = e.getX();
        double y = e.getY();
     
        // Next Level
        if (nextLevelButtonCompound.getElementAt(
                x - nextLevelButtonCompound.getX(),
                y - nextLevelButtonCompound.getY()) != null) {

            program.switchToLevel(currentLevelIndex + 1);
        }

        // Restart Level 0
        if (restartButtonCompound.getElementAt(x - restartButtonCompound.getX(), y - restartButtonCompound.getY()) != null) {
            program.switchToLevel(currentLevelIndex);
        }

        // Back to menu
        if (menuButtonCompound.getElementAt(x - menuButtonCompound.getX(), y - menuButtonCompound.getY()) != null) {
            program.switchToWelcomeScreen();
        }
        
    }

    @Override
    public void hideContent() {
        for (GObject obj : contents) {
            program.remove(obj);
        }
        contents.clear();
    }
}