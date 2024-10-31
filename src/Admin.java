public class Admin extends Persona {
    private String usuario;
    private String contrasena;

    public Admin(String nombre, String apellidos, String usuario, String contrasena) {
        super(nombre, apellidos);
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public String Detalles() {
        return super.DetallesMinimos() + "\nTiene permisos de administrador";
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasena() {
        return contrasena;
    }
}