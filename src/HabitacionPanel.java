import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

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
        Reservar.addActionListener(e -> {
            int num = 0;
            JPanel panelNum = new JPanel(new GridLayout(1, 2));
            panelNum.add(new JLabel("Ingrese el número de huéspedes:"));
            JSpinner numSpinner = new JSpinner(new SpinnerNumberModel(1, 1, h.getMaxHuespedes(), 1));
            panelNum.add(numSpinner);

            int confirmarNum = JOptionPane.showConfirmDialog(null, panelNum, "Días de reserva", JOptionPane.OK_CANCEL_OPTION);
            if (confirmarNum == JOptionPane.OK_OPTION) {
                num = (int) numSpinner.getValue();
            } else {
                JOptionPane.showMessageDialog(null, "Reserva cancelada","Reserva cancelada",JOptionPane.WARNING_MESSAGE);
                return;
            }

            Huesped[] huespedes = new Huesped[num];
            for (int i = 0; i < num; i++) {
                JPanel panel = new JPanel(new GridLayout(5, 2));
                JTextField nombresField = new JTextField();
                JTextField apellidosField = new JTextField();
                JTextField tipoDocumentoField = new JTextField();
                JComboBox<String> numeroDocumentoField = new JComboBox<>();
                numeroDocumentoField.addItem("DNI");
                numeroDocumentoField.addItem("Carnet de extranjería");
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
                    String numeroDocumento = (String) numeroDocumentoField.getSelectedItem();
                    String pais = paisField.getText();
                    huespedes[i] = new Huesped(nombres, apellidos, tipoDocumento, numeroDocumento, pais);
                } else {
                    JOptionPane.showMessageDialog(null, "Reserva cancelada","Reserva cancelada",JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            JPanel panelDias = new JPanel(new GridLayout(1, 2));
            panelDias.add(new JLabel("Ingrese la cantidad de días de la reserva:"));
            JSpinner diasSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 365, 1));
            panelDias.add(diasSpinner);

            int confirmarDias = JOptionPane.showConfirmDialog(null, panelDias, "Días de reserva", JOptionPane.OK_CANCEL_OPTION);
            if (confirmarDias == JOptionPane.OK_OPTION) {
                int dias = (int) diasSpinner.getValue();
                h.CrearReserva(huespedes, dias);
                JOptionPane.showMessageDialog(null, "Reserva creada con éxito");
                Reservar.setEnabled(false);
                TerminarReserva.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(null, "Reserva cancelada","Reserva cancelada",JOptionPane.WARNING_MESSAGE);
            }
        });

        TerminarReserva.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea terminar la reserva?", "Confirmar terminación", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                h.Deshabilitar();
                JOptionPane.showMessageDialog(null, "Reserva terminada con éxito");
                Reservar.setEnabled(true);
                TerminarReserva.setEnabled(false);
            }
        });
    }
}