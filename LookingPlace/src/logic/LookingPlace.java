package logic;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jpl7.*;
import userInterface.ChargingScreen;
import userInterface.Main;
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
            Main main = new Main();
            main.setVisible(true);

        };

        //mando a llamar el hilo de carga de las pantallas
        Thread mHiloCharging = new Thread(mRun);
        mHiloCharging.start();

        // Prueba de conecion a la base de conocimiento
        Query q1
                = new Query(
                        "consult",
                        new Term[]{new Atom("..\\LookingPlace\\src\\src\\bc\\LookingPlace.pl")}
                );

        System.out.println("consult " + (q1.hasSolution() ? "succeeded" : "failed"));

        //prueba de consulta de contenido a la base de conocimiento
        Variable X = new Variable("X");
        Query q4
                = new Query(
                        "descendent_of",
                        new Term[]{X, new Atom("joe")}
                );

        Map<String, Term>[] solutions = q4.allSolutions();
        for (int i = 0; i < solutions.length; i++) {
            System.out.println("X = " + solutions[i].get("X")); 
        }
    }

}
