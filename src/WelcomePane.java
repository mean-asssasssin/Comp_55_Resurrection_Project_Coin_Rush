import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

public class WelcomePane extends GraphicsPane {

    private MainApplication mainScreen;

    private GCompound startButton;
    private GCompound exitButton;

    public WelcomePane(MainApplication mainScreen) {
        this.mainScreen = mainScreen;
    }

    @Override
    public void showContent() {
        addPicture();
        addStartButton();
        addExitButton();
    }

    @Override
    public void hideContent() {
        for (GObject item : contents) {
            mainScreen.remove(item);
        }
        contents.clear();
    }

    private void addPicture() {
        GImage startImage = new GImage("Media/welcomepane.jpg", 200, 100);
        startImage.setSize(mainScreen.getWidth(), mainScreen.getHeight());
        startImage.setLocation((mainScreen.getWidth() - startImage.getWidth()) / 2, 0);

        contents.add(startImage);
        mainScreen.add(startImage);
    }

    private void addStartButton() {
        GImage buttonImage = new GImage("CGB02-yellow_L_btn.png");
        buttonImage.scale(0.3, 0.3);

        GLabel label = new GLabel("START GAME");
        label.setFont("SansSerif-bold-18");
        label.setColor(Color.WHITE);

        startButton = new GCompound();
        startButton.add(buttonImage, 0, 0);

        double labelX = (buttonImage.getWidth() - label.getWidth()) / 2;
        double labelY = (buttonImage.getHeight() + label.getAscent()) / 2;
        startButton.add(label, labelX, labelY);

        double x = (mainScreen.getWidth() - startButton.getWidth()) / 2;
        double y = 400;
        startButton.setLocation(x, y);

        contents.add(startButton);
        mainScreen.add(startButton);
    }

    private void addExitButton() {
        GImage buttonImage = new GImage("CGB02-yellow_L_btn.png");
        buttonImage.scale(0.3, 0.3);

        GLabel label = new GLabel("EXIT");
        label.setFont("SansSerif-bold-18");
        label.setColor(Color.WHITE);

        exitButton = new GCompound();
        exitButton.add(buttonImage, 0, 0);

        double labelX = (buttonImage.getWidth() - label.getWidth()) / 2;
        double labelY = (buttonImage.getHeight() + label.getAscent()) / 2;
        exitButton.add(label, labelX, labelY);

        double x = (mainScreen.getWidth() - exitButton.getWidth()) / 2;
        double y = 500;
        exitButton.setLocation(x, y);

        contents.add(exitButton);
        mainScreen.add(exitButton);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        double x = e.getX();
        double y = e.getY();

        // START GAME
        if (startButton.getElementAt(x - startButton.getX(), y - startButton.getY()) != null) {
            mainScreen.switchToLevel(0);
        }

        // EXIT GAME
        if (exitButton.getElementAt(x - exitButton.getX(), y - exitButton.getY()) != null) {
            System.exit(0);
        }
    }
}