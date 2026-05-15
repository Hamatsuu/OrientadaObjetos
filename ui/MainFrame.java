package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import model.users.Jugador;
import repository.DataStore;
import ui.panels.LibraryPanel;
import ui.panels.StorePanel;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Steam Clone");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();

        LibraryPanel libraryPanel = new LibraryPanel();
        StorePanel storePanel = new StorePanel();

        DefaultComboBoxModel<Jugador> userModel = new DefaultComboBoxModel<>();
        for (Jugador jugador : DataStore.PLAYERS) {
            userModel.addElement(jugador);
        }

        JComboBox<Jugador> userSelector = new JComboBox<>(userModel);
        if (DataStore.currentPlayer != null) {
            userSelector.setSelectedItem(DataStore.currentPlayer);
        }
        userSelector.setPreferredSize(new Dimension(220, 28));
        userSelector.addActionListener(e -> {
            Jugador selected = (Jugador) userSelector.getSelectedItem();
            if (selected != null) {
                DataStore.currentPlayer = selected;
                storePanel.refreshStore();
                libraryPanel.refresh();
            }
        });

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.add(new JLabel("Usuario:"));
        topPanel.add(userSelector);
        add(topPanel, BorderLayout.NORTH);

        tabs.add("Store", storePanel);
        tabs.add("Library", libraryPanel);
        
        tabs.addChangeListener(e -> {
            int index = tabs.getSelectedIndex();
            if (index == 0) { // StorePanel
                storePanel.refreshBalance();
            } else if (index == 1) { // LibraryPanel
                libraryPanel.refresh();
            }
        });
        add(tabs, BorderLayout.CENTER);
    }
}

