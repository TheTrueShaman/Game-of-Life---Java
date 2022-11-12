package game_of_life;

import java.awt.Color;

public class settings {
    int sleepTime = 100;
    int zoom = 1;
    int moveRate = 10;
    int[] screenCenter = {0, 0};
    // 0 = main menu, 1 = running, 2 = paused
    int gameState = 1;
    boolean running = true;
    boolean grid = true;
    boolean dynamicMovement = false;
    Color backgroundColor = new Color(230, 230, 230);
    Color gridColor = new Color(255, 255, 255);
    Color aliveColor = new Color(0, 0, 0);
}
