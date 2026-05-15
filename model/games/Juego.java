package model.games;

import java.util.ArrayList;
import java.util.List;

import model.reviews.Review;
import model.store.ArticuloComprable;
import model.users.Desarrollador;
import observer.Observable; // Agregamos el import

// Hereda de Observable e implementa ArticuloComprable
public class Juego extends Observable implements ArticuloComprable {

    private String nombre;
    private String descripcion;
    private double precio;
    private String genero;

    private Desarrollador desarrollador;

    private List<Review> reviews;

    public Juego(
            String nombre,
            String descripcion,
            double precio,
            String genero,
            Desarrollador desarrollador
    ) {
        super(); // Llama al constructor de Observable para la lista de observadores
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.genero = genero;
        this.desarrollador = desarrollador;

        this.reviews = new ArrayList<>();
    }

    // Método añadido del UML para disparar la notificación
    public void aplicarDescuento(double nuevoPrecio) {
        this.precio = nuevoPrecio;
        notifyObservers("¡El juego " + nombre + " ahora cuesta $" + precio + "!");
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public double getPrecio() {
        return precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getGenero() {
        return genero;
    }

    public Desarrollador getDesarrollador() {
        return desarrollador;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void addReview(Review review) {
        reviews.add(review);
    }

    @Override
    public String toString() {
        return nombre + " - $" + precio;
    }
}

