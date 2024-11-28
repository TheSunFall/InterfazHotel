import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.ArrayList;

public class MenuAdmin extends JFrame {
    private final Hotel hotel;
    private JTabbedPane TabsPanel;
    private JPanel panel1;
    private JTextField piso;
    private JTextField numero;
    private JComboBox<String> tipo;
    private JButton confirmarHabitacion;
    private JButton confirmarAdmin;
    private JTextField Nombres;
    private JTextField Apellidos;
    private JTextField NombreUsuario;
    private JPasswordField Password;
    private JPanel AdminPanelHabitaciones;
    private final HotelChangeListener changeListener;


    public MenuAdmin(Hotel hotel, ArrayList<Admin> admins, HotelChangeListener listener) {
        this.hotel = hotel;
        this.changeListener = listener;
        setTitle("Menú de administración");
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        tipo.addItem("b - Habitación básica");
        tipo.addItem("e - Habitación ejecutiva");
        tipo.addItem("s - Habitación suite");
        pack();

        confirmarHabitacion.addActionListener(e -> {
            String piso = this.piso.getText();
            String numero = this.numero.getText();
            String tipo = (String) this.tipo.getSelectedItem();
            assert tipo != null;
            aniadirHabitacion(Integer.parseInt(piso), Integer.parseInt(numero), tipo.charAt(0));
        });

        confirmarAdmin.addActionListener(e -> {
            String nombre = Nombres.getText();
            String apellidos = Apellidos.getText();
            String usuario = NombreUsuario.getText();
            String contrasena = new String(Password.getPassword());
            admins.add(new Admin(nombre, apellidos, usuario, contrasena));
            JOptionPane.showMessageDialog(null,"Usuario de administrador creado con éxito");
        });

        AdminPanelHabitaciones.setLayout(new BoxLayout(AdminPanelHabitaciones, BoxLayout.Y_AXIS));
        AdminPanelHabitaciones.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Recorrer las habitaciones y agregar botones para eliminar y modificar
        for (int piso = 0; piso < hotel.getPisos(); piso++) {
            for (int numero = 0; numero < hotel.getNumeros(); numero++) {
                Habitacion habitacion;
                JPanel panel = new JPanel();
                try {
                    habitacion = hotel.getHabitacion(piso, numero);
                    JButton btnEliminar = new JButton("Eliminar habitación");
                    JButton btnModificarTipo = new JButton("Modificar tipo de habitación");

                    int pisoAct = piso;
                    int numeroAct = numero;

                    btnEliminar.addActionListener(e -> {
                        if (!habitacion.Disponible()) {
                            btnEliminar.setEnabled(false);  // Deshabilitar botón si la habitación está ocupada
                            btnModificarTipo.setEnabled(false);
                            JOptionPane.showMessageDialog(null, "No se puede eliminar la habitación. Está ocupada.", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            eliminarHabitacion(pisoAct, numeroAct);
                        }
                    });

                    // Acción para modificar el tipo de habitación
                    btnModificarTipo.addActionListener(e -> {
                        if (!habitacion.Disponible()) {
                            btnEliminar.setEnabled(false);  // Deshabilitar botón si la habitación está ocupada
                            btnModificarTipo.setEnabled(false);
                            JOptionPane.showMessageDialog(null, "No se puede modificar la habitación. Está ocupada.", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            String[] tipos = {"b - Habitación básica", "e - Habitación ejecutiva", "s - Habitación suite"};
                            JComboBox<String> comboBox = new JComboBox<>(tipos);
                            int result = JOptionPane.showOptionDialog(null, comboBox, "Selecciona el nuevo tipo de habitación",
                                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

                            if (result == JOptionPane.OK_OPTION) {
                                String nuevoTipo = (String) comboBox.getSelectedItem();
                                if (nuevoTipo != null) {
                                    char tipoSeleccionado = nuevoTipo.charAt(0); // 'b', 'e', o 's'
                                    try {
                                        hotel.CambiarTipoHabitacion(pisoAct, numeroAct, tipoSeleccionado);
                                        JOptionPane.showMessageDialog(null, "Tipo de habitación modificado con éxito");
                                        actualizarPanelHabitaciones();  // Actualiza el panel para reflejar el cambio
                                        if (changeListener != null) {
                                            changeListener.onHotelChanged(); // Notifica el cambio
                                        }
                                    } catch (HabitacionNoValida ex) {
                                        JOptionPane.showMessageDialog(null, "Error: La habitación no existe", "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            }
                        }
                    });

                    JLabel txt = new JLabel(habitacion.Detalles());
                    panel.setBorder(new EmptyBorder(10, 10, 10, 10));
                    panel.add(txt);
                    panel.add(btnEliminar);
                    panel.add(btnModificarTipo); // Añadir el botón de modificar tipo

                    AdminPanelHabitaciones.add(panel);

                } catch (HabitacionNoValida e) {
                    System.out.println("No hay habitación en el piso " + (piso+1) + " y número " + (numero+1));
                }
            }
        }
    }

    // Método para añadir habitación
    public void aniadirHabitacion(int piso, int numero, char tipo) {
        try {
            hotel.CrearHabitacion(piso, numero, tipo);
            JOptionPane.showMessageDialog(null,"Habitación creada con éxito");

            actualizarPanelHabitaciones();
            if (changeListener != null) {
                changeListener.onHotelChanged(); // Notifica el cambio
            }
        } catch (Sobreescritura e) {
            JOptionPane.showMessageDialog(null,"Error: La habitación ya existe","Error",JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null,"Error: Tipo de habitación inválido","Error",JOptionPane.ERROR_MESSAGE);
        } catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null,"Error: Piso o número inválidos","Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para eliminar habitación
    public void eliminarHabitacion(int piso, int numero) {
        try {
            int result = JOptionPane.showConfirmDialog(null,"¿Está seguro de que desea eliminar la habitación?","Confirmar eliminación",JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                hotel.EliminarHabitacion(piso, numero);
                JOptionPane.showMessageDialog(null,"Habitación eliminada con éxito");

                actualizarPanelHabitaciones();
                if (changeListener != null) {
                    changeListener.onHotelChanged(); // Notifica el cambio
                }
            }
        } catch (HabitacionNoValida e) {
            JOptionPane.showMessageDialog(null,"Error: La habitación no existe","Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para actualizar el panel de habitaciones
    public void actualizarPanelHabitaciones() {
        AdminPanelHabitaciones.removeAll();  // Limpiar el panel actual

        // Recorrer las habitaciones y agregar botones para eliminar y modificar
        for (int piso = 0; piso < hotel.getPisos(); piso++) {
            for (int numero = 0; numero < hotel.getNumeros(); numero++) {
                Habitacion habitacion;
                JPanel panel = new JPanel();
                try {
                    habitacion = hotel.getHabitacion(piso, numero);
                    JButton btnEliminar = new JButton("Eliminar habitación");
                    JButton btnModificarTipo = new JButton("Modificar tipo de habitación");

                    int pisoAct = piso;
                    int numeroAct = numero;

                    // Acción para eliminar habitación
                    btnEliminar.addActionListener(e -> eliminarHabitacion(pisoAct, numeroAct));

                    // Acción para modificar el tipo de habitación
                    btnModificarTipo.addActionListener(e -> {
                        String[] tipos = {"b - Habitación básica", "e - Habitación ejecutiva", "s - Habitación suite"};
                        JComboBox<String> comboBox = new JComboBox<>(tipos);
                        int result = JOptionPane.showOptionDialog(null, comboBox, "Selecciona el nuevo tipo de habitación",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

                        if (result == JOptionPane.OK_OPTION) {
                            String nuevoTipo = (String) comboBox.getSelectedItem();
                            if (nuevoTipo != null) {
                                char tipoSeleccionado = nuevoTipo.charAt(0); // 'b', 'e', o 's'
                                try {
                                    hotel.CambiarTipoHabitacion(pisoAct, numeroAct, tipoSeleccionado);
                                    JOptionPane.showMessageDialog(null, "Tipo de habitación modificado con éxito");
                                    actualizarPanelHabitaciones();  // Actualiza el panel para reflejar el cambio
                                } catch (HabitacionNoValida ex) {
                                    JOptionPane.showMessageDialog(null, "Error: La habitación no existe", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    });

                    JLabel txt = new JLabel(habitacion.Detalles());
                    panel.setBorder(new EmptyBorder(10, 10, 10, 10));
                    panel.add(txt);
                    panel.add(btnEliminar);
                    panel.add(btnModificarTipo); // Añadir el botón de modificar tipo

                    AdminPanelHabitaciones.add(panel);

                } catch (HabitacionNoValida e) {
                    System.out.println("No hay habitación en el piso " + (piso+1) + " y número " + (numero+1));
                }
            }
        }
        AdminPanelHabitaciones.revalidate();  // Refrescar el panel
        AdminPanelHabitaciones.repaint();     // Repintar el panel
    }

}
