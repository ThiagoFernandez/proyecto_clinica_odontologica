package Exception;

public class PacienteNoEncontradoException extends ClinicaException {
    public PacienteNoEncontradoException(Long id) {
        super("Paciente no encontrado con ID: " + id, 101);
    }
    public PacienteNoEncontradoException(String mensaje) {
        super(mensaje, 101);
    }
}