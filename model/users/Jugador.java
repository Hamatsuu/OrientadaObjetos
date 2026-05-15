package model.users;

import model.library.Libreria;

public class Jugador extends Usuario {

    private double wallet;
    private Libreria libreria;

    public Jugador(String username, String email, double wallet) {
        super(username, email);
        this.wallet = wallet;
        this.libreria = new Libreria();
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


