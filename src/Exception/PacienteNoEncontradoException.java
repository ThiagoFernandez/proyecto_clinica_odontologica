package Exception;

public class PacienteNoEncontradoException extends RuntimeException {
    public PacienteNoEncontradoException(Long id) {
        super("Paciente no encontrado con ID: " + id);
    }
    public PacienteNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}