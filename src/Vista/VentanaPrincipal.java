package Vista;

import Servicio.ServicioOdontologo;
import Servicio.ServicioPaciente;
import Servicio.ServicioTurno;
import Persistencia.Persistencia;
import Repositorio.RepositorioOdontologo;
import Repositorio.RepositorioPaciente;
import Repositorio.RepositorioTurno;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VentanaPrincipal extends JFrame {
    private final ServicioPaciente servicioPaciente;
    private final ServicioOdontologo servicioOdontologo;
    private final ServicioTurno servicioTurno;
    private final RepositorioPaciente repoPaciente;
    private final RepositorioOdontologo repoOdontologo;
    private final RepositorioTurno repoTurno;

    private final JPanel panelCentral;

    public VentanaPrincipal(ServicioPaciente sp, ServicioOdontologo so, ServicioTurno st,
                            RepositorioPaciente rp, RepositorioOdontologo ro, RepositorioTurno rt) {
        this.servicioPaciente = sp;
        this.servicioOdontologo = so;
        this.servicioTurno = st;
        this.repoPaciente = rp;
        this.repoOdontologo = ro;
        this.repoTurno = rt;

        setTitle("Clinica Odontologica - Sonrisa Feliz");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());

        // barra de navegacion arriba
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT));
        barra.setBackground(new Color(70, 130, 180));
        JButton btnPacientes = new JButton("Pacientes");
        JButton btnOdontologos = new JButton("Odontologos");
        JButton btnTurnos = new JButton("Turnos");
        JButton btnBusquedas = new JButton("Busquedas");
        barra.add(btnPacientes);
        barra.add(btnOdontologos);
        barra.add(btnTurnos);
        barra.add(btnBusquedas);
        add(barra, BorderLayout.NORTH);

        // panel central que va cambiando
        panelCentral = new JPanel(new BorderLayout());
        panelCentral.add(new JLabel("Bienvenido. Elegi una opcion de la barra superior.",
                SwingConstants.CENTER), BorderLayout.CENTER);
        add(panelCentral, BorderLayout.CENTER);

        // listeners de navegacion
        btnPacientes.addActionListener(e -> cambiarPanel(new PanelPacientes(servicioPaciente)));
        btnOdontologos.addActionListener(e -> cambiarPanel(new PanelOdontologos(servicioOdontologo)));
        btnTurnos.addActionListener(e -> cambiarPanel(
                new PanelTurnos(servicioTurno, servicioPaciente, servicioOdontologo)));
        btnBusquedas.addActionListener(e -> cambiarPanel(
                new PanelBusquedas(servicioPaciente, servicioTurno)));

        // guardar al cerrar
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int opcion = JOptionPane.showConfirmDialog(VentanaPrincipal.this,
                        "Seguro que queres salir? Los datos se guardaran automaticamente.",
                        "Confirmar salida",
                        JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    guardarDatos();
                    System.exit(0);
                }
            }
        });
    }

    private void cambiarPanel(JPanel nuevo) {
        panelCentral.removeAll();
        panelCentral.add(nuevo, BorderLayout.CENTER);
        panelCentral.revalidate();
        panelCentral.repaint();
    }

    private void guardarDatos() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                Persistencia.guardarPacientes(repoPaciente.listar());
                Persistencia.guardarOdontologos(repoOdontologo.listar());
                Persistencia.guardarTurnos(repoTurno.listar());
                return null;
            }
        };
        worker.execute();
        try {
            worker.get();
        } catch (Exception e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }
}