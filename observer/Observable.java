package observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {

    private List<Observador> observadores;

    public Observable() {
        this.observadores = new ArrayList<>();
    }

    public void addObserver(Observador o) {
        if (!observadores.contains(o)) {
            observadores.add(o);
        }
    }

    public void removeObserver(Observador o) {
        observadores.remove(o);
    }

    public void notifyObservers(String mensaje) {
        for (Observador o : observadores) {
            o.update(mensaje);
        }
    }
}
