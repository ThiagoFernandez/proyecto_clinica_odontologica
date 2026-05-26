package Servicio;

import Modelo.EstadoTurno;
import Modelo.Odontologo;
import Modelo.Paciente;
import Modelo.Turno;
import Repositorio.RepositorioOdontologo;
import Repositorio.RepositorioPaciente;
import Repositorio.RepositorioTurno;
import Exception.PacienteNoEncontradoException;
import Exception.OdontologoNoEncontradoException;
import Exception.TurnoYaReservadoException;
import Exception.TurnoNoEncontradoException;
import Exception.DatoInvalidoException;
import Exception.ClinicaException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ServicioTurno {
    private final RepositorioTurno repoTurno;
    private final RepositorioPaciente repoPaciente;
    private final RepositorioOdontologo repoOdontologo;

    public ServicioTurno(RepositorioTurno repoTurno, RepositorioPaciente repoPaciente, RepositorioOdontologo repoOdontologo){
        this.repoTurno = repoTurno;
        this.repoPaciente = repoPaciente;
        this.repoOdontologo = repoOdontologo;
    }

    public void agendarTurno(Paciente p, Odontologo o, LocalDate fecha, LocalTime hora) {
        if (repoPaciente.buscarPorId(p.getId()) == null)
            throw new PacienteNoEncontradoException("No existe este paciente");
        if (repoOdontologo.buscarPorId(o.getId()) == null)
            throw new OdontologoNoEncontradoException("No existe este odontologo");
        if (!fecha.isAfter(LocalDate.now()))
            throw new DatoInvalidoException("fecha", " debe ser futura");
        if (repoTurno.existeTurno(o, fecha, hora))
            throw new TurnoYaReservadoException();

        //int duracion = o.calcularDuracionTurno(); al final lo dejo en presentacion esto

        Turno turno = new Turno(p, o, fecha, hora, EstadoTurno.PENDIENTE);
        repoTurno.guardar(turno);
        p.agregarTurno(turno); // listo aunque ahora tendria en tres lugares distintos el mismo objeto turno pero bueno, es para otros metodos
        o.agregarTurno(turno);
    }

    public Turno buscarTurno(Long id){
        return buscarOLanzar(id);
    }

    public void modificarTurno(Long id, LocalDate nuevaFecha, LocalTime nuevaHora) {
        Turno turno = buscarOLanzar(id);
        if (turno.getEstado() == EstadoTurno.CANCELADO || turno.getEstado() == EstadoTurno.COMPLETADO)
            throw new ClinicaException("No se puede modificar un turno cerrado", 204);
        if (!nuevaFecha.isAfter(LocalDate.now()))
            throw new DatoInvalidoException("fecha", "debe ser futura");
        if (repoTurno.existeTurno(turno.getOdontologo(), nuevaFecha, nuevaHora))
            throw new TurnoYaReservadoException();
        turno.setLocalDate(nuevaFecha);
        turno.setLocalTime(nuevaHora);
        repoTurno.actualizar(turno);
    }

    public void eliminarTurno(Long id) {
        Turno turno = buscarOLanzar(id);
        if (turno.getEstado() == EstadoTurno.PENDIENTE || turno.getEstado() == EstadoTurno.CONFIRMADO)
            throw new ClinicaException("No se puede eliminar un turno activo. Cancelalo primero.", 205);
        repoTurno.eliminar(id);
    }

    public List<Turno> listarTurnos(){return repoTurno.listar();}
    public List<Turno> listarPorPaciente(Paciente p) { return repoTurno.buscarPorPaciente(p); }
    public List<Turno> listarPorOdontologo(Odontologo o) { return repoTurno.buscarPorOdontologo(o); }
    public List<Turno> listarPorFecha(LocalDate fecha) { return repoTurno.buscarPorFecha(fecha); }
    public List<Turno> listarPorEstado(EstadoTurno estado) {return repoTurno.buscarPorEstado(estado);}

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

    public List<Turno> listarPorRangoFechas(LocalDate desde, LocalDate hasta) {
        return repoTurno.buscarPorRangoFechas(desde, hasta);
    }



    // auxiliar
    private Turno buscarOLanzar(Long id) {
        Turno turno = repoTurno.buscarPorId(id);
        if (turno == null) throw new TurnoNoEncontradoException(id);
        return turno;
    }
}
