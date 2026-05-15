package factory;

import model.users.Desarrollador;
import model.users.Jugador;
import model.users.Usuario;

public class UserFactory {

    public static Usuario createUser(
            String type,
            String username,
            String email
    ) {

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
