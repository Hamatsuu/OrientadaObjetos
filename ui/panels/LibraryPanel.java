package ui.panels;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.store.ArticuloComprable;
import repository.DataStore;

public class LibraryPanel extends JPanel {

    private DefaultListModel<ArticuloComprable> model;

    public LibraryPanel() {

        setLayout(new BorderLayout());

        model = new DefaultListModel<>();

        JList<ArticuloComprable> list = new JList<>(model);

        refresh();

        add(new JScrollPane(list), BorderLayout.CENTER);
    }

    public void refresh() {

        model.clear();

        for(ArticuloComprable item :
                DataStore.currentPlayer
                .getLibreria()
                .getItems()) {

            model.addElement(item);
        }
    }
}
