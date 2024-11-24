import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HabitacionPanel extends JPanel{
    private JButton Reservar;
    private JButton TerminarReserva;
    public HabitacionPanel(Habitacion h){
        add(new JLabel(h.Detalles()));
        add(Reservar);
        add(TerminarReserva);
        Reservar.setEnabled(h.Disponible());
        TerminarReserva.setEnabled(!h.Disponible());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        Reservar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int num = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad de huéspedes"));
                if (num > h.getMaxHuespedes()) {
                    JOptionPane.showMessageDialog(null, "La cantidad de huéspedes supera la capacidad de la habitación");
                    return;
                }

                Huesped[] huespedes = new Huesped[num];
                for (int i = 0; i < num; i++) {
                    JPanel panel = new JPanel(new GridLayout(5, 2));
                    JTextField nombresField = new JTextField();
                    JTextField apellidosField = new JTextField();
                    JTextField tipoDocumentoField = new JTextField();
                    JTextField numeroDocumentoField = new JTextField();
                    JTextField paisField = new JTextField();

                    panel.add(new JLabel("Nombres del huésped " + (i + 1) + ":"));
                    panel.add(nombresField);
                    panel.add(new JLabel("Apellidos del huésped " + (i + 1) + ":"));
                    panel.add(apellidosField);
                    panel.add(new JLabel("Tipo de documento del huésped " + (i + 1) + ":"));
                    panel.add(tipoDocumentoField);
                    panel.add(new JLabel("Número de documento del huésped " + (i + 1) + ":"));
                    panel.add(numeroDocumentoField);
                    panel.add(new JLabel("País del huésped " + (i + 1) + ":"));
                    panel.add(paisField);

                    int result = JOptionPane.showConfirmDialog(null, panel, "Ingrese los datos del huésped", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        String nombres = nombresField.getText();
                        String apellidos = apellidosField.getText();
                        String tipoDocumento = tipoDocumentoField.getText();
                        String numeroDocumento = numeroDocumentoField.getText();
                        String pais = paisField.getText();
                        huespedes[i] = new Huesped(nombres, apellidos, tipoDocumento, numeroDocumento, pais);
                    } else {
                        return;
                    }
                }

                int dias = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad de días de la reserva"));
                h.CrearReserva(huespedes, dias);
                JOptionPane.showMessageDialog(null, "Reserva creada con éxito");
                Reservar.setEnabled(false);
                TerminarReserva.setEnabled(true);
            }
        });

        TerminarReserva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                h.Deshabilitar();
                h.Disponible(true);
                JOptionPane.showMessageDialog(null, "Reserva terminada con éxito");
                Reservar.setEnabled(true);
                TerminarReserva.setEnabled(false);
            }
        });
    }
}