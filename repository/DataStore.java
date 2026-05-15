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
}
