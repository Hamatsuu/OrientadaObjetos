package repository;

import java.util.ArrayList;
import java.util.List;
import model.games.Juego;
import model.store.ArticuloComprable;
import model.users.Jugador;

public class DataStore {
 
    
    public static List<ArticuloComprable> STORE_ITEMS = new ArrayList<>();

    public static List<Jugador> PLAYERS = new ArrayList<>();

    public static Jugador currentPlayer;

    public static List<Juego> GAMES = new ArrayList<>();

    public static boolean isUsernameTaken(String username) {
        if (username == null) return false;
        return PLAYERS.stream()
                .anyMatch(p -> p.getUsername().equalsIgnoreCase(username));
    }

    public static void addPlayer(Jugador jugador) {
        if (jugador == null) {
            throw new IllegalArgumentException("Jugador no puede ser nulo");
        }
        if (isUsernameTaken(jugador.getUsername())) {
            throw new IllegalArgumentException("Nombre de usuario ya existe: " + jugador.getUsername());
        }
        PLAYERS.add(jugador);
    }
}
