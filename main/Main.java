package main;

import javax.swing.SwingUtilities;
import ui.MainFrame;
import util.MockDataLoader;

public class Main {

    public static void main(String[] args) {

        MockDataLoader.load();

        SwingUtilities.invokeLater(() -> {

            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
