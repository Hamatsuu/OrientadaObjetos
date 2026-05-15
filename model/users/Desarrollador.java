package model.users;

import java.util.ArrayList;
import java.util.List;

import model.games.Juego;

public class Desarrollador extends Usuario {

    private String studioName;
    private List<Juego> publishedGames;

    public Desarrollador(String username, String email, String studioName) {
        super(username, email);
        this.studioName = studioName;
        this.publishedGames = new ArrayList<>();
    }

    public void publishGame(Juego juego) {
        publishedGames.add(juego);
    }

    public List<Juego> getPublishedGames() {
        return publishedGames;
    }

    public String getStudioName() {
        return studioName;
    }
}
