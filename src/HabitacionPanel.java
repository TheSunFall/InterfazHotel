import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class HabitacionPanel extends JPanel {
    private JButton Reservar;
    private JButton TerminarReserva;
    private JButton GenerarBoleta;
    private JButton ExtenderReserva;
    private JLabel Detalles;
    private final Habitacion habitacion;
    private HotelChangeListener changeListener;

    public HabitacionPanel(Habitacion h) {
        this.habitacion = h; // Guardamos la referencia a la habitación
        Detalles.setText(h.Detalles());  // Mostrar detalles de la habitación

        add(Detalles);  // Mostrar detalles de la habitación
        add(Reservar);
        add(TerminarReserva);
        add(GenerarBoleta);
        add(ExtenderReserva);

        // Inicialmente, los botones tienen el siguiente estado:
        setBorder(new EmptyBorder(10, 10, 10, 10));
        actualizarBotones();  // Llamamos a la función para actualizar los botones según el estado de la habitación

        // Acción de reservar
        Reservar.addActionListener(e -> {
            // Lógica de reserva
            reservarHabitacion(habitacion);
        });

        // Acción de terminar reserva
        TerminarReserva.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(null, "Terminando " + h.getReserva().Detalles(), "Confirmar terminación", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                habitacion.Deshabilitar(); // Deshabilitar la habitación
                JOptionPane.showMessageDialog(null, "Reserva terminada con éxito");
                actualizarBotones(); // Actualizamos el estado de los botones después de terminar la reserva
                if (changeListener != null) {
                    changeListener.onHotelChanged(); // Notifica el cambio
                }
            }
        });

        // Acción de generar boleta
        GenerarBoleta.addActionListener(e -> generarBoleta(habitacion));

        // Acción de extender reserva
        ExtenderReserva.addActionListener(e -> {
            if (!habitacion.Disponible()) {
                // Si la habitación tiene una reserva activa, extender la reserva
                JPanel panelDias = new JPanel(new GridLayout(3, 1));
                panelDias.add(new JLabel(h.getReserva().Detalles()));
                panelDias.add(new JLabel("Ingrese la cantidad de días a extender:"));
                JSpinner diasSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 365, 1));
                panelDias.add(diasSpinner);

                int confirmarDias = JOptionPane.showConfirmDialog(null, panelDias, "Extender reserva", JOptionPane.OK_CANCEL_OPTION);
                if (confirmarDias == JOptionPane.OK_OPTION) {
                    int dias = (int) diasSpinner.getValue();
                    habitacion.getReserva().Extender(dias); // Extender la reserva
                    JOptionPane.showMessageDialog(null, "Reserva extendida con éxito");
                } else {
                    JOptionPane.showMessageDialog(null, "Extensión cancelada", "Cancelación", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Esta habitación no tiene una reserva activa", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

    }

    // Método que actualiza los botones según el estado de la habitación
    private void actualizarBotones() {
        Reservar.setEnabled(habitacion.Disponible());
        TerminarReserva.setEnabled(!habitacion.Disponible());
        GenerarBoleta.setEnabled(!habitacion.Disponible());
        ExtenderReserva.setEnabled(!habitacion.Disponible());
        if (changeListener != null) {
            changeListener.onHotelChanged(); // Notifica el cambio
        }
    }

    private void reservarHabitacion(Habitacion h) {
        int num;
        JPanel panelNum = new JPanel(new GridLayout(1, 2));
        panelNum.add(new JLabel("Ingrese el número de huéspedes:"));
        JSpinner numSpinner = new JSpinner(new SpinnerNumberModel(1, 1, h.getMaxHuespedes(), 1));
        panelNum.add(numSpinner);

        int confirmarNum = JOptionPane.showConfirmDialog(null, panelNum, "Número de huéspedes", JOptionPane.OK_CANCEL_OPTION);
        if (confirmarNum == JOptionPane.OK_OPTION) {
            num = (int) numSpinner.getValue();
        } else {
            JOptionPane.showMessageDialog(null, "Reserva cancelada", "Reserva cancelada", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Huesped[] huespedes = new Huesped[num];
        for (int i = 0; i < num; i++) {
            JPanel panel = new JPanel(new GridLayout(5, 2));
            JTextField nombresField = new JTextField();
            JTextField apellidosField = new JTextField();
            JTextField dniField = new JTextField();
            JTextField paisField = new JTextField();

            panel.add(new JLabel("Nombres del huésped " + (i + 1) + ":"));
            panel.add(nombresField);
            panel.add(new JLabel("Apellidos del huésped " + (i + 1) + ":"));
            panel.add(apellidosField);
            panel.add(new JLabel("DNI del huésped " + (i + 1) + ":"));
            panel.add(dniField);
            panel.add(new JLabel("País del huésped " + (i + 1) + ":"));
            panel.add(paisField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Datos del huésped", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String nombres = nombresField.getText();
                String apellidos = apellidosField.getText();
                String dni = dniField.getText();
                String pais = paisField.getText();
                huespedes[i] = new Huesped(nombres, apellidos, "DNI", dni, pais);
            } else {
                JOptionPane.showMessageDialog(null, "Reserva cancelada", "Reserva cancelada", JOptionPane.WARNING_MESSAGE);
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
            actualizarBotones(); // Actualizamos los botones después de realizar la reserva
            if (changeListener != null) {
                changeListener.onHotelChanged(); // Notifica el cambio
            }
        } else {
            JOptionPane.showMessageDialog(null, "Reserva cancelada", "Reserva cancelada", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void generarBoleta(Habitacion h) {
        Reserva reserva = h.getReserva();
        if (reserva == null) {
            JOptionPane.showMessageDialog(null, "No hay reserva activa para esta habitación", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("boleta_habitacion_" + h.getCodigo() + ".txt"))) {
            writer.write("====================================\n");
            writer.write("BOLETA ELECTRÓNICA\n");
            writer.write("====================================\n");
            writer.write("Habitación: " + h.getCodigo() + "\n");
            writer.write("Total a pagar: " + h.getPrecio() * reserva.getDias() + "\n");
            writer.write("====================================\n");
            writer.write("DATOS:\n");
            for (Huesped huesped : reserva.getHuespedes()) {
                writer.write("Nombres: " + huesped.getNombres() + "\n");
                writer.write("Apellidos: " + huesped.getApellidos() + "\n");
                writer.write("DNI: " + huesped.getNumeroDocumento() + "\n");
            }
            writer.write("Fecha de inicio: " + reserva.getFechaInicio() + "\n");
            writer.write("Cantidad de días: " + reserva.getDias() + "\n");
            writer.write("Fecha de salida: " + reserva.getFechaFin() + "\n");
            writer.write("====================================\n");
            JOptionPane.showMessageDialog(null, "Boleta generada con éxito");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al generar la boleta", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
