public class Admin extends Persona {
    private final String usuario;
    private final String contrasena;

    public Admin(String nombre, String apellidos, String usuario, String contrasena) {
        super(nombre, apellidos);
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasena() {
        return contrasena;
    }
}