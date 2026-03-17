package Modelo;
import java.time.LocalDate;
import java.time.LocalTime;

public class Turno {
    private Long id;
    private Paciente paciente;
    private Odontologo odontologo;
    private LocalDate fecha;
    private LocalTime hora;
    private EstadoTurno estado;

    public Turno(Long id, Paciente paciente, Odontologo odontologo, LocalDate fecha, LocalTime hora, EstadoTurno estado){
        this.id = id;
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
    }

    public void mostrarDatosDelTurno(){
        System.out.println("Datos del turno " + id + "\n"+
        "Paciente: " + paciente.getNombre() + " " + paciente.getApellido() + "\n"+
        "Odontologo: " + odontologo.getNombre() + " " + odontologo.getApellido() + "\n"+
        "Fecha: " + fecha + "\n"+
        "Hora: " + hora + "\n"+
        "Estado: " + estado);
    }
}
