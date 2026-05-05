package Exception;

public class DniDuplicadoException extends RuntimeException {
    public DniDuplicadoException(String dni) {
        super("Ya existe un paciente con DNI: " + dni);
    }
}