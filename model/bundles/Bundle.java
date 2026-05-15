package model.bundles;

import java.util.ArrayList;
import java.util.List;
import model.games.Juego;
import model.store.ArticuloComprable;
import repository.DataStore;

public class Bundle implements ArticuloComprable {

    private String nombre;
    private double descuento;
    private List<Juego> juegos;

    public Bundle(String nombre, double descuento) {
        this.nombre = nombre;
        this.descuento = descuento;
        this.juegos = new ArrayList<>();
    }

    public void addJuego(Juego juego) {
        juegos.add(juego);
    }

    public List<Juego> getJuegos(){
        return juegos;
    }
    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public double getPrecio() {
        return getPrecioSinDescuento() * (1 - descuento);
    }

    public double getPrecioSinDescuento() {
        double total = 0;
        for (Juego j : juegos) {
            total += j.getPrecio();
        }
        return total;
    }

    public double getDescuento() {
        return descuento;
    }

    public double getPrecioPara(model.users.Jugador jugador) {
        double total = getPrecioSinDescuento();
        double ownedTotal = 0;
        for (Juego j : juegos) {
            if (jugador.getLibreria().getItems().contains(j)) {
                ownedTotal += j.getPrecio();
            }
        }
        double remaining = total - ownedTotal;
        return remaining <= 0 ? 0 : remaining * (1 - descuento);
    }

    @Override
    public String toString(){
        double precio = DataStore.currentPlayer != null ? getPrecioPara(DataStore.currentPlayer) : getPrecio();
        return nombre + " Bundle - $" + String.format("%.2f", precio);
    }
}


