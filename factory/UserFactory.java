package factory;

import model.users.Desarrollador;
import model.users.Jugador;
import model.users.Usuario;
import repository.DataStore;

public class UserFactory {

    public static Usuario createUser(
            String type,
            String username,
            String email
    ) {

        if (DataStore.isUsernameTaken(username)) {
            throw new IllegalArgumentException("Nombre de usuario ya existe: " + username);
        }

        switch(type.toLowerCase()) {

            case "jugador":
                return new Jugador(username, email, 500);

            case "desarrollador":
                return new Desarrollador(username, email, "Unknown Studio");

            default:
                throw new IllegalArgumentException("Tipo inválido");
        }
    }
}
