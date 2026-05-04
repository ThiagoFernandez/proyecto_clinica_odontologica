package Servicio;

import Modelo.EstadoTurno;
import Modelo.Odontologo;
import Modelo.Paciente;
import Modelo.Turno;
import Repositorio.RepositorioOdontologo;
import Repositorio.RepositorioPaciente;
import Repositorio.RepositorioTurno;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ServicioTurno {
    private RepositorioTurno repoTurno;
    private RepositorioPaciente repoPaciente;
    private RepositorioOdontologo repoOdontologo;

    public ServicioTurno(RepositorioTurno repoTurno, RepositorioPaciente repoPaciente, RepositorioOdontologo repoOdontologo){
        this.repoTurno = repoTurno;
        this.repoPaciente = repoPaciente;
        this.repoOdontologo = repoOdontologo;
    }

    public void agendarTurno(Paciente p, Odontologo o, LocalDate fecha, LocalTime hora) {
        if (repoPaciente.buscarPorId(p.getId()) == null)
            throw new RuntimeException("Paciente no encontrado");
        if (repoOdontologo.buscarPorId(o.getId()) == null)
            throw new RuntimeException("Odontologo no encontrado");
        if (!fecha.isAfter(LocalDate.now()))
            throw new RuntimeException("La fecha del turno debe ser futura");
        if (repoTurno.existeTurno(o, fecha, hora))
            throw new RuntimeException("El odontologo ya tiene turno en ese horario");

        //int duracion = o.calcularDuracionTurno(); al final lo dejo en presentacion esto

        Turno turno = new Turno(p, o, fecha, hora, EstadoTurno.PENDIENTE);
        repoTurno.guardar(turno);
    }

    public Turno buscarTurno(Long id){
        return buscarOLanzar(id);
    }

    public void modificarTurno(Long id, LocalDate nuevaFecha, LocalTime nuevaHora) {
        Turno turno = buscarOLanzar(id);
        if (turno.getEstado() == EstadoTurno.CANCELADO || turno.getEstado() == EstadoTurno.COMPLETADO)
            throw new RuntimeException("No se puede modificar un turno cerrado");
        if (!nuevaFecha.isAfter(LocalDate.now()))
            throw new RuntimeException("La nueva fecha debe ser futura");
        if (repoTurno.existeTurno(turno.getOdontologo(), nuevaFecha, nuevaHora))
            throw new RuntimeException("El odontologo ya tiene turno en ese horario");
        turno.setLocalDate(nuevaFecha);
        turno.setLocalTime(nuevaHora);
        repoTurno.actualizar(turno);
    }

    public void eliminarTurno(Long id) {
        Turno turno = buscarOLanzar(id);
        if (turno.getEstado() == EstadoTurno.PENDIENTE || turno.getEstado() == EstadoTurno.CONFIRMADO)
            throw new RuntimeException("No se puede eliminar un turno activo. Cancelalo primero.");
        repoTurno.eliminar(id);
    }

    public List<Turno> listarTurnos(){return repoTurno.listar();}
    public List<Turno> listarPorPaciente(Paciente p) { return repoTurno.buscarPorPaciente(p); }
    public List<Turno> listarPorOdontologo(Odontologo o) { return repoTurno.buscarPorOdontologo(o); }
    public List<Turno> listarPorFecha(LocalDate fecha) { return repoTurno.buscarPorFecha(fecha); }

    // podria hacer un solo metodo y pasarle otro parametro para no tener q escribir lo mismo pero cambiando el estado aunque para el usuario debe ser mas facil asi como esta
    public void cancelarTurno(Long id){
        Turno turno = buscarOLanzar(id);
        turno.setEstado(EstadoTurno.CANCELADO);
        repoTurno.actualizar(turno);
    }

    public void confirmarTurno(Long id){
        Turno turno = buscarOLanzar(id);
        turno.setEstado(EstadoTurno.CONFIRMADO);
        repoTurno.actualizar(turno);
    }

    public void completarTurno(Long id){
        Turno turno = buscarOLanzar(id);
        turno.setEstado(EstadoTurno.COMPLETADO);
        repoTurno.actualizar(turno);
    }

    // auxiliar
    private Turno buscarOLanzar(Long id) {
        Turno turno = repoTurno.buscarPorId(id);
        if (turno == null) throw new RuntimeException("Turno no encontrado");
        return turno;
    }
}
