package model.users;

import model.library.Libreria;
import observer.Observador; // Agregamos el import

// Implementa la interfaz Observador
public class Jugador extends Usuario implements Observador {

    private double wallet;
    private Libreria libreria;

    public Jugador(String username, String email, double wallet) {
        super(username, email);
        this.wallet = wallet;
        this.libreria = new Libreria();
    }

    // Cumplimos con el método del UML para recibir la notificación
    @Override
    public void update(String mensaje) {
        System.out.println("🔔 Notificación para " + getUsername() + ": " + mensaje);
    }

    public double getWallet() {
        return wallet;
    }

    public void addFunds(double amount) {
        wallet += amount;
    }

    public boolean withdraw(double amount) {
        if(wallet >= amount) {
            wallet -= amount;
            return true;
        }

        return false;
    }

    public Libreria getLibreria() {
        return libreria;
    }
}


