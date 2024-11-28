import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class InterfazHotel extends JFrame {
    private Hotel hotel;
    private ArrayList<Admin> admins;
    private JTabbedPane Tabs;
    private JPanel Panel;
    private JPasswordField AdminPassword;
    private JTextField AdminUsr;
    private JButton Confirmar;
    private JPanel PanelAdmin;
    private JPanel PanelHabitaciones;

    public InterfazHotel() {
        FileInputStream fhotel;
        FileInputStream fadmins;
        try {
            fhotel = new FileInputStream("hotel.dat");
            fadmins = new FileInputStream("admins.dat");
            ObjectInputStream inAdmins = new ObjectInputStream(fadmins);
            ObjectInputStream in = new ObjectInputStream(fhotel);
            hotel = (Hotel) in.readObject();
            admins = (ArrayList<Admin>) inAdmins.readObject();
            in.close();
            inAdmins.close();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null,"No se encontraron datos previos, se creará un nuevo hotel","Creando nuevo hotel",JOptionPane.WARNING_MESSAGE);
            JPanel Panel = new JPanel(new GridLayout(2, 2));
            JSpinner PisosSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
            JSpinner NumerosSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
            Panel.add(new JLabel("Ingrese la cantidad de pisos:"));
            Panel.add(PisosSpinner);
            Panel.add(new JLabel("Ingrese la cantidad de habitaciones por piso:"));
            Panel.add(NumerosSpinner);
            int confirmar = JOptionPane.showConfirmDialog(null, Panel, "Configuración inicial", JOptionPane.OK_CANCEL_OPTION);
            if (confirmar == JOptionPane.OK_OPTION) {
                int pisos = (int) PisosSpinner.getValue();
                int numeros = (int) NumerosSpinner.getValue();
                hotel = new Hotel(pisos, numeros);
                admins = new ArrayList<>();
                admins.add(new Admin("Admin", "1", "admin", "1234"));
                JOptionPane.showMessageDialog(null, "Se ha creado un usuario administrador por defecto\nUsuario: admin\nContraseña: 1234");
            } else {
                JOptionPane.showMessageDialog(null, "No se ha creado un hotel, el programa se cerrará", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
        setTitle("InterfazHotel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(Panel);
        PanelHabitaciones.setLayout(new BoxLayout(PanelHabitaciones,BoxLayout.Y_AXIS));
        PanelHabitaciones.setBorder(new EmptyBorder(10, 10, 10, 10));
        createUIComponents();
        pack();
        Confirmar.addActionListener(e -> validarAdmin(AdminUsr.getText(),new String(AdminPassword.getPassword())));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                guardarDatos();
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        JFrame frame = new InterfazHotel();
        frame.setVisible(true);
    }

    public void guardarDatos() {
        try {
            FileOutputStream fhotel = new FileOutputStream("hotel.dat");
            FileOutputStream fadmins = new FileOutputStream("admins.dat");
            ObjectOutputStream out = new ObjectOutputStream(fhotel);
            ObjectOutputStream outAdmins = new ObjectOutputStream(fadmins);
            out.writeObject(hotel);
            outAdmins.writeObject(admins);
            out.flush();
            outAdmins.flush();
            out.close();
            outAdmins.close();
        } catch (IOException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }
    public void actualizarPanelHabitaciones() {
        PanelHabitaciones.removeAll(); // Limpia las habitaciones actuales
        createUIComponents();         // Recrea las habitaciones
        PanelHabitaciones.revalidate(); // Refresca el panel
        PanelHabitaciones.repaint();   // Repinta el panel
    }

    public void validarAdmin(String usuario, String password) {
        for (Admin admin : admins) {
            if (admin.getUsuario().equals(usuario) && admin.getContrasena().equals(password)) {
                JOptionPane.showMessageDialog(null, "Bienvenido, " + admin.getNombres() + " " + admin.getApellidos());
                MenuAdmin menuAdmin = new MenuAdmin(hotel, admins, this::actualizarPanelHabitaciones);
                menuAdmin.setVisible(true);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
    }
    private void createUIComponents() {
        for (int piso = 0; piso < hotel.getPisos(); piso++) {
            for (int numero = 0; numero < hotel.getNumeros(); numero++) {
                Habitacion habitacion;
                try {
                    habitacion = hotel.getHabitacion(piso, numero);
                    HabitacionPanel panel = new HabitacionPanel(habitacion);
                    panel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    habitacion.setChangeListener(this::actualizarPanelHabitaciones);
                    PanelHabitaciones.add(panel);
                } catch (HabitacionNoValida e) {
                    System.out.println("No hay habitación en el piso " + (piso + 1) + " y número " + (numero + 1));
                }
            }
        }
    }
}