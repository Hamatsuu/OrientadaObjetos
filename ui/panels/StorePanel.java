package ui.panels;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.store.ArticuloComprable;
import repository.DataStore;
import service.StoreService;

public class StorePanel extends JPanel {

    private JList<ArticuloComprable> list;

    public StorePanel() {

        setLayout(new BorderLayout());

        DefaultListModel<ArticuloComprable> model =
                new DefaultListModel<>();

        for(ArticuloComprable item : DataStore.STORE_ITEMS) {
            model.addElement(item);
        }

        list = new JList<>(model);

        JButton buyButton = new JButton("Comprar");

        buyButton.addActionListener(e -> purchaseSelected());

        add(new JLabel("Tienda"), BorderLayout.NORTH);
        add(new JScrollPane(list), BorderLayout.CENTER);
        add(buyButton, BorderLayout.SOUTH);
    }

    private void purchaseSelected() {

        ArticuloComprable selected = list.getSelectedValue();

        if(selected == null) {
            return;
        }

        StoreService service = new StoreService();

        try {
            // Intentamos hacer la compra
            service.purchase(DataStore.currentPlayer, selected);
            
            // Si no hay errores, mostramos mensaje de éxito
            JOptionPane.showMessageDialog(this,
                    "¡Compra realizada con éxito!", 
                    "Compra", 
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            // Si el servicio lanza un error, lo atrapamos y lo mostramos en la UI
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(), 
                    "Error en la compra", 
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}
