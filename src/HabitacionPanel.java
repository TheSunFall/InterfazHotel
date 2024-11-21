import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class HabitacionPanel extends JPanel{
    private JButton Reservar;
    private JButton TerminarReserva;
    public HabitacionPanel(String s, boolean disp){
        add(new JLabel(s));
        add(Reservar);
        add(TerminarReserva);
        Reservar.setEnabled(disp);
        TerminarReserva.setEnabled(!disp);
        setBorder(new EmptyBorder(10, 10, 10, 10));
    }
}