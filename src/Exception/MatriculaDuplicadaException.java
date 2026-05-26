package Exception;

public class MatriculaDuplicadaException extends ClinicaException {
    public MatriculaDuplicadaException(String matricula) {
        super("Ya existe un odontologo con matricula: " + matricula, 202);
    }
}