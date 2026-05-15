package main;

import javax.swing.SwingUtilities;

import model.users.Jugador;
import repository.DataStore;
import ui.MainFrame;
import util.MockDataLoader;

public class Main {

    public static void main(String[] args) {

        MockDataLoader.load();

        DataStore.currentPlayer = new Jugador(
                "ryou",
                "ryou@mail.com",
                200
        );

        SwingUtilities.invokeLater(() -> {

            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
