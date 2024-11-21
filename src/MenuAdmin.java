import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class MenuAdmin extends JFrame{
    private Hotel hotel;
    private ArrayList<Admin> admins;
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JComboBox comboBox1;
    private JTextPane textPane1;
    private JButton confirmarHabitacion;
    private JButton confirmarAdmin;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JPasswordField passwordField1;
    private JTextPane textPane2;
    private JPanel AdminPanelHabitaciones;

    public MenuAdmin(Hotel hotel, ArrayList<Admin> admins) {
        this.hotel = hotel;
        this.admins = admins;
        setTitle("Menú de administración");
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        comboBox1.addItem("b - Habitación básica");
        comboBox1.addItem("e - Habitación ejecutiva");
        comboBox1.addItem("s - Habitación suite");
        pack();
        confirmarHabitacion.addActionListener(e -> {
            String piso = textField1.getText();
            String numero = textField2.getText();
            String tipo = (String) comboBox1.getSelectedItem();
            textPane1.setText("Creando habitación en piso " + piso + " y número " + numero + " de tipo " + tipo + "...");
            aniadirHabitacion(Integer.parseInt(piso), Integer.parseInt(numero), tipo.charAt(0));
        });
        confirmarAdmin.addActionListener(e -> {
            String nombre = textField3.getText();
            String apellidos = textField4.getText();
            String usuario = textField5.getText();
            String contrasena = new String(passwordField1.getPassword());
            textPane2.setText("Creando administrador " + nombre + " " + apellidos + " con usuario " + usuario + "...");
            admins.add(new Admin(nombre, apellidos, usuario, contrasena));
            textPane2.setText("Usuario de administrador creado con éxito");
        });
        AdminPanelHabitaciones.setLayout(new BoxLayout(AdminPanelHabitaciones, BoxLayout.Y_AXIS));
        AdminPanelHabitaciones.setBorder(new EmptyBorder(10, 10, 10, 10));
        for (int piso = 0; piso < hotel.getPisos(); piso++) {
            for (int numero = 0; numero < hotel.getNumeros(); numero++) {
                Habitacion habitacion;;
                try {
                    habitacion = hotel.getHabitacion(piso, numero);
                    JLabel txt = new JLabel(habitacion.Detalles());
                    AdminPanelHabitaciones.add(txt);
                } catch (HabitacionNoValida e) {
                }
            }
        }
    }
    public void aniadirHabitacion(int piso, int numero, char tipo) {
        try {
            hotel.CrearHabitacion(piso, numero, tipo);
            textPane1.setText("Habitación creada con éxito");
        } catch (Sobreescritura e) {
            textPane1.setText("Error: La habitación ya existe");
        } catch (IllegalArgumentException e) {
            textPane1.setText("Error: Tipo de habitación inválido");
        } catch (ArrayIndexOutOfBoundsException e) {
            textPane1.setText("Error: Piso o número inválidos");
        }
    }
}