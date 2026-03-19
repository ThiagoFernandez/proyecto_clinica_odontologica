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

    public String toString(){
        return "Datos del turno " + id + "\n"+
        "Paciente: " + paciente.getNombre() + " " + paciente.getApellido() + "\n"+
        "Odontologo: " + odontologo.getNombre() + " " + odontologo.getApellido() + "\n"+
        "Fecha: " + fecha + "\n"+
        "Hora: " + hora + "\n"+
        "Estado: " + estado;
    }

    // setters

    /** no estoy seguro si conviene dejar que el id del turno se pueda modificar, ya que es un dato unico que lo identifica, pero lo dejo por las dudas
     * public void setId(Long id){
     this.id = id;
     }
     */

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public  void setOdontologo(Odontologo odontologo) {
        this.odontologo = odontologo;
    }
     public void setLocalDate(LocalDate fecha) {
         this.fecha = fecha;
     }

     public void setLocalTime(LocalTime hora) {
        this.hora = hora;
    }

    public void setEstado(EstadoTurno estado) {
        this.estado = estado;
    }

     // getters

     public Long getId() {
         return id;
     }

     public Paciente getPaciente() {
         return paciente;
     }

     public Odontologo getOdontologo() {
         return odontologo;
     }

     public LocalDate getFecha() {
         return fecha;
     }

     public LocalTime getHora() {
         return hora;
     }

     public EstadoTurno getEstado() {
         return estado;
     }


}
