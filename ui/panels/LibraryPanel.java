package ui.panels;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import model.games.Juego;
import model.store.ArticuloComprable;
import repository.DataStore;

public class LibraryPanel extends JPanel {

    private DefaultListModel<ArticuloComprable> model;
    private JList<ArticuloComprable> list;

    public LibraryPanel() {
        setLayout(new BorderLayout());

        model = new DefaultListModel<>();
        list = new JList<>(model);
        list.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof ArticuloComprable) {
                    label.setText(((ArticuloComprable) value).getNombre());
                }
                return label;
            }
        });

        // Agregamos el listener para lanzar el juego con doble clic
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    ArticuloComprable selected = list.getSelectedValue();
                    if (selected != null) {
                        abrirJuego(selected);
                    }
                }
            }
        });

        refresh();

        add(new JLabel("Tu Biblioteca (Haz doble clic para jugar)"), BorderLayout.NORTH);
        add(new JScrollPane(list), BorderLayout.CENTER);
    }

    public void refresh() {
        model.clear();
        for(ArticuloComprable item : DataStore.currentPlayer.getLibreria().getItems()) {
            model.addElement(item);
        }
    }
    private void abrirJuego(ArticuloComprable item) {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Lanzador", true);
        dialog.setSize(350, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        infoPanel.add(new JLabel("<html><h2 style='margin:0;'>" + item.getNombre() + "</h2></html>"));
        infoPanel.add(Box.createVerticalStrut(10));

        if (item instanceof Juego) {
            Juego juego = (Juego) item;
            infoPanel.add(new JLabel("<html><i>" + juego.getDescripcion() + "</i></html>"));
        } 
        else if (item instanceof model.bundles.Bundle) {
            model.bundles.Bundle bundle = (model.bundles.Bundle) item;
            infoPanel.add(new JLabel("<html><b>Contenido del Bundle:</b></html>"));
            for (model.games.Juego j : bundle.getJuegos()) {
                infoPanel.add(new JLabel(" - " + j.getNombre()));
            }
        }

        JButton playButton = new JButton("▶ Ejecutar Contenido");
        playButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(dialog, "Iniciando componentes de " + item.getNombre());
            dialog.dispose();
        });

        dialog.add(new JScrollPane(infoPanel), BorderLayout.CENTER);
        dialog.add(playButton, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
  
}

