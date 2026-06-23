package Vista.modelos;

import Modelo.Paciente;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TablaPacientesModel extends AbstractTableModel {
    private final String[] columnas = {"ID", "Nombre Completo", "DNI", "Email", "Localidad"};
    private List<Paciente> pacientes;

    public TablaPacientesModel(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    @Override public int getRowCount() { return pacientes.size(); }
    @Override public int getColumnCount() { return columnas.length; }
    @Override public String getColumnName(int col) { return columnas[col]; }
    @Override public boolean isCellEditable(int fila, int col) { return false; }

    @Override
    public Object getValueAt(int fila, int col) {
        Paciente p = pacientes.get(fila);
        return switch (col) {
            case 0 -> p.getId();
            case 1 -> p.getNombreCompleto();
            case 2 -> p.getDni();
            case 3 -> p.getEmail();
            case 4 -> p.getDomicilio() != null ? p.getDomicilio().getLocalidad() : "";
            default -> null;
        };
    }

    public Paciente getPacienteEn(int fila) {
        return pacientes.get(fila);
    }

    public void actualizar(List<Paciente> nuevos) {
        this.pacientes = nuevos;
        fireTableDataChanged();
    }
}