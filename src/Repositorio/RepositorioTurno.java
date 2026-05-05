package Repositorio;

import Modelo.EstadoTurno;
import Modelo.Odontologo;
import Modelo.Paciente;
import Modelo.Turno;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return new ArrayList<>(turnos.values());
    }

    public List<Turno> buscarPorPaciente(Paciente paciente){
        List<Turno> resultados = new ArrayList<>();
        for (Turno t: turnos.values()){
            if (t.getPaciente().equals(paciente)){
                resultados.add(t);
            }
        }
        return resultados;
    }

    public List<Turno> buscarPorOdontologo(Odontologo odontologo){
        List<Turno> resultados = new ArrayList<>();
        for (Turno t: turnos.values()){
            if (t.getOdontologo().equals(odontologo)){
                resultados.add(t);
            }
        }
        return resultados;
    }

    public List<Turno> buscarPorFecha(LocalDate fecha){
        List<Turno> resultados = new ArrayList<>();
        for (Turno t: turnos.values()){
            if (t.getFecha().equals(fecha)){
                resultados.add(t);
            }
        }
        return resultados;
    }

    public List<Turno> buscarPorEstado(EstadoTurno estado) {
        List<Turno> resultado = new ArrayList<>();
        for (Turno t : turnos.values()) {
            if (t.getEstado() == estado) resultado.add(t);
        }
        return resultado;
    }

    public boolean existeTurno(Odontologo o, LocalDate fecha, LocalTime hora){
        for (Turno t : turnos.values()){
            if (
                    t.getOdontologo() != null &&
                    t.getFecha() != null &&
                    t.getHora() != null &&
                    t.getOdontologo().equals(o) &&
                    t.getFecha().equals(fecha) &&
                    t.getHora().equals(hora)
            ){
                return true;
            }
        }
        return false;
    }}
