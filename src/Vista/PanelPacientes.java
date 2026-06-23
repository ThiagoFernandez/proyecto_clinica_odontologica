package Vista;

import Modelo.Domicilio;
import Modelo.Paciente;
import Servicio.ServicioPaciente;
import Exception.ClinicaException;
import Vista.modelos.TablaPacientesModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

public class PanelPacientes extends JPanel {
    private final ServicioPaciente servicio;
    private TablaPacientesModel modelo;
    private JTable tabla;
    private JTextField txtNombre, txtApellido, txtDni, txtEmail;
    private JTextField txtCalle, txtNumero, txtLocalidad, txtProvincia;
    private Long idSeleccionado = null;

    public PanelPacientes(ServicioPaciente servicio) {
        this.servicio = servicio;
        setLayout(new BorderLayout());

        // titulo arriba
        JLabel titulo = new JLabel("Gestion de Pacientes", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titulo, BorderLayout.NORTH);

        // tabla en el centro
        modelo = new TablaPacientesModel(servicio.listarPacientes());
        tabla = new JTable(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // panel inferior con formulario y botones
        JPanel panelInferior = new JPanel(new BorderLayout());

        // formulario
        JPanel formulario = new JPanel(new GridLayout(0, 4, 5, 5));
        formulario.setBorder(BorderFactory.createTitledBorder("Datos del paciente"));

        formulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        formulario.add(txtNombre);
        formulario.add(new JLabel("Apellido:"));
        txtApellido = new JTextField();
        formulario.add(txtApellido);

        formulario.add(new JLabel("DNI:"));
        txtDni = new JTextField();
        formulario.add(txtDni);
        formulario.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        formulario.add(txtEmail);

        formulario.add(new JLabel("Calle:"));
        txtCalle = new JTextField();
        formulario.add(txtCalle);
        formulario.add(new JLabel("Numero:"));
        txtNumero = new JTextField();
        formulario.add(txtNumero);

        formulario.add(new JLabel("Localidad:"));
        txtLocalidad = new JTextField();
        formulario.add(txtLocalidad);
        formulario.add(new JLabel("Provincia:"));
        txtProvincia = new JTextField();
        formulario.add(txtProvincia);

        panelInferior.add(formulario, BorderLayout.CENTER);

        // botones
        JPanel botones = new JPanel(new FlowLayout());
        JButton btnNuevo = new JButton("Nuevo");
        JButton btnGuardar = new JButton("Guardar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");
        botones.add(btnNuevo);
        botones.add(btnGuardar);
        botones.add(btnEliminar);
        botones.add(btnLimpiar);
        panelInferior.add(botones, BorderLayout.SOUTH);

        add(panelInferior, BorderLayout.SOUTH);

        // listeners
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                if (fila != -1) cargarEnFormulario(modelo.getPacienteEn(fila));
            }
        });

        btnNuevo.addActionListener(e -> { idSeleccionado = null; limpiarFormulario(); });
        btnGuardar.addActionListener(e -> guardar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
    }

    private void cargarEnFormulario(Paciente p) {
        idSeleccionado = p.getId();
        txtNombre.setText(p.getNombre());
        txtApellido.setText(p.getApellido());
        txtDni.setText(p.getDni());
        txtEmail.setText(p.getEmail());
        if (p.getDomicilio() != null) {
            txtCalle.setText(p.getDomicilio().getCalle());
            txtNumero.setText(String.valueOf(p.getDomicilio().getNumero()));
            txtLocalidad.setText(p.getDomicilio().getLocalidad());
            txtProvincia.setText(p.getDomicilio().getProvincia());
        }
    }

    private void limpiarFormulario() {
        idSeleccionado = null;
        txtNombre.setText(""); txtApellido.setText("");
        txtDni.setText(""); txtEmail.setText("");
        txtCalle.setText(""); txtNumero.setText("");
        txtLocalidad.setText(""); txtProvincia.setText("");
        tabla.clearSelection();
    }

    private void guardar() {
        try {
            if (txtNombre.getText().isBlank() || txtApellido.getText().isBlank()
                    || txtDni.getText().isBlank() || txtEmail.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "Completa los campos obligatorios.",
                        "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int numero;
            try {
                numero = Integer.parseInt(txtNumero.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El numero debe ser un valor numerico.",
                        "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Domicilio domicilio = new Domicilio(txtCalle.getText(), numero,
                    txtLocalidad.getText(), txtProvincia.getText());
            Paciente paciente = new Paciente(txtNombre.getText(), txtApellido.getText(),
                    txtDni.getText(), txtEmail.getText(), LocalDate.now(), domicilio);

            if (idSeleccionado != null) {
                paciente.setId(idSeleccionado);
                servicio.modificarPaciente(paciente);
                JOptionPane.showMessageDialog(this, "Paciente modificado correctamente.");
            } else {
                servicio.registrarPaciente(paciente);
                JOptionPane.showMessageDialog(this, "Paciente registrado correctamente.");
            }

            modelo.actualizar(servicio.listarPacientes());
            limpiarFormulario();
        } catch (ClinicaException ex) {
            JOptionPane.showMessageDialog(this, "[" + ex.getCodigo() + "] " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminar() {
        if (idSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un paciente de la tabla.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int opcion = JOptionPane.showConfirmDialog(this,
                "Confirmar eliminacion del paciente?", "Confirmar",
                JOptionPane.YES_NO_OPTION);
        if (opcion != JOptionPane.YES_OPTION) return;

        try {
            servicio.eliminarPaciente(idSeleccionado);
            modelo.actualizar(servicio.listarPacientes());
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Paciente eliminado.");
        } catch (ClinicaException ex) {
            JOptionPane.showMessageDialog(this, "[" + ex.getCodigo() + "] " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}