package service;

import model.bundles.Bundle;
import model.store.ArticuloComprable;
import model.users.Jugador;

public class StoreService {

    // Cambiamos el boolean por void y agregamos 'throws Exception'
    public void purchase(Jugador jugador, ArticuloComprable item) throws Exception {

        // 1. Validar si el jugador ya tiene el artículo exacto
        if (jugador.getLibreria().getItems().contains(item)) {
            throw new Exception("Intento de compra denegado: Ya tienes " + item.getNombre() + " en tu librería.");
        }

        double precio = item.getPrecio();

        if (item instanceof Bundle) {
            Bundle bundle = (Bundle) item;
            precio = bundle.getPrecioPara(jugador);
            if (precio <= 0) {
                throw new Exception("Ya posees todos los juegos de este bundle.");
            }
        }

        // 2. Intentar descontar el saldo
        if (!jugador.withdraw(precio)) {
            throw new Exception("Saldo insuficiente para comprar " + item.getNombre() + ".");
        }

        // 3. Agregar a la librería si todo salió bien
        jugador.getLibreria().addItem(item);
    }
}

