package Exception;

public class ClinicaException extends RuntimeException {
    private final int codigo;

    public ClinicaException(String mensaje, int codigo) {
        super(mensaje);
        this.codigo = codigo;
    }

    public ClinicaException(String mensaje) {
        super(mensaje);
        this.codigo = 0;
    }

    public int getCodigo() {
        return codigo;
    }
}