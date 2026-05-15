// Clase Libreria
// Agrega Jugador
//
// Debe dejarme agregar articulos, buscarlo y obtener el total

package model.library;

import java.util.ArrayList;
import java.util.List;

import model.store.ArticuloComprable;

public class Libreria {

    private List<ArticuloComprable> items;

    public Libreria() {
        items = new ArrayList<>();
    }

    public void addItem(ArticuloComprable item) {
        items.add(item);
    }

    public List<ArticuloComprable> getItems() {
        return items;
    }
}
