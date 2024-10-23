public class Hotel {
    //atributos
    private final int pisos;
    private final int numeros;
    private final Habitacion[][] habitaciones;
    private Reserva[] reservas;
    //constructores

    /**
     * Crea el hotel con la cantidad de pisos y habitaciones por piso indicados
     */
    public Hotel(int pisos, int numeros) {
        this.pisos = pisos;
        this.numeros = numeros;
        habitaciones = new Habitacion[pisos][numeros];
    }

    //métodos

    /**
     * Crea una habitación en el piso y número indicados
     */
    public boolean CrearHabitacion(int piso, int numero) {
        if (habitaciones[piso][numero] == null) {
            int codigo = (piso + 1) * 100 + numero + 1;
            habitaciones[piso][numero] = new Habitacion(codigo);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Muestra las habitaciones existentes
     * indicando si están ocupadas o disponibles
     */
    public void VerHabitacionesDisponibles() {
        System.out.print("-------------------------------\n");
        System.out.println("Habitaciones Disponibles:");
        System.out.print("-------------------------------\n");
        for (int i = 0; i < pisos; i++) {
            int c = 0;
            for (int j = 0; j < numeros; j++) {
                if (habitaciones[i][j] != null) {
                    c++;
                    System.out.println(habitaciones[i][j].Detalles());
                }
            }
            if (c == 0) {
                System.out.println("No hay habitaciones en el piso " + i);
            }
        }
    }

    /**
     * Crea una reserva, la asigna a los huéspedes y asigna los huéspedes a las habitaciones
     *
     * @param huespedes arreglo de los huéspedes que realizarán la reserva
     * @param piso      el piso de la habitación a reservar
     * @param numero    el número de la habitación a reservar
     * @param dias      la cantidad de días que durará la reserva
     */
    public boolean CrearReserva(Huesped[] huespedes, int piso, int numero, int dias) {
        if (habitaciones[piso][numero] != null && habitaciones[piso][numero].Disponible()) {
            int codigo = piso * 100 + numero;
            Reserva reserva = new Reserva(codigo, dias, huespedes);
            for (Huesped huesped : huespedes) {
                habitaciones[piso][numero].AgregarHuesped(huesped);
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean CrearReserva(Huesped huesped, int piso, int numero, int dias) {
        if (habitaciones[piso][numero] != null && habitaciones[piso][numero].Disponible()) {
            int codigo = (piso + 1) * 100 + numero + 1;
            Reserva reserva = new Reserva(codigo, dias, huesped);
            habitaciones[piso][numero].AgregarHuesped(huesped);
            return true;
        } else {
            return false;
        }
    }
}

public class HotelSistema {
    private final Hotel hotel;
    private final Scanner scanner;
    private String adminUsuario = "admin";  
    private String adminPassword = "1234"; 

    public HotelSistema(int pisos, int numeros) {
        hotel = new Hotel(pisos, numeros);
        scanner = new Scanner(System.in);
    }

    public void menuPrincipal() {
        int opcion;
        do {
        	System.out.println("====================================");
            System.out.println("            ¡BIENVENIDO!");
            System.out.println("====================================");
            System.out.println("1. Ver habitaciones disponibles");
            System.out.println("2. Reservar habitación");
            System.out.println("3. Registrar salida");
            System.out.println("4. Generar boleta de pago");
            System.out.println("5. Menú para administradores");
            System.out.println("0. Salir");
            System.out.print("Ingrese su opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();  

            switch (opcion) {
                case 1:
                    hotel.VerHabitacionesDisponibles();
                    break;
                case 2:
                    realizarReserva();
                    break;
                case 3:
                    registrarSalida();
                    break;
                case 4:
                    generarBoletaPago();
                    break;
                case 5:
                	if (validarAdministrador()) {
                        menuAdministradores();
                    }
                    break;
                case 0:
                    System.out.println("¡Gracias por utilizar el sistema!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }

    private void realizarReserva() {
        System.out.print("Ingrese el número de huéspedes: ");
        int numHuespedes = scanner.nextInt();
        scanner.nextLine(); 

        Huesped[] huespedes = new Huesped[numHuespedes];
        for (int i = 0; i < numHuespedes; i++) {
            System.out.print("Ingrese el nombre del huésped " + (i + 1) + ": ");
            String nombre = scanner.nextLine();
            System.out.print("Ingrese el tipo de documento (ej. DNI): ");
            String tipoDocumento = scanner.nextLine();
            System.out.print("Ingrese el número de documento: ");
            int numeroDocumento = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Ingrese el país: ");
            String pais = scanner.nextLine();

            huespedes[i] = new Huesped(nombre, tipoDocumento, numeroDocumento, pais);
        }

        System.out.print("Ingrese el piso de la habitación: ");
        int piso = scanner.nextInt();
        System.out.print("Ingrese el número de la habitación: ");
        int numero = scanner.nextInt();
        System.out.print("Ingrese el número de días de la reserva: ");
        int dias = scanner.nextInt();
        
        if (hotel.CrearReserva(huespedes, piso, numero, dias)) {
            System.out.println("Reserva realizada con éxito.");
        } else {
            System.out.println("No se pudo realizar la reserva. Verifique la disponibilidad.");
        }
    }

    private void registrarSalida() {
        System.out.print("Ingrese el piso de la habitación: ");
        int piso = scanner.nextInt();
        System.out.print("Ingrese el número de la habitación: ");
        int numero = scanner.nextInt();

        Habitacion habitacion = hotel.getHabitaciones()[piso][numero];
        
        if (habitacion != null && !habitacion.Disponible()) {
            habitacion.Deshabilitar(); 
            System.out.println("Salida registrada con éxito.");
        } else {
            System.out.println("No se pudo registrar la salida. La habitación no está ocupada.");
        }
    }

    private void generarBoletaPago() {
        System.out.print("Ingrese el piso de la habitación: ");
        int piso = scanner.nextInt();
        System.out.print("Ingrese el número de la habitación: ");
        int numero = scanner.nextInt();

        Habitacion habitacion = hotel.getHabitaciones()[piso][numero];
        if (habitacion != null && !habitacion.Disponible()) {
            float total = habitacion.getPrecioNoche() * habitacion.getHuespedesActuales(); // Total simple, puedes ajustar según las políticas
            System.out.println("Boleta de Pago:");
            System.out.println("Habitación: " + habitacion.Detalles());
            System.out.println("Total a pagar: " + total);
        } else {
            System.out.println("No se pudo generar la boleta. La habitación no está ocupada.");
        }
    }
    
    private boolean validarAdministrador() {
        System.out.print("Ingrese el usuario de administrador: ");
        String usuarioIngresado = scanner.nextLine();
        System.out.print("Ingrese la contraseña: ");
        String passwordIngresado = scanner.nextLine();

        if (usuarioIngresado.equals(adminUsuario) && passwordIngresado.equals(adminPassword)) {
            System.out.println("Acceso concedido.");
            return true;
        } else {
            System.out.println("Usuario o contraseña incorrectos.");
            return false;
        }
    }
    
    private void menuAdministradores() {
        int opcionAdmin;
        do {
        	System.out.println("====================================");
            System.out.println("       Menú de administración");
            System.out.println("====================================");
            System.out.println("1. Añadir habitación");
            System.out.println("2. Modificar habitación");
            System.out.println("3. Colocar habitación fuera de servicio");
            System.out.println("4. Editar usuarios y contraseñas");
            System.out.println("5. Mostrar huéspedes registrados");
            System.out.println("0. Salir");
            System.out.print("Ingrese su opción: ");
            opcionAdmin = scanner.nextInt();
            scanner.nextLine();  

            switch (opcionAdmin) {
                case 1:
                    añadirHabitacion();
                    break;
                case 2:
                    modificarHabitacion();
                    break;
                case 3:
                    colocarHabitacionFueraDeServicio();
                    break;
                case 4:
                    editarUsuarioContraseña();
                    break;
                case 5:
                    mostrarHuespedesRegistrados();
                    break;
                case 0:
                    System.out.println("Saliendo del menú de administración.");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcionAdmin != 0);
    }

    private void añadirHabitacion() {
        System.out.print("Ingrese el piso de la nueva habitación: ");
        int piso = scanner.nextInt();
        System.out.print("Ingrese el número de la nueva habitación: ");
        int numero = scanner.nextInt();
        
        if (hotel.CrearHabitacion(piso, numero)) {
            System.out.println("Habitación añadida con éxito.");
        } else {
            System.out.println("No se pudo añadir la habitación.");
        }
    }
    
    private void colocarHabitacionFueraDeServicio() {
        System.out.print("Ingrese el piso de la habitación: ");
        int piso = scanner.nextInt();
        System.out.print("Ingrese el número de la habitación: ");
        int numero = scanner.nextInt();

        Habitacion habitacion = hotel.getHabitaciones()[piso][numero];
        if (habitacion != null) {
            habitacion.Deshabilitar();
            System.out.println("Habitación colocada fuera de servicio.");
        } else {
            System.out.println("No se encontró la habitación.");
        }
    }
    
    private void modificarHabitacion() {
        System.out.print("Ingrese el piso de la habitación a modificar: ");
        int piso = scanner.nextInt();
        System.out.print("Ingrese el número de la habitación a modificar: ");
        int numero = scanner.nextInt();

        Habitacion habitacion = hotel.getHabitaciones()[piso][numero];
        if (habitacion != null) {
            System.out.println("Estado actual de la habitación:");
            System.out.println(habitacion.Detalles());
            
            System.out.print("¿Desea cambiar la disponibilidad de la habitación? (1 = Sí, 0 = No): ");
            int cambiarDisponibilidad = scanner.nextInt();
            if (cambiarDisponibilidad == 1) {
                if (habitacion.Disponible()) {
                    habitacion.Deshabilitar();
                    System.out.println("La habitación ahora está fuera de servicio.");
                } else {
                    System.out.println("Habitación fuera de servicio. Habilitándola de nuevo...");
                }
            }
        } else {
            System.out.println("La habitación no existe.");
        }
    }
    
    private void mostrarHuespedesRegistrados() {
        System.out.println("Huéspedes registrados:");
        boolean huespedesEncontrados = false;
        for (int piso = 0; piso < hotel.getHabitaciones().length; piso++) {
            for (int numero = 0; numero < hotel.getHabitaciones()[piso].length; numero++) {
                Habitacion habitacion = hotel.getHabitaciones()[piso][numero];
                if (habitacion != null && !habitacion.Disponible()) {
                    Huesped[] huespedes = habitacion.getHuespedes();  
                    for (Huesped huesped : huespedes) {
                        if (huesped != null) {
                            System.out.println(huesped.Detalles()); 
                            huespedesEncontrados = true;
                        }
                    }
                }
            }
        }
        if (!huespedesEncontrados) {
            System.out.println("No se encontraron huéspedes registrados.");
        }
    }
    
    private void editarUsuarioContraseña() {
        System.out.print("Ingrese el nuevo nombre de usuario: ");
        adminUsuario = scanner.nextLine();
        System.out.print("Ingrese la nueva contraseña: ");
        adminPassword = scanner.nextLine();
        System.out.println("Usuario y contraseña actualizados.");
    }

    public static void main(String[] args) {
        HotelSistema sistema = new HotelSistema(3, 10); 
        sistema.menuPrincipal();
    }
}
