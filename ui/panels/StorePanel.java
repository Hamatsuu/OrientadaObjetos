package ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import model.bundles.Bundle;
import model.games.Juego;
import model.reviews.Review;
import model.store.ArticuloComprable;
import repository.DataStore;
import service.StoreService;

public class StorePanel extends JPanel {

    private DefaultListModel<ArticuloComprable> model;
    private JList<ArticuloComprable> list;
    private final StoreService service;
    private JLabel balanceLabel; // Etiqueta para el saldo

    public StorePanel() {
        setLayout(new BorderLayout());
        service = new StoreService();

        model = new DefaultListModel<>();
        for (ArticuloComprable item : DataStore.STORE_ITEMS) {
            model.addElement(item);
        }

        list = new JList<>(model);
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    ArticuloComprable selected = list.getSelectedValue();
                    if (selected != null) {
                        abrirDetallesTienda(selected);
                    }
                }
            }
        });

        // Panel de cabecera para el título y el saldo
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("Tienda Virtual");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        
        balanceLabel = new JLabel();
        refreshBalance(); // Inicializamos el texto del saldo
        balanceLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        balanceLabel.setForeground(new Color(46, 139, 87)); // Color verde tipo "dinero"

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(balanceLabel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);
        add(new JScrollPane(list), BorderLayout.CENTER);
    }

    // Método para actualizar visualmente el dinero del jugador
    public void refreshBalance() {
        balanceLabel.setText("Tu Saldo: $" + String.format("%.2f", DataStore.currentPlayer.getWallet()));
    }

    private void abrirDetallesTienda(ArticuloComprable item) {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Detalles del Producto", true);
        dialog.setSize(450, 500); // Un poco más alto para las reseñas
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        infoPanel.add(new JLabel("<html><h2 style='color:#333;'>" + item.getNombre() + "</h2></html>"));
        double precioActual = item.getPrecio();
        if (item instanceof Bundle) {
            precioActual = ((Bundle) item).getPrecioPara(DataStore.currentPlayer);
        }
        infoPanel.add(new JLabel("Precio: $" + String.format("%.2f", precioActual)));
        infoPanel.add(Box.createVerticalStrut(15));

        if (item instanceof Juego) {
            Juego juego = (Juego) item;
            infoPanel.add(new JLabel("<html><b>Género:</b> " + juego.getGenero() + "</html>"));
            infoPanel.add(Box.createVerticalStrut(5));
            infoPanel.add(new JLabel("<html><p width='300'><b>Descripción:</b> " + juego.getDescripcion() + "</p></html>"));
            
            // SECCIÓN DE RESEÑAS
            infoPanel.add(Box.createVerticalStrut(20));
            infoPanel.add(new JLabel("<html><b style='font-size:12px; color:#555;'>RESEÑAS DE LA COMUNIDAD:</b></html>"));
            infoPanel.add(new JSeparator());
            
            if (juego.getReviews().isEmpty()) {
                infoPanel.add(new JLabel("   Nadie ha escrito una reseña aún."));
            } else {
                for (Review r : juego.getReviews()) {
                    // Usamos el toString() que ya tenías en Review.java
                    JLabel reviewLabel = new JLabel("<html><p width='350' style='margin-bottom:5px;'>• " + r.toString() + "</p></html>");
                    infoPanel.add(reviewLabel);
                }
            }
        } 
        else if (item instanceof model.bundles.Bundle) {
            model.bundles.Bundle bundle = (model.bundles.Bundle) item;
            infoPanel.add(new JLabel("<html><b>Contenido del Bundle:</b></html>"));
            for (Juego j : bundle.getJuegos()) {
                infoPanel.add(new JLabel(" • " + j.getNombre()));
            }
        }

        JButton buyButton = new JButton("Confirmar Compra");
        buyButton.addActionListener(e -> {
            try {
                service.purchase(DataStore.currentPlayer, item);
                refreshStore(); // ¡Actualizamos el saldo en la tienda!
                JOptionPane.showMessageDialog(dialog, "¡Gracias por tu compra!");
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(new JScrollPane(infoPanel), BorderLayout.CENTER);
        dialog.add(buyButton, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    public void refreshStore() {
        model.clear();
        for (ArticuloComprable item : DataStore.STORE_ITEMS) {
            model.addElement(item);
        }
        list.repaint();
        refreshBalance();
    }
}

