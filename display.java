package game_of_life;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class display extends JFrame {
    display(settings settings) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setTitle("Game of Life");
        setLayout(null);
        setBackground(settings.backgroundColor);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                settings.running = false;
                dispose();
                System.exit(0);
            }
        });
        keyBinds panel = new keyBinds(settings);
        add(panel);
        setVisible(true);
    }

    static int round_thing(int axis, int zoom){
        return Math.round((Math.round((axis/2f)/10f) * 10)/(10f * zoom)) * 10 * zoom;
    }

    public void draw_life(int[][] alive, settings settings) {
        Insets insets = getInsets();
        Graphics g = getGraphics();
        g.clearRect(0, 0, getWidth(), getHeight());
        int width = getWidth() - (insets.left + insets.right);
        int height = getHeight() - (insets.top + insets.bottom);
        g.translate(insets.left, insets.top);
        if (settings.grid && settings.zoom >= 1) {
            g.setColor(settings.gridColor);
            int x = 0;
            while (x < (settings.zoom/10f * width)) {
                x ++;
                g.fillRect((x - 1) * settings.zoom * 10 - settings.zoom, 0, settings.zoom, height);
            }
            x = 0;
            while (x < (settings.zoom/10f * height)) {
                x ++;
                g.fillRect(0, (x - 1) * settings.zoom * 10 - settings.zoom, width, settings.zoom);
            }
        }
        g.setColor(settings.aliveColor);
        // To do: make this only render within screen area.
        for (int[] position : alive) {
            g.fillRect((10 * position[0] + settings.screenCenter[0]) * settings.zoom + round_thing(width, settings.zoom),
                    (-10 * position[1] + settings.screenCenter[1]) * settings.zoom + round_thing(height, settings.zoom),
                    settings.zoom * 9, settings.zoom * 9);
        }
        g.dispose();
    }
}
