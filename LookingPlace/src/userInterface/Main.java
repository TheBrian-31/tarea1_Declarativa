package userInterface;

import data.RoutingData;
import data.RoutingService;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;
import waypoint.EventWaypoint;
import waypoint.MyWaypoint;
import waypoint.WaypointRender;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Map;
import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.Variable;
import waypoint.MyWaypoint;
import logic.PlaceInfoExtractor;

public class Main extends javax.swing.JFrame {

    private final Set<MyWaypoint> waypoints = new HashSet<>();
    private List<RoutingData> routingData = new ArrayList<>();
    private List<RoutingData> allRoutingData = new ArrayList<>();
    private EventWaypoint event;
    private Point mousePosition;
    private ArrayList<String> nombreLugares = new ArrayList<>();

    public Main() {
        initComponents();
        init();
        setIconImage(getIconImage()); //coloca el icono de la aplicacion 
        lugares();
        addWaypointImage();
//
//        //for que recorre todo el array
//        for (int i = 0; i < nombreLugares.size(); i++) {
//            System.out.println(nombreLugares.get(i));
//        }

    }

    //icono del jframe
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("src/image/icon.jpeg"));
        return retValue;
    }

    //funcion para colocar los puntos con sus logos
    private void addWaypointImage() {
//        List<GeoPosition> coordinatesList = new ArrayList<>();
        //bucle que coloca las imagenes de los puntos
        for (int i = 0; i < nombreLugares.size(); i++) {

            if (i <= 4) {
                GeoPosition geop = PlaceInfoExtractor.getCoordinates(nombreLugares.get(i));
                MyWaypoint wayPoint = new MyWaypoint(nombreLugares.get(i), MyWaypoint.PointType.COORDINATE, event, new GeoPosition(geop.getLatitude(), geop.getLongitude()), "gas");
                pintar(wayPoint);
            } else if (i <= 9) {
                GeoPosition geop = PlaceInfoExtractor.getCoordinates(nombreLugares.get(i));
                MyWaypoint wayPoint = new MyWaypoint(nombreLugares.get(i), MyWaypoint.PointType.COORDINATE, event, new GeoPosition(geop.getLatitude(), geop.getLongitude()), "escuela");
                pintar(wayPoint);
            } else if (i<=14) {
                GeoPosition geop = PlaceInfoExtractor.getCoordinates(nombreLugares.get(i));
                MyWaypoint wayPoint = new MyWaypoint(nombreLugares.get(i), MyWaypoint.PointType.COORDINATE, event, new GeoPosition(geop.getLatitude(), geop.getLongitude()), "salud");
                pintar(wayPoint);
            }else if (i<=19) {
                GeoPosition geop = PlaceInfoExtractor.getCoordinates(nombreLugares.get(i));
                MyWaypoint wayPoint = new MyWaypoint(nombreLugares.get(i), MyWaypoint.PointType.COORDINATE, event, new GeoPosition(geop.getLatitude(), geop.getLongitude()), "parque");
                pintar(wayPoint);
            }else{
                GeoPosition geop = PlaceInfoExtractor.getCoordinates(nombreLugares.get(i));
                MyWaypoint wayPoint = new MyWaypoint(nombreLugares.get(i), MyWaypoint.PointType.COORDINATE, event, new GeoPosition(geop.getLatitude(), geop.getLongitude()), "ccomercial");
                pintar(wayPoint);
            }

            //GeoPosition geop = PlaceInfoExtractor.getCoordinates(nombreLugares.get(i));
            //MyWaypoint wayPoint = new MyWaypoint(nombreLugares.get(i), MyWaypoint.PointType.COORDINATE, event, new GeoPosition(geop.getLatitude(), geop.getLongitude()), "gas");
            //pintar(wayPoint);
        }
    }

    private void init() {

        TileFactoryInfo info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        jXMapViewer.setTileFactory(tileFactory);
        GeoPosition geo = new GeoPosition(13.67694, -89.27972);
        jXMapViewer.setAddressLocation(geo);
        jXMapViewer.setZoom(4);

        // Create event mouse move
        MouseInputListener mm = new PanMouseInputListener(jXMapViewer);
        jXMapViewer.addMouseListener(mm);
        jXMapViewer.addMouseMotionListener(mm);

        // Agrega el MouseWheelListener personalizado para limitar el zoom máximo a 4
        jXMapViewer.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int notches = e.getWheelRotation();
                int currentZoom = jXMapViewer.getZoom();
                int newZoom = currentZoom - notches;  // Incrementa o disminuye el nivel de zoom

                // Limita el nivel de zoom máximo a 4
                if (newZoom > 4) {
                    newZoom = 4;
                }

                // Actualiza el nivel de zoom del mapa
                jXMapViewer.setZoom(newZoom);
            }
        });

        event = getEvent();

    }

    //funcion que es una copia de la funcion addWaypoint con la modificacion que
    //permite pintar los puntos de los lugares
    private void pintar(MyWaypoint waypoint) {
        for (MyWaypoint d : waypoints) {
            jXMapViewer.remove(d.getButton());
        }
//        Iterator<MyWaypoint> iter = waypoints.iterator();

        waypoints.add(waypoint);
        iniciarPunto();
    }

    private void addWaypoint(MyWaypoint waypoint) {
        for (MyWaypoint d : waypoints) {
            jXMapViewer.remove(d.getButton());
        }
        Iterator<MyWaypoint> iter = waypoints.iterator();
        while (iter.hasNext()) {
            if (iter.next().getPointType() == waypoint.getPointType()) {
                iter.remove();
            }
        }
        waypoints.add(waypoint);
        initWaypoint();
    }

    //copia de la funcion initWaypoint pero que solo se utiliza para pintar los puntos del mapa para el logo
    private void iniciarPunto() {
        WaypointPainter<MyWaypoint> wp = new WaypointRender();
        wp.setWaypoints(waypoints);
        jXMapViewer.setOverlayPainter(wp);
        for (MyWaypoint d : waypoints) {
            jXMapViewer.add(d.getButton());
        }

    }

    private void initWaypoint() {
        WaypointPainter<MyWaypoint> wp = new WaypointRender();
        wp.setWaypoints(waypoints);
        jXMapViewer.setOverlayPainter(wp);
        for (MyWaypoint d : waypoints) {
            jXMapViewer.add(d.getButton());
        }

        if (waypoints.size() == 2) {
            GeoPosition start = null;
            GeoPosition end = null;
            List<GeoPosition> intermediateCoords = new ArrayList<>(); // Nueva lista de coordenadas intermedias

            for (MyWaypoint w : waypoints) {
                if (w.getPointType() == MyWaypoint.PointType.START) {
                    start = w.getPosition();
                    intermediateCoords.add(0, start);
                } else if (w.getPointType() == MyWaypoint.PointType.END) {
                    end = w.getPosition();
                    intermediateCoords.add(end);
                }
            }

            if (start != null && end != null) {
                routingData = RoutingService.getInstance().routing(start.getLatitude(), start.getLongitude(), end.getLatitude(), end.getLongitude());

            } else {
                routingData.clear();
            }
            jXMapViewer.setRoutingData(routingData);
        }
    }

    //Trazar ruta con N puntos
    private void drawRoutes(List<GeoPosition> coordinatesList) {
        allRoutingData.clear();
        // Agregar un pin al primer elemento de la lista
        if (!coordinatesList.isEmpty()) {
            GeoPosition firstCoordinate = coordinatesList.get(0);
            MyWaypoint startWaypoint = new MyWaypoint("Start Location", MyWaypoint.PointType.START, event, firstCoordinate, "");
            addWaypoint(startWaypoint);
        }
        // Itera a través de la lista de coordenadas y realiza el enrutamiento
        for (int i = 0; i < coordinatesList.size() - 1; i++) {
            GeoPosition start = coordinatesList.get(i);
            GeoPosition end = coordinatesList.get(i + 1);

            // Realiza el enrutamiento y obtén la lista de RoutingData
            List<RoutingData> routingData = RoutingService.getInstance().routing(start.getLatitude(), start.getLongitude(), end.getLatitude(), end.getLongitude());

            // Agrega las rutas a la lista general
            allRoutingData.addAll(routingData);
        }
        // Agregar un pin al último elemento de la lista
        if (!coordinatesList.isEmpty()) {
            GeoPosition lastCoordinate = coordinatesList.get(coordinatesList.size() - 1);
            MyWaypoint endWaypoint = new MyWaypoint("End Location", MyWaypoint.PointType.END, event, lastCoordinate, "");
            addWaypoint(endWaypoint);
        }
        // Actualiza el mapa con todas las rutas al mismo tiempo
        jXMapViewer.setRoutingData(allRoutingData);
    }

    private void clearWaypoint() {
        for (MyWaypoint d : waypoints) {
            jXMapViewer.remove(d.getButton());
        }
        allRoutingData.clear();
        routingData.clear();
        waypoints.clear();
        initWaypoint();
    }

    private EventWaypoint getEvent() {
        return new EventWaypoint() {
            @Override
            public void selected(MyWaypoint waypoint) {
                JOptionPane.showMessageDialog(Main.this, waypoint.getName());
            }
        };
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        menuStart = new javax.swing.JMenuItem();
        menuEnd = new javax.swing.JMenuItem();
        jXMapViewer = new data.JXMapViewerCustom();
        cmdAdd = new javax.swing.JButton();
        cmdClear = new javax.swing.JButton();
        lugaresInicio = new javax.swing.JComboBox<>();
        lugaresFin = new javax.swing.JComboBox<>();
        drawLineButtonActionPerformed = new javax.swing.JButton();

        menuStart.setText("Start");
        menuStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuStartActionPerformed(evt);
            }
        });
        jPopupMenu1.add(menuStart);

        menuEnd.setText("End");
        menuEnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEndActionPerformed(evt);
            }
        });
        jPopupMenu1.add(menuEnd);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Looking Place");
        setResizable(false);

        jXMapViewer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jXMapViewerMouseReleased(evt);
            }
        });

        cmdAdd.setText("Add Waypoint");
        cmdAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdAddActionPerformed(evt);
            }
        });

        cmdClear.setText("Clear Waypoint");
        cmdClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdClearActionPerformed(evt);
            }
        });

        lugaresInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lugaresInicioActionPerformed(evt);
            }
        });

        lugaresFin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lugaresFinActionPerformed(evt);
            }
        });

        drawLineButtonActionPerformed.setText("Ir");
        drawLineButtonActionPerformed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                drawLineButtonActionPerformedActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jXMapViewerLayout = new javax.swing.GroupLayout(jXMapViewer);
        jXMapViewer.setLayout(jXMapViewerLayout);
        jXMapViewerLayout.setHorizontalGroup(
            jXMapViewerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXMapViewerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmdAdd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdClear)
                .addGap(18, 18, 18)
                .addComponent(lugaresInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(lugaresFin, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(drawLineButtonActionPerformed)
                .addContainerGap(443, Short.MAX_VALUE))
        );
        jXMapViewerLayout.setVerticalGroup(
            jXMapViewerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXMapViewerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXMapViewerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdAdd)
                    .addComponent(cmdClear)
                    .addComponent(lugaresInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lugaresFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(drawLineButtonActionPerformed))
                .addContainerGap(624, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXMapViewer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXMapViewer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cmdAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdAddActionPerformed

    }//GEN-LAST:event_cmdAddActionPerformed

    private void cmdClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdClearActionPerformed
        clearWaypoint();
    }//GEN-LAST:event_cmdClearActionPerformed

    private void menuStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuStartActionPerformed
        GeoPosition geop = jXMapViewer.convertPointToGeoPosition(mousePosition);
        MyWaypoint wayPoint = new MyWaypoint("Start Location", MyWaypoint.PointType.START, event, new GeoPosition(geop.getLatitude(), geop.getLongitude()), "");
        addWaypoint(wayPoint);
    }//GEN-LAST:event_menuStartActionPerformed

    private void menuEndActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEndActionPerformed
        GeoPosition geop = jXMapViewer.convertPointToGeoPosition(mousePosition);
        MyWaypoint wayPoint = new MyWaypoint("End Location", MyWaypoint.PointType.END, event, new GeoPosition(geop.getLatitude(), geop.getLongitude()), "");
        addWaypoint(wayPoint);
    }//GEN-LAST:event_menuEndActionPerformed

    private void jXMapViewerMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jXMapViewerMouseReleased
        if (SwingUtilities.isRightMouseButton(evt)) {
            mousePosition = evt.getPoint();
            jPopupMenu1.show(jXMapViewer, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jXMapViewerMouseReleased

    private void drawLineButtonActionPerformedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_drawLineButtonActionPerformedActionPerformed
        List<GeoPosition> coordinatesList = new ArrayList<>();
        // Obtener el nombre del lugar seleccionado en lugaresInicio
        String lugarInicio = lugaresInicio.getSelectedItem().toString();
        // Obtener el nombre del lugar seleccionado en lugaresFin
        String lugarFin = lugaresFin.getSelectedItem().toString();
        // Obtener las coordenadas para los lugares seleccionados
        GeoPosition init = PlaceInfoExtractor.getCoordinates(lugarInicio);
        GeoPosition fin = PlaceInfoExtractor.getCoordinates(lugarFin);

        //Coordenada de inicio
        coordinatesList.add(init);
        ////Aquí se deben agregar las rutas intermedias

        coordinatesList.add(new GeoPosition(13.672963495146654, -89.28239868905219));
        coordinatesList.add(new GeoPosition(13.669643929782355, -89.28256807592008));
        coordinatesList.add(new GeoPosition(13.669765876693948, -89.28591543977916));

        //Coordenada de fin
        coordinatesList.add(fin);

        // Llama a la función para trazar las rutas con la lista de coordenadas
        drawRoutes(coordinatesList);
    }//GEN-LAST:event_drawLineButtonActionPerformedActionPerformed

    private void lugaresInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lugaresInicioActionPerformed
        String lugarInicio = lugaresInicio.getSelectedItem().toString();
    }//GEN-LAST:event_lugaresInicioActionPerformed

    private void lugaresFinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lugaresFinActionPerformed
        String lugarFin = lugaresFin.getSelectedItem().toString();
    }//GEN-LAST:event_lugaresFinActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    //funcion que llena los comboBox con los lugares traidos desde Prolog
    private void lugares() {
//        lugaresInicio.addItem("X");
        Variable X = new Variable("X");
        Query q1
                = new Query(
                        "obtener_nombre_lugar",
                        new Term[]{X}
                );

        Map<String, Term>[] solutions = q1.allSolutions();
        for (int i = 0; i < solutions.length; i++) {
//            System.out.println("X = " + solutions[i].get("X").toString()); 
            lugaresInicio.addItem(solutions[i].get("X").toString());
            lugaresFin.addItem(solutions[i].get("X").toString());
            nombreLugares.add(solutions[i].get("X").toString());

        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdAdd;
    private javax.swing.JButton cmdClear;
    private javax.swing.JButton drawLineButtonActionPerformed;
    private javax.swing.JPopupMenu jPopupMenu1;
    private data.JXMapViewerCustom jXMapViewer;
    private javax.swing.JComboBox<String> lugaresFin;
    private javax.swing.JComboBox<String> lugaresInicio;
    private javax.swing.JMenuItem menuEnd;
    private javax.swing.JMenuItem menuStart;
    // End of variables declaration//GEN-END:variables
}
