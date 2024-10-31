import java.util.Scanner;

public class InterfazHotel implements Menu, MenuAdmin {
    private final Hotel hotel;
    private final Scanner scanner;
    private Admin[] admins;
    private int cantAdmins;

    public InterfazHotel(int pisos, int numeros) {
        hotel = new Hotel(pisos, numeros);
        scanner = new Scanner(System.in);
        admins = new Admin[10];
        admins[0] = new Admin("Juan", "Pérez", "admin", "1234");
        cantAdmins = 1;
    }

    public static void main(String[] args) {
        InterfazHotel sistema = new InterfazHotel(3, 10);
        sistema.MenuPrincipal();
    }

    public void MenuPrincipal() {
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

    public void realizarReserva() {
        System.out.print("Ingrese el número de huéspedes: ");
        int numHuespedes = scanner.nextInt();
        scanner.nextLine();

        Huesped[] huespedes = new Huesped[numHuespedes];
        for (int i = 0; i < numHuespedes; i++) {
            System.out.print("Ingrese los nombres del huésped " + (i + 1) + ": ");
            String nombres = scanner.nextLine();
            System.out.print("Ingrese los apellidos del huésped " + (i + 1) + ": ");
            String apellidos = scanner.nextLine();
            System.out.print("Ingrese el tipo de documento (ej. DNI): ");
            String tipoDocumento = scanner.nextLine();
            System.out.print("Ingrese el número de documento: ");
            String numeroDocumento = scanner.nextLine();
            scanner.nextLine();
            System.out.print("Ingrese el país: ");
            String pais = scanner.nextLine();

            huespedes[i] = new Huesped(nombres, apellidos, tipoDocumento, numeroDocumento, pais);
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

    public void registrarSalida() {
        System.out.print("Ingrese el piso de la habitación: ");
        int piso = scanner.nextInt();
        System.out.print("Ingrese el número de la habitación: ");
        int numero = scanner.nextInt();

        Habitacion habitacion = hotel.getHabitacion(piso, numero);

        if (habitacion != null && !habitacion.Disponible()) {
            habitacion.Deshabilitar();
            System.out.println("Salida registrada con éxito.");
        } else {
            System.out.println("No se pudo registrar la salida. La habitación no está ocupada.");
        }
    }

    public void generarBoletaPago() {
        System.out.print("Ingrese el piso de la habitación: ");
        int piso = scanner.nextInt();
        System.out.print("Ingrese el número de la habitación: ");
        int numero = scanner.nextInt();

        Habitacion habitacion = hotel.getHabitacion(piso, numero);
        if (habitacion != null && !habitacion.Disponible()) {
            float total = habitacion.getPrecioNoche() * habitacion.getHuespedesActuales(); // Total simple, puedes ajustar según las políticas
            System.out.println("Boleta de Pago:");
            System.out.println("Habitación: " + habitacion.Detalles());
            System.out.println("Total a pagar: " + total);
        } else {
            System.out.println("No se pudo generar la boleta. La habitación no está ocupada.");
        }
    }

    public boolean validarAdministrador() {
        System.out.print("Ingrese el usuario de administrador: ");
        String usuarioIngresado = scanner.nextLine();
        System.out.print("Ingrese la contraseña: ");
        String passwordIngresado = scanner.nextLine();

        for (Admin admin : admins) {
            if (admin != null && usuarioIngresado.equals(admin.getUsuario()) && passwordIngresado.equals(admin.getContrasena())) {
                System.out.println("Acceso concedido.");
                return true;
            }
        }

        System.out.println("Usuario o contraseña incorrectos.");
        return false;

    }

    public void menuAdministradores() {
        int opcionAdmin;
        do {
            System.out.println("====================================");
            System.out.println("       Menú de administración");
            System.out.println("====================================");
            System.out.println("1. Añadir habitación");
            System.out.println("2. Modificar habitación");
            System.out.println("3. Colocar habitación fuera de servicio");
            System.out.println("4. Añadir usuario de administrador");
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
                    aniadirAdmin();
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

    public void añadirHabitacion() {
        System.out.print("Ingrese el piso de la nueva habitación: ");
        int piso = scanner.nextInt() - 1;
        System.out.print("Ingrese el número de la nueva habitación: ");
        int numero = scanner.nextInt() - 1;
        System.out.println("Ingrese tipo de habitación: (b)ásica, (e)jecutiva, (s)uite: ");
        char tipo = scanner.next().charAt(0);
        if (hotel.CrearHabitacion(piso, numero, tipo)) {
            System.out.println("Habitación añadida con éxito.");
        } else {
            System.out.println("No se pudo añadir la habitación.");
        }
    }

    public void colocarHabitacionFueraDeServicio() {
        System.out.print("Ingrese el piso de la habitación: ");
        int piso = scanner.nextInt();
        System.out.print("Ingrese el número de la habitación: ");
        int numero = scanner.nextInt();

        Habitacion habitacion = hotel.getHabitacion(piso, numero);
        if (habitacion != null) {
            habitacion.Deshabilitar();
            System.out.println("Habitación colocada fuera de servicio.");
        } else {
            System.out.println("No se encontró la habitación.");
        }
    }

    public void modificarHabitacion() {
        System.out.print("Ingrese el piso de la habitación a modificar: ");
        int piso = scanner.nextInt();
        System.out.print("Ingrese el número de la habitación a modificar: ");
        int numero = scanner.nextInt();

        Habitacion habitacion = hotel.getHabitacion(piso, numero);
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

    public void mostrarHuespedesRegistrados() {
        System.out.println("Huéspedes registrados:");
        boolean huespedesEncontrados = false;
        for (int piso = 0; piso < hotel.getPisos(); piso++) {
            for (int numero = 0; numero < hotel.getNumeros(); numero++) {
                Habitacion habitacion = hotel.getHabitacion(piso, numero);
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

    public void aniadirAdmin() {
        System.out.print("Ingrese los nombres: ");
        String nombresAdmin = scanner.nextLine();
        System.out.print("Ingrese los apellidos: ");
        String apellidosAdmin = scanner.nextLine();
        System.out.print("Ingrese el nombre de usuario: ");
        String usuarioAdmin = scanner.nextLine();
        System.out.print("Ingrese la contraseña: ");
        String adminPassword = scanner.nextLine();
        admins[cantAdmins] = new Admin(nombresAdmin, apellidosAdmin, usuarioAdmin, adminPassword);
        System.out.println("Usuario y contraseña actualizados.");
    }
}