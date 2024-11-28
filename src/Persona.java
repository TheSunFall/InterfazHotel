import java.io.Serializable;

public abstract class Persona implements Serializable {
    private String nombres;
    private String apellidos;


    public Persona(String nombre, String apellidos) {
        this.nombres = nombre;
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String DetallesMinimos() {
        return "Nombre: " + nombres + " " + apellidos + "\n";
    }
}