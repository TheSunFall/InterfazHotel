import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.ArrayList;

public class MenuAdmin extends JFrame{
    private final Hotel hotel;
    private final ArrayList<Admin> admins;
    private JTabbedPane tabbedPane1;
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

    public MenuAdmin(Hotel hotel, ArrayList<Admin> admins) {
        this.hotel = hotel;
        this.admins = admins;
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
        for (int piso = 0; piso < hotel.getPisos(); piso++) {
            for (int numero = 0; numero < hotel.getNumeros(); numero++) {
                Habitacion habitacion;
                JPanel panel = new JPanel();
                try {
                    habitacion = hotel.getHabitacion(piso, numero);
                    JButton btn = new JButton("Eliminar habitación");
                    int pisoAct = piso;
                    int numeroAct = numero;
                    btn.addActionListener(e -> eliminarHabitacion(pisoAct, numeroAct));
                    JLabel txt = new JLabel(habitacion.Detalles());
                    panel.setBorder(new EmptyBorder(10, 10, 10, 10));
                    panel.add(txt);
                    panel.add(btn);
                    AdminPanelHabitaciones.add(panel);

                } catch (HabitacionNoValida e) {
                    System.out.println("No hay habitación en el piso " + (piso+1) + " y número " + (numero+1));
                }
            }
        }
    }
    public void aniadirHabitacion(int piso, int numero, char tipo) {
        try {
            hotel.CrearHabitacion(piso, numero, tipo);
            JOptionPane.showMessageDialog(null,"Habitación creada con éxito");
        } catch (Sobreescritura e) {
            JOptionPane.showMessageDialog(null,"Error: La habitación ya existe","Error",JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null,"Error: Tipo de habitación inválido","Error",JOptionPane.ERROR_MESSAGE);
        } catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null,"Error: Piso o número inválidos","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    public void eliminarHabitacion(int piso, int numero) {
        try {
            int result = JOptionPane.showConfirmDialog(null,"¿Está seguro de que desea eliminar la habitación?","Confirmar eliminación",JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                hotel.EliminarHabitacion(piso, numero);
                JOptionPane.showMessageDialog(null,"Habitación eliminada con éxito");
            }
        } catch (HabitacionNoValida e) {
            JOptionPane.showMessageDialog(null,"Error: La habitación no existe","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
}