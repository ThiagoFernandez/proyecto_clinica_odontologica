package Exception;

public class TurnoYaReservadoException extends RuntimeException {
    public TurnoYaReservadoException() {
        super("El odontologo ya tiene un turno en esa fecha y hora.");
    }
}