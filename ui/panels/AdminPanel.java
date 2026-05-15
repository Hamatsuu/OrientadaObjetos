package ui.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import model.games.Juego;
import model.users.Desarrollador;
import model.users.Jugador;
import repository.DataStore;
import ui.panels.LibraryPanel;
import ui.panels.StorePanel;

public class AdminPanel extends JPanel {

    private final DefaultComboBoxModel<Jugador> userModel;
    private final JComboBox<Jugador> globalUserSelector;
    private final StorePanel storePanel;
    private final LibraryPanel libraryPanel;
    private JComboBox<Jugador> balanceUserSelector;
    private JLabel currentBalanceLabel;

    public AdminPanel(DefaultComboBoxModel<Jugador> userModel, JComboBox<Jugador> globalUserSelector, StorePanel storePanel, LibraryPanel libraryPanel) {
        this.userModel = userModel;
        this.globalUserSelector = globalUserSelector;
        this.storePanel = storePanel;
        this.libraryPanel = libraryPanel;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        content.add(createUserPanel());
        content.add(Box.createVerticalStrut(10));
        content.add(createBalancePanel());
        content.add(Box.createVerticalStrut(10));
        content.add(createGamePanel());

        add(content, BorderLayout.NORTH);

        balanceUserSelector.setSelectedItem(DataStore.currentPlayer);
        updateBalanceLabel();
    }

    private JPanel createUserPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 8, 8));
        panel.setBorder(new TitledBorder("Administrar Usuarios"));

        JTextField userNameField = new JTextField();
        JTextField userEmailField = new JTextField();
        JTextField userBalanceField = new JTextField("0");
        JButton addUserButton = new JButton("Agregar Usuario");

        panel.add(new JLabel("Nombre de usuario:"));
        panel.add(userNameField);
        panel.add(new JLabel("Email:"));
        panel.add(userEmailField);
        panel.add(new JLabel("Saldo inicial:"));
        panel.add(userBalanceField);
        panel.add(new JLabel());
        panel.add(addUserButton);

        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userNameField.getText().trim();
                String email = userEmailField.getText().trim();
                String balanceText = userBalanceField.getText().trim();
                if (username.isEmpty() || email.isEmpty() || balanceText.isEmpty()) {
                    return;
                }
                double balance;
                try {
                    balance = Double.parseDouble(balanceText);
                } catch (NumberFormatException ex) {
                    return;
                }
                Jugador nuevo = new Jugador(username, email, balance);
                DataStore.PLAYERS.add(nuevo);
                userModel.addElement(nuevo);
                globalUserSelector.setSelectedItem(nuevo);
                DataStore.currentPlayer = nuevo;
                storePanel.refreshStore();
                libraryPanel.refresh();
                balanceUserSelector.setSelectedItem(nuevo);
                updateBalanceLabel();
                userNameField.setText("");
                userEmailField.setText("");
                userBalanceField.setText("0");
            }
        });

        return panel;
    }

    private JPanel createBalancePanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 8, 8));
        panel.setBorder(new TitledBorder("Modificar Saldo"));

        balanceUserSelector = new JComboBox<>(userModel);
        JTextField amountField = new JTextField("0");
        JButton addFundsButton = new JButton("Agregar Saldo");
        JButton setFundsButton = new JButton("Establecer Saldo");
        currentBalanceLabel = new JLabel("Saldo actual: $0.00");

        panel.add(new JLabel("Usuario:"));
        panel.add(balanceUserSelector);
        panel.add(new JLabel("Cantidad:"));
        panel.add(amountField);
        panel.add(addFundsButton);
        panel.add(setFundsButton);
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(currentBalanceLabel);

        balanceUserSelector.addActionListener(e -> updateBalanceLabel());

        addFundsButton.addActionListener(e -> {
            Jugador selected = (Jugador) balanceUserSelector.getSelectedItem();
            if (selected == null) {
                return;
            }
            double amount;
            try {
                amount = Double.parseDouble(amountField.getText().trim());
            } catch (NumberFormatException ex) {
                return;
            }
            selected.addFunds(amount);
            updateBalanceLabel();
            if (selected == DataStore.currentPlayer) {
                storePanel.refreshStore();
            }
        });

        setFundsButton.addActionListener(e -> {
            Jugador selected = (Jugador) balanceUserSelector.getSelectedItem();
            if (selected == null) {
                return;
            }
            double amount;
            try {
                amount = Double.parseDouble(amountField.getText().trim());
            } catch (NumberFormatException ex) {
                return;
            }
            double delta = amount - selected.getWallet();
            selected.addFunds(delta);
            updateBalanceLabel();
            if (selected == DataStore.currentPlayer) {
                storePanel.refreshStore();
            }
        });

        return panel;
    }

    private JPanel createGamePanel() {
        JPanel panel = new JPanel(new GridLayout(7, 2, 8, 8));
        panel.setBorder(new TitledBorder("Agregar Juegos"));

        JTextField gameNameField = new JTextField();
        JTextField gameDescField = new JTextField();
        JTextField gamePriceField = new JTextField("0");
        JTextField gameGenreField = new JTextField();
        JTextField devNameField = new JTextField();
        JTextField devStudioField = new JTextField();
        JButton addGameButton = new JButton("Agregar Juego");

        panel.add(new JLabel("Nombre del juego:"));
        panel.add(gameNameField);
        panel.add(new JLabel("Descripción:"));
        panel.add(gameDescField);
        panel.add(new JLabel("Precio:"));
        panel.add(gamePriceField);
        panel.add(new JLabel("Género:"));
        panel.add(gameGenreField);
        panel.add(new JLabel("Dev nombre:"));
        panel.add(devNameField);
        panel.add(new JLabel("Dev estudio:"));
        panel.add(devStudioField);
        panel.add(new JLabel());
        panel.add(addGameButton);

        addGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = gameNameField.getText().trim();
                String descripcion = gameDescField.getText().trim();
                String precioText = gamePriceField.getText().trim();
                String genero = gameGenreField.getText().trim();
                String devNombre = devNameField.getText().trim();
                String devStudio = devStudioField.getText().trim();

                if (nombre.isEmpty() || descripcion.isEmpty() || precioText.isEmpty() || genero.isEmpty() || devNombre.isEmpty() || devStudio.isEmpty()) {
                    return;
                }

                double precio;
                try {
                    precio = Double.parseDouble(precioText);
                } catch (NumberFormatException ex) {
                    return;
                }

                Desarrollador desarrollador = new Desarrollador(devNombre, devNombre.toLowerCase() + "@studio.com", devStudio);
                Juego juego = new Juego(nombre, descripcion, precio, genero, desarrollador);
                DataStore.GAMES.add(juego);
                DataStore.STORE_ITEMS.add(juego);
                storePanel.refreshStore();

                gameNameField.setText("");
                gameDescField.setText("");
                gamePriceField.setText("0");
                gameGenreField.setText("");
                devNameField.setText("");
                devStudioField.setText("");
            }
        });

        return panel;
    }

    private void updateBalanceLabel() {
        Jugador selected = (Jugador) balanceUserSelector.getSelectedItem();
        if (selected != null) {
            currentBalanceLabel.setText("Saldo actual: $" + String.format("%.2f", selected.getWallet()));
        } else {
            currentBalanceLabel.setText("Saldo actual: $0.00");
        }
    }
}
