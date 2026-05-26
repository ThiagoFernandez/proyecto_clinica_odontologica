package Exception;

public class DniDuplicadoException extends ClinicaException {
    public DniDuplicadoException(String dni) {
        super("Ya existe un paciente con DNI: " + dni, 201);
    }
}