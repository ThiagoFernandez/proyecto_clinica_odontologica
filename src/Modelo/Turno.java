package Modelo;
import java.time.LocalDate;
import java.time.LocalTime;

public class Turno {
    private static Long contadorId = 1L;

    private Long id;
    private Paciente paciente;
    private Odontologo odontologo;
    private LocalDate fecha;
    private LocalTime hora;
    private EstadoTurno estado;

    public Turno(Paciente paciente, Odontologo odontologo, LocalDate fecha, LocalTime hora, EstadoTurno estado){
        this.id = contadorId++;
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
    }

    public String toString(){
        return "Datos del turno " + id + "\n"+
        "Paciente: " + paciente.getNombreCompleto() + "\n"+
        "Odontologo: " + odontologo.getNombre() + " " + odontologo.getApellido() + "\n"+
        "Fecha: " + fecha + "\n"+
        "Hora: " + hora + "\n"+
        "Estado: " + estado;
    }

    public boolean esFuturo(){
        if (this.fecha == null || this.hora == null) {
            return false; // Si no se ha establecido la fecha u hora, no se tiene en cuenta
        }
        if (this.fecha.isAfter(LocalDate.now())){
            return true;
        } else if (this.fecha.isEqual(LocalDate.now()) && this.hora.isAfter(LocalTime.now())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean estaDisponible(){
        return this.estado == EstadoTurno.PENDIENTE;
        // no me termina de convencer pero lo pide la consigna, lo ideal seria que el turno se considere disponible si no tiene un paciente asignado
        // pero el turno no puede existir sin paciente asi que no se si tiene sentido esa logica, lo dejo asi por las dudas
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
