package service;

import model.store.ArticuloComprable;
import model.users.Jugador;

public class StoreService {

    // Cambiamos el boolean por void y agregamos 'throws Exception'
    public void purchase(Jugador jugador, ArticuloComprable item) throws Exception {

        // 1. Validar si el jugador ya tiene el artículo
        if (jugador.getLibreria().getItems().contains(item)) {
            // Lanzamos el error hacia la UI
            throw new Exception("Intento de compra denegado: Ya tienes " + item.getNombre() + " en tu librería.");
        }

        // 2. Intentar descontar el saldo
        if (!jugador.withdraw(item.getPrecio())) {
            // Si withdraw devuelve false, no hay dinero
            throw new Exception("Saldo insuficiente para comprar " + item.getNombre() + ".");
        }

        // 3. Agregar a la librería si todo salió bien
        jugador.getLibreria().addItem(item);
    }
}

