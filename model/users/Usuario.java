// Clase abstracta usuario
// Dependencia UserFactory
// debería implementar entre algunas cosas lo siguiente:
// email
// username
// password

package model.users;

public abstract class Usuario {

    protected String username;
    protected String email;

    public Usuario(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return username;
    }
}
