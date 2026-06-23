package Vista.modelos;

import Modelo.Turno;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TablaTurnosModel extends AbstractTableModel {
    private final String[] columnas = {"ID", "Paciente", "Odontologo", "Fecha", "Hora", "Estado"};
    private List<Turno> turnos;

    public TablaTurnosModel(List<Turno> turnos) {
        this.turnos = turnos;
    }

    @Override public int getRowCount() { return turnos.size(); }
    @Override public int getColumnCount() { return columnas.length; }
    @Override public String getColumnName(int col) { return columnas[col]; }
    @Override public boolean isCellEditable(int fila, int col) { return false; }

    @Override
    public Object getValueAt(int fila, int col) {
        Turno t = turnos.get(fila);
        return switch (col) {
            case 0 -> t.getId();
            case 1 -> t.getPaciente().getNombreCompleto();
            case 2 -> t.getOdontologo().getNombreCompleto();
            case 3 -> t.getFecha();
            case 4 -> t.getHora();
            case 5 -> t.getEstado();
            default -> null;
        };
    }

    public Turno getTurnoEn(int fila) {
        return turnos.get(fila);
    }

    public void actualizar(List<Turno> nuevos) {
        this.turnos = nuevos;
        fireTableDataChanged();
    }
}