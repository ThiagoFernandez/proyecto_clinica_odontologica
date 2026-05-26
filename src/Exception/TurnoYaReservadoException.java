package Exception;

public class TurnoYaReservadoException extends ClinicaException {
    public TurnoYaReservadoException() {
        super("El odontologo ya tiene un turno en esa fecha y hora.", 203);
    }
}