package Repositorio;

import Modelo.EstadoTurno;
import Modelo.Odontologo;
import Modelo.Paciente;
import Modelo.Turno;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RepositorioTurno implements iRepository<Turno>{
    Map<Long, Turno> turnos = new HashMap<>();

    @Override
    public void guardar(Turno obj){
        turnos.put(obj.getId(), obj);
    }

    @Override
    public Turno buscarPorId(Long id){
        return turnos.get(id);
    }

    @Override
    public void actualizar(Turno obj){
        turnos.put(obj.getId(), obj);
    }

    @Override
    public void eliminar(Long id){
        turnos.remove(id);
    }

    @Override
    public List<Turno> listar(){
        return turnos.values().stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Turno> buscarPorPaciente(Paciente paciente){
        return turnos.values().stream()
                .filter(t -> t.getPaciente().equals(paciente))
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Turno> buscarPorOdontologo(Odontologo odontologo){
        return turnos.values().stream()
                .filter(t -> t.getOdontologo().equals(odontologo))
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Turno> buscarPorFecha(LocalDate fecha){
        return turnos.values().stream()
                .filter(t -> t.getFecha().equals(fecha))
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Turno> buscarPorEstado(EstadoTurno estado) {
        return turnos.values().stream()
                .filter(t -> t.getEstado() == estado)
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Turno> buscarPorRangoFechas(LocalDate desde, LocalDate hasta) {
        return turnos.values().stream()
                .filter(t -> t.getFecha() != null)
                .filter(t -> !t.getFecha().isBefore(desde) && !t.getFecha().isAfter(hasta))
                .sorted()
                .collect(Collectors.toList());
    }

    public boolean existeTurno(Odontologo o, LocalDate fecha, LocalTime hora){
        return turnos.values().stream()
                .filter(t -> t.getOdontologo() != null && t.getFecha() != null && t.getHora() != null)
                .filter(t -> t.getEstado() != EstadoTurno.CANCELADO)
                .anyMatch(t -> t.getOdontologo().equals(o)
                        && t.getFecha().equals(fecha)
                        && t.getHora().equals(hora));
    }
}