package Vista;

import Modelo.EstadoTurno;
import Modelo.Odontologo;
import Modelo.Paciente;
import Modelo.Turno;
import Servicio.ServicioOdontologo;
import Servicio.ServicioPaciente;
import Servicio.ServicioTurno;
import Exception.ClinicaException;
import Vista.modelos.TablaTurnosModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class PanelTurnos extends JPanel {
    private final ServicioTurno servicioTurno;
    private final ServicioPaciente servicioPaciente;
    private final ServicioOdontologo servicioOdontologo;

    private TablaTurnosModel modelo;
    private JTable tabla;
    private JComboBox<Paciente> cmbPaciente;
    private JComboBox<Odontologo> cmbOdontologo;
    private JTextField txtFecha, txtHora;
    private Long idSeleccionado = null;

    public PanelTurnos(ServicioTurno st, ServicioPaciente sp, ServicioOdontologo so) {
        this.servicioTurno = st;
        this.servicioPaciente = sp;
        this.servicioOdontologo = so;

        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Gestion de Turnos", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titulo, BorderLayout.NORTH);

        modelo = new TablaTurnosModel(servicioTurno.listarTurnos());
        tabla = new JTable(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new BorderLayout());
        JPanel formulario = new JPanel(new GridLayout(0, 4, 5, 5));
        formulario.setBorder(BorderFactory.createTitledBorder("Datos del turno"));

        formulario.add(new JLabel("Paciente:"));
        cmbPaciente = new JComboBox<>();
        cargarPacientes();
        formulario.add(cmbPaciente);

        formulario.add(new JLabel("Odontologo:"));
        cmbOdontologo = new JComboBox<>();
        cargarOdontologos();
        formulario.add(cmbOdontologo);

        formulario.add(new JLabel("Fecha (YYYY-MM-DD):"));
        txtFecha = new JTextField();
        formulario.add(txtFecha);
        formulario.add(new JLabel("Hora (HH:MM):"));
        txtHora = new JTextField();
        formulario.add(txtHora);

        panelInferior.add(formulario, BorderLayout.CENTER);

        JPanel botones = new JPanel(new FlowLayout());
        JButton btnAgendar = new JButton("Agendar");
        JButton btnConfirmar = new JButton("Confirmar");
        JButton btnCompletar = new JButton("Completar");
        JButton btnCancelar = new JButton("Cancelar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");
        botones.add(btnAgendar); botones.add(btnConfirmar); botones.add(btnCompletar);
        botones.add(btnCancelar); botones.add(btnEliminar); botones.add(btnLimpiar);
        panelInferior.add(botones, BorderLayout.SOUTH);

        add(panelInferior, BorderLayout.SOUTH);

        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                if (fila != -1) {
                    Turno t = modelo.getTurnoEn(fila);
                    idSeleccionado = t.getId();
                    cmbPaciente.setSelectedItem(t.getPaciente());
                    cmbOdontologo.setSelectedItem(t.getOdontologo());
                    txtFecha.setText(t.getFecha().toString());
                    txtHora.setText(t.getHora().toString());
                }
            }
        });

        btnAgendar.addActionListener(e -> agendar());
        btnConfirmar.addActionListener(e -> cambiarEstado("confirmar"));
        btnCompletar.addActionListener(e -> cambiarEstado("completar"));
        btnCancelar.addActionListener(e -> cambiarEstado("cancelar"));
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiar());
    }

    private void cargarPacientes() {
        cmbPaciente.removeAllItems();
        servicioPaciente.listarPacientes().forEach(cmbPaciente::addItem);
    }

    private void cargarOdontologos() {
        cmbOdontologo.removeAllItems();
        servicioOdontologo.listarOdontologos().forEach(cmbOdontologo::addItem);
    }

    private void limpiar() {
        idSeleccionado = null;
        txtFecha.setText(""); txtHora.setText("");
        cargarPacientes(); cargarOdontologos();
        tabla.clearSelection();
    }

    private void agendar() {
        try {
            Paciente p = (Paciente) cmbPaciente.getSelectedItem();
            Odontologo o = (Odontologo) cmbOdontologo.getSelectedItem();
            if (p == null || o == null) {
                JOptionPane.showMessageDialog(this, "Selecciona paciente y odontologo.",
                        "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            LocalDate fecha = LocalDate.parse(txtFecha.getText().trim());
            LocalTime hora = LocalTime.parse(txtHora.getText().trim());

            servicioTurno.agendarTurno(p, o, fecha, hora);
            JOptionPane.showMessageDialog(this,
                    "Turno agendado correctamente.\nDuracion estimada: "
                            + o.calcularDuracionTurno() + " minutos.");

            modelo.actualizar(servicioTurno.listarTurnos());
            limpiar();
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha u hora invalido.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClinicaException ex) {
            JOptionPane.showMessageDialog(this, "[" + ex.getCodigo() + "] " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cambiarEstado(String accion) {
        if (idSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un turno de la tabla.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            switch (accion) {
                case "confirmar" -> servicioTurno.confirmarTurno(idSeleccionado);
                case "completar" -> servicioTurno.completarTurno(idSeleccionado);
                case "cancelar" -> servicioTurno.cancelarTurno(idSeleccionado);
            }
            modelo.actualizar(servicioTurno.listarTurnos());
            JOptionPane.showMessageDialog(this, "Operacion realizada.");
        } catch (ClinicaException ex) {
            JOptionPane.showMessageDialog(this, "[" + ex.getCodigo() + "] " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminar() {
        if (idSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un turno de la tabla.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int opcion = JOptionPane.showConfirmDialog(this,
                "Confirmar eliminacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcion != JOptionPane.YES_OPTION) return;

        try {
            servicioTurno.eliminarTurno(idSeleccionado);
            modelo.actualizar(servicioTurno.listarTurnos());
            limpiar();
            JOptionPane.showMessageDialog(this, "Turno eliminado.");
        } catch (ClinicaException ex) {
            JOptionPane.showMessageDialog(this, "[" + ex.getCodigo() + "] " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}