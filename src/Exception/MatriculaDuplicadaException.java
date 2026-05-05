package Exception;

public class MatriculaDuplicadaException extends RuntimeException {
    public MatriculaDuplicadaException(String matricula) {
        super("Ya existe un odontologo con matricula: " + matricula);
    }
}