package Vista;

import Modelo.*;
import Servicio.ServicioOdontologo;
import Exception.ClinicaException;
import Vista.modelos.TablaOdontologosModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelOdontologos extends JPanel {
    private final ServicioOdontologo servicio;
    private TablaOdontologosModel modelo;
    private JTable tabla;
    private JTextField txtNombre, txtApellido, txtMatricula;
    private JComboBox<String> cmbEspecialidad;
    private Long idSeleccionado = null;

    public PanelOdontologos(ServicioOdontologo servicio) {
        this.servicio = servicio;
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Gestion de Odontologos", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titulo, BorderLayout.NORTH);

        modelo = new TablaOdontologosModel(servicio.listarOdontologos());
        tabla = new JTable(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new BorderLayout());
        JPanel formulario = new JPanel(new GridLayout(0, 4, 5, 5));
        formulario.setBorder(BorderFactory.createTitledBorder("Datos del odontologo"));

        formulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        formulario.add(txtNombre);
        formulario.add(new JLabel("Apellido:"));
        txtApellido = new JTextField();
        formulario.add(txtApellido);

        formulario.add(new JLabel("Matricula:"));
        txtMatricula = new JTextField();
        formulario.add(txtMatricula);
        formulario.add(new JLabel("Especialidad:"));
        cmbEspecialidad = new JComboBox<>(new String[]{
                "Odontologo General (30 min)", "Ortodoncista (45 min)", "Endodoncista (60 min)"});
        formulario.add(cmbEspecialidad);

        panelInferior.add(formulario, BorderLayout.CENTER);

        JPanel botones = new JPanel(new FlowLayout());
        JButton btnNuevo = new JButton("Nuevo");
        JButton btnGuardar = new JButton("Guardar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");
        botones.add(btnNuevo); botones.add(btnGuardar);
        botones.add(btnEliminar); botones.add(btnLimpiar);
        panelInferior.add(botones, BorderLayout.SOUTH);

        add(panelInferior, BorderLayout.SOUTH);

        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                if (fila != -1) cargarEnFormulario(modelo.getOdontologoEn(fila));
            }
        });

        btnNuevo.addActionListener(e -> { idSeleccionado = null; limpiarFormulario(); });
        btnGuardar.addActionListener(e -> guardar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
    }

    private void cargarEnFormulario(Odontologo o) {
        idSeleccionado = o.getId();
        txtNombre.setText(o.getNombre());
        txtApellido.setText(o.getApellido());
        txtMatricula.setText(o.getMatricula());
        if (o instanceof OdontologoGeneral) cmbEspecialidad.setSelectedIndex(0);
        else if (o instanceof Ortodoncista) cmbEspecialidad.setSelectedIndex(1);
        else if (o instanceof Endodoncista) cmbEspecialidad.setSelectedIndex(2);
    }

    private void limpiarFormulario() {
        idSeleccionado = null;
        txtNombre.setText(""); txtApellido.setText("");
        txtMatricula.setText(""); cmbEspecialidad.setSelectedIndex(0);
        tabla.clearSelection();
    }

    private void guardar() {
        try {
            if (txtNombre.getText().isBlank() || txtApellido.getText().isBlank()
                    || txtMatricula.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "Completa todos los campos.",
                        "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Odontologo odontologo = switch (cmbEspecialidad.getSelectedIndex()) {
                case 0 -> new OdontologoGeneral(txtNombre.getText(), txtApellido.getText(), txtMatricula.getText());
                case 1 -> new Ortodoncista(txtNombre.getText(), txtApellido.getText(), txtMatricula.getText());
                case 2 -> new Endodoncista(txtNombre.getText(), txtApellido.getText(), txtMatricula.getText());
                default -> new OdontologoGeneral(txtNombre.getText(), txtApellido.getText(), txtMatricula.getText());
            };

            if (idSeleccionado != null) {
                odontologo.setId(idSeleccionado);
                servicio.modificarOdontologo(odontologo);
                JOptionPane.showMessageDialog(this, "Odontologo modificado correctamente.");
            } else {
                servicio.registrarOdontologo(odontologo);
                JOptionPane.showMessageDialog(this, "Odontologo registrado correctamente.");
            }

            modelo.actualizar(servicio.listarOdontologos());
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
            JOptionPane.showMessageDialog(this, "Selecciona un odontologo de la tabla.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int opcion = JOptionPane.showConfirmDialog(this,
                "Confirmar eliminacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcion != JOptionPane.YES_OPTION) return;

        try {
            servicio.eliminarOdontologo(idSeleccionado);
            modelo.actualizar(servicio.listarOdontologos());
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Odontologo eliminado.");
        } catch (ClinicaException ex) {
            JOptionPane.showMessageDialog(this, "[" + ex.getCodigo() + "] " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}