package Vista.modelos;

import Modelo.Odontologo;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TablaOdontologosModel extends AbstractTableModel {
    private final String[] columnas = {"ID", "Nombre Completo", "Matricula", "Especialidad", "Duracion"};
    private List<Odontologo> odontologos;

    public TablaOdontologosModel(List<Odontologo> odontologos) {
        this.odontologos = odontologos;
    }

    @Override public int getRowCount() { return odontologos.size(); }
    @Override public int getColumnCount() { return columnas.length; }
    @Override public String getColumnName(int col) { return columnas[col]; }
    @Override public boolean isCellEditable(int fila, int col) { return false; }

    @Override
    public Object getValueAt(int fila, int col) {
        Odontologo o = odontologos.get(fila);
        return switch (col) {
            case 0 -> o.getId();
            case 1 -> o.getNombreCompleto();
            case 2 -> o.getMatricula();
            case 3 -> o.getClass().getSimpleName();
            case 4 -> o.calcularDuracionTurno() + " min";
            default -> null;
        };
    }

    public Odontologo getOdontologoEn(int fila) {
        return odontologos.get(fila);
    }

    public void actualizar(List<Odontologo> nuevos) {
        this.odontologos = nuevos;
        fireTableDataChanged();
    }
}