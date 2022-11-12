package game_of_life;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class keyBinds extends JPanel {
    public keyBinds(settings settings) {
        String[][] defaults = {{"RIGHT", "rightAction"}, {"LEFT", "leftAction"}, {"UP", "upAction"},
                {"DOWN", "downAction"}};
        create_keyBinds(defaults, settings);
    }

    public void create_keyBinds(String[][] binds, settings settings) {
        InputMap inputMap = getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();
        for (String[] bind : binds) {
            inputMap.put(KeyStroke.getKeyStroke(bind[0]), bind[1]);
        }
        // Create actions
        Action rightAction;
        Action leftAction;
        Action upAction;
        Action downAction;
        // Switch this so that it uses the shifting system the python version uses.
        {
            rightAction = new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    settings.screenCenter[0] -= settings.moveRate;
                }
            };
            leftAction = new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    settings.screenCenter[0] += settings.moveRate;
                }
            };
            upAction = new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    settings.screenCenter[1] += settings.moveRate;
                }
            };
            downAction = new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    settings.screenCenter[1] -= settings.moveRate;
                }
            };
        }
        actionMap.put("rightAction", rightAction);
        actionMap.put("leftAction", leftAction);
        actionMap.put("upAction", upAction);
        actionMap.put("downAction", downAction);
    }
}
