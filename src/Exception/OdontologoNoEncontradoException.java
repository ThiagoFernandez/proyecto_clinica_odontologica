package Exception;

public class OdontologoNoEncontradoException extends RuntimeException {
    public OdontologoNoEncontradoException(Long id) {
        super("Odontologo no encontrado con ID: " + id);
    }
    public OdontologoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}