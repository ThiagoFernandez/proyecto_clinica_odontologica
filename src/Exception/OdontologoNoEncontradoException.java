package Exception;

public class OdontologoNoEncontradoException extends ClinicaException {
    public OdontologoNoEncontradoException(Long id) {
        super("Odontologo no encontrado con ID: " + id, 102);
    }
    public OdontologoNoEncontradoException(String mensaje) {
        super(mensaje, 102);
    }
}