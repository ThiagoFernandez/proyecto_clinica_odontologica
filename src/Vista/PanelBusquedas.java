package Vista;

import Modelo.Paciente;
import Modelo.Turno;
import Servicio.ServicioPaciente;
import Servicio.ServicioTurno;
import Exception.ClinicaException;
import Vista.modelos.TablaPacientesModel;
import Vista.modelos.TablaTurnosModel;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class PanelBusquedas extends JPanel {
    private final ServicioPaciente servicioPaciente;
    private final ServicioTurno servicioTurno;

    public PanelBusquedas(ServicioPaciente sp, ServicioTurno st) {
        this.servicioPaciente = sp;
        this.servicioTurno = st;

        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Busquedas", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titulo, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Paciente por DNI", construirBusquedaPorDni());
        tabs.addTab("Turnos por rango de fechas", construirBusquedaPorRangoFechas());
        add(tabs, BorderLayout.CENTER);
    }

    private JPanel construirBusquedaPorDni() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel arriba = new JPanel(new FlowLayout(FlowLayout.LEFT));
        arriba.add(new JLabel("DNI:"));
        JTextField txtDni = new JTextField(15);
        arriba.add(txtDni);
        JButton btnBuscar = new JButton("Buscar");
        arriba.add(btnBuscar);
        panel.add(arriba, BorderLayout.NORTH);

        JTextArea resultado = new JTextArea();
        resultado.setEditable(false);
        resultado.setFont(new Font("Monospaced", Font.PLAIN, 13));
        panel.add(new JScrollPane(resultado), BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> {
            String dni = txtDni.getText().trim();
            if (dni.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingresa un DNI.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                Paciente p = servicioPaciente.buscarPorDni(dni);
                resultado.setText(p.toString());
            } catch (ClinicaException ex) {
                resultado.setText("");
                JOptionPane.showMessageDialog(this,
                        "[" + ex.getCodigo() + "] " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    private JPanel construirBusquedaPorRangoFechas() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel arriba = new JPanel(new FlowLayout(FlowLayout.LEFT));
        arriba.add(new JLabel("Desde (YYYY-MM-DD):"));
        JTextField txtDesde = new JTextField(10);
        arriba.add(txtDesde);
        arriba.add(new JLabel("Hasta (YYYY-MM-DD):"));
        JTextField txtHasta = new JTextField(10);
        arriba.add(txtHasta);
        JButton btnBuscar = new JButton("Buscar");
        arriba.add(btnBuscar);
        panel.add(arriba, BorderLayout.NORTH);

        TablaTurnosModel modelo = new TablaTurnosModel(java.util.Collections.emptyList());
        JTable tabla = new JTable(modelo);
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> {
            try {
                LocalDate desde = LocalDate.parse(txtDesde.getText().trim());
                LocalDate hasta = LocalDate.parse(txtHasta.getText().trim());
                if (desde.isAfter(hasta)) {
                    JOptionPane.showMessageDialog(this,
                            "La fecha desde debe ser anterior a la fecha hasta.",
                            "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                List<Turno> turnos = servicioTurno.listarPorRangoFechas(desde, hasta);
                modelo.actualizar(turnos);
                if (turnos.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "No hay turnos en ese rango de fechas.");
                }
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this,
                        "Formato de fecha invalido. Usa YYYY-MM-DD.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }
}