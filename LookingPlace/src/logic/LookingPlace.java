package logic;

import java.util.logging.Level;
import java.util.logging.Logger;
import userInterface.ChargingScreen;
import userInterface.Home;
//import userInterface.Home;
//import userInterface.Home;

public class LookingPlace {

    public static void main(String[] args) {
        Runnable mRun = () -> {
            ChargingScreen screen = new ChargingScreen();
            screen.setVisible(true);
            screen.setLocationRelativeTo(null);
            
            try {
                //tiempo que se mostrara la pantalla de carga
                Thread.sleep(5000); //5 segundos
            } catch (InterruptedException ex) {
                Logger.getLogger(LookingPlace.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //cierro la pantalla de carga
            screen.dispose();
            
            //mando a llamar la nueva ventana
            Home home = new Home();
            home.setVisible(true);
            
        };
        
        //mando a llamar el hilo de carga de las pantallas
        Thread mHiloCharging = new Thread(mRun);
        mHiloCharging.start();
        
        

    }

}
