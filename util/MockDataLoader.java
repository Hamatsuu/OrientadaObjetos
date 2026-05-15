package util;

import model.bundles.Bundle;
import model.games.Juego;
import model.users.Desarrollador;
import repository.DataStore;

public class MockDataLoader {

    public static void load() {

        Desarrollador dev1 = new Desarrollador(
                "voidmaster",
                "dev@studio.com",
                "Nebula Forge"
        );

        Juego game1 = new Juego(
                "Cyber Samurai",
                "Fast-paced cyberpunk action",
                39.99,
                "Action",
                dev1
        );

        Juego game2 = new Juego(
                "Dungeon Protocol",
                "Sci-fi roguelike",
                24.99,
                "RPG",
                dev1
        );

        Juego game3 = new Juego(
                "Farm Kingdom 2077",
                "Relax farming simulator",
                14.99,
                "Simulation",
                dev1
        );

        Bundle starterBundle = new Bundle(
                "Indie Starter Pack",
                0.25
        );

        starterBundle.addJuego(game1);
        starterBundle.addJuego(game2);

        DataStore.GAMES.add(game1);
        DataStore.GAMES.add(game2);
        DataStore.GAMES.add(game3);

        DataStore.STORE_ITEMS.add(game1);
        DataStore.STORE_ITEMS.add(game2);
        DataStore.STORE_ITEMS.add(game3);
        DataStore.STORE_ITEMS.add(starterBundle);
    }
}
