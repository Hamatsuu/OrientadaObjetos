package ui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

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
        
        tabs.add("Store", new StorePanel());
        tabs.add("Library", libraryPanel);
        
        tabs.addChangeListener(e -> {
            int index = tabs.getSelectedIndex();
            if (index == 0) { // StorePanel
                ((StorePanel) tabs.getComponentAt(0)).refreshBalance();
            } else if (index == 1) { // LibraryPanel
                libraryPanel.refresh();
            }
        });
        setLayout(new BorderLayout());
        add(tabs, BorderLayout.CENTER);
    }
}

