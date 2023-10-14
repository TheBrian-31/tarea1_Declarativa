package waypoint;

import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ButtonWaypoint extends JButton {

    public ButtonWaypoint(String lugar) {
        setContentAreaFilled(false);
        if (lugar == "gas") {
            setIcon(new ImageIcon(getClass().getResource("/icon/gas.png")));
        } else if (lugar == "escuela") {
            setIcon(new ImageIcon(getClass().getResource("/icon/escuela.png")));
        }else if (lugar == "salud") {
            setIcon(new ImageIcon(getClass().getResource("/icon/hospital.png")));
        }else if (lugar == "parque") {
            setIcon(new ImageIcon(getClass().getResource("/icon/parque.png")));
        }else if (lugar == "ccomercial") {
            setIcon(new ImageIcon(getClass().getResource("/icon/ccomercial.png")));
        }
        else{
            setIcon(new ImageIcon(getClass().getResource("/icon/pin.png")));
        }

        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setSize(new Dimension(24, 24));
    }
}
