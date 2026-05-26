package Exception;

public class DatoInvalidoException extends ClinicaException {
    public DatoInvalidoException(String campo, String motivo) {
        super("Dato invalido en " + campo + ": " + motivo, 301);
    }
}