package model.bundles;

import java.util.ArrayList;
import java.util.List;

import model.games.Juego;
import model.store.ArticuloComprable;

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
        double total = 0;

        for (Juego j : juegos) {
            total += j.getPrecio();
        }

        return total * (1 - descuento);
    }
    @Override
    public String toString(){
        return nombre + "Bundle - $" + getPrecio();
    }
}


