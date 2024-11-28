import java.io.Serializable;

public abstract class Persona implements Serializable {
    private final String nombres;
    private final String apellidos;


    public Persona(String nombre, String apellidos) {
        this.nombres = nombre;
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String DetallesMinimos() {
        return "Nombre: " + nombres + " " + apellidos + "\n";
    }
}