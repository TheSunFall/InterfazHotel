public class Huesped {
    //atributos
    private String nombre;
    private String tipoDocumento;
    private int numeroDocumento;
    private String pais;
    private String fechaNacimiento;
    private Reserva reserva;
    //constructores

    public Huesped(String nombre, String tipoDocumento, int numeroDocumento, String pais, String fechaNacimiento) {
        this.nombre = nombre;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.pais = pais;
        this.fechaNacimiento = fechaNacimiento;
    }

    //métodos
}