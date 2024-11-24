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

    public InterfazHotel(int pisos, int numeros) {
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
            System.out.println("No se encontraron datos previos. Creando nuevo hotel...");
            hotel = new Hotel(pisos, numeros);
            admins = new ArrayList<Admin>();
            admins.add(new Admin("Juan", "Pérez", "admin", "1234"));
        }
        setTitle("InterfazHotel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(Panel);
        PanelHabitaciones.setLayout(new BoxLayout(PanelHabitaciones,BoxLayout.Y_AXIS));
        PanelHabitaciones.setBorder(new EmptyBorder(10, 10, 10, 10));
        createUIComponents();
        pack();
        Confirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validarAdmin(AdminUsr.getText(),new String(AdminPassword.getPassword()));
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                guardarDatos();
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new InterfazHotel(3, 10);
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
    public void validarAdmin(String usuario, String password) {
        for (Admin admin : admins) {
            if (admin.getUsuario().equals(usuario) && admin.getContrasena().equals(password)) {
                JOptionPane.showMessageDialog(null, "Bienvenido, " + admin.getNombres() + " " + admin.getApellidos());
                MenuAdmin menuAdmin = new MenuAdmin(hotel, admins);
                menuAdmin.setVisible(true);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
    }
    private void createUIComponents() {
        for (int piso = 0; piso < hotel.getPisos(); piso++) {
            for (int numero = 0; numero < hotel.getNumeros(); numero++) {
                Habitacion habitacion;;
                try {
                    habitacion = hotel.getHabitacion(piso, numero);
                    HabitacionPanel panel = new HabitacionPanel(habitacion);
                    panel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    PanelHabitaciones.add(panel);
                } catch (HabitacionNoValida e) {
                }
            }
        }
    }
}