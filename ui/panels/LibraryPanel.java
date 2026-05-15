package ui.panels;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import model.games.Juego;
import model.reviews.Review;
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

        final Juego juego = item instanceof Juego ? (Juego) item : null;
        if (juego != null) {
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

        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(playButton);

        if (juego != null) {
            boolean alreadyReviewed = DataStore.currentPlayer != null && juego.getReviews().stream()
                    .anyMatch(r -> r.getAuthor().equalsIgnoreCase(DataStore.currentPlayer.getUsername()));
            JButton deleteButton = new JButton("Eliminar Reseña");
            deleteButton.setEnabled(alreadyReviewed);
            JButton reviewButton = new JButton("Agregar Reseña");
            reviewButton.setEnabled(!alreadyReviewed);
            if (alreadyReviewed) {
                reviewButton.setToolTipText("Ya has dejado una reseña para este juego.");
            }
            reviewButton.addActionListener(ev -> {
                JDialog reviewDialog = new JDialog(dialog, "Agregar Reseña", true);
                reviewDialog.setSize(400, 300);
                reviewDialog.setLocationRelativeTo(dialog);
                JPanel rPanel = new JPanel();
                rPanel.setLayout(new BoxLayout(rPanel, BoxLayout.Y_AXIS));
                rPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

                rPanel.add(new JLabel("Autor:"));
                JTextField authorField = new JTextField();
                authorField.setText(DataStore.currentPlayer != null ? DataStore.currentPlayer.getUsername() : "");
                authorField.setEditable(false);
                rPanel.add(authorField);

                rPanel.add(new JLabel("Reseña:"));
                JTextArea textArea = new JTextArea(6, 30);
                rPanel.add(new JScrollPane(textArea));

                rPanel.add(new JLabel("Puntuación (1-10):"));
                SpinnerNumberModel spinModel = new SpinnerNumberModel(8, 1, 10, 1);
                JSpinner scoreSpinner = new JSpinner(spinModel);
                rPanel.add(scoreSpinner);

                JButton submit = new JButton("Enviar");
                submit.addActionListener(ae -> {
                    String autor = authorField.getText().trim();
                    String texto = textArea.getText().trim();
                    int score = (Integer) scoreSpinner.getValue();
                    if (autor.isEmpty() || texto.isEmpty()) {
                        JOptionPane.showMessageDialog(reviewDialog, "Completa todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (DataStore.currentPlayer != null && juego.getReviews().stream()
                            .anyMatch(r -> r.getAuthor().equalsIgnoreCase(DataStore.currentPlayer.getUsername()))) {
                        JOptionPane.showMessageDialog(reviewDialog, "Ya has dejado una reseña para este juego.", "Error", JOptionPane.ERROR_MESSAGE);
                        reviewDialog.dispose();
                        return;
                    }
                    Review r = new Review(autor, texto, score);
                    juego.addReview(r);
                    JOptionPane.showMessageDialog(reviewDialog, "Reseña agregada.");
                    reviewDialog.dispose();
                    reviewButton.setEnabled(false);
                    deleteButton.setEnabled(true);
                });
                rPanel.add(Box.createVerticalStrut(8));
                rPanel.add(submit);

                reviewDialog.add(rPanel);
                reviewDialog.setVisible(true);
            });
            buttons.add(reviewButton);

            deleteButton.addActionListener(ev -> {
                if (DataStore.currentPlayer == null) return;
                int option = JOptionPane.showConfirmDialog(dialog,
                        "¿Eliminar tu reseña para este juego?",
                        "Eliminar reseña",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                if (option == JOptionPane.YES_OPTION) {
                    boolean removed = juego.removeReviewByAuthor(DataStore.currentPlayer.getUsername());
                    if (removed) {
                        JOptionPane.showMessageDialog(dialog, "Reseña eliminada.");
                        reviewButton.setEnabled(true);
                        deleteButton.setEnabled(false);
                    }
                }
            });
            buttons.add(deleteButton);
        }

        bottom.add(buttons);

        dialog.add(new JScrollPane(infoPanel), BorderLayout.CENTER);
        dialog.add(bottom, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
  
}

