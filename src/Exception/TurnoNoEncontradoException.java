package Exception;

public class TurnoNoEncontradoException extends ClinicaException {
    public TurnoNoEncontradoException(Long id) {
        super("Turno no encontrado con ID: " + id, 103);
    }
}