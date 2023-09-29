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
        }else{
            setIcon(new ImageIcon(getClass().getResource("/icon/pin.png")));
        }
        
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setSize(new Dimension(24, 24));
    }
}
