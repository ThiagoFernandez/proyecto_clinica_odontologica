package Servicio;

import Modelo.EstadoTurno;
import Modelo.Odontologo;
import Modelo.Turno;
import Repositorio.RepositorioOdontologo;
import Repositorio.RepositorioTurno;
import Exception.MatriculaDuplicadaException;
import Exception.OdontologoNoEncontradoException;
import Exception.ClinicaException;

import java.util.List;

public class ServicioOdontologo {
    private final RepositorioOdontologo repoOdontologo;
    private final RepositorioTurno repoTurno;

    public ServicioOdontologo(RepositorioOdontologo repoOdontologo, RepositorioTurno repoTurno) {
        this.repoOdontologo = repoOdontologo;
        this.repoTurno = repoTurno;
    }

    public void registrarOdontologo(Odontologo odontologo){
        Odontologo existente = repoOdontologo.buscarPorMatricula(odontologo.getMatricula());

        if (existente!=null) {
            throw new MatriculaDuplicadaException(odontologo.getMatricula());
        }
        repoOdontologo.guardar(odontologo);
    }

    public Odontologo buscarOdontologo(Long id){
        return buscarOLanzar(id);
    }

    public List<Odontologo> listarOdontologos(){return repoOdontologo.listar();}
    public List<Odontologo> listarPorEspecialidad(Class<?> especialidad) {return repoOdontologo.buscarPorEspecialidad(especialidad);}
    public void modificarOdontologo(Odontologo odontologo) {

        buscarOLanzar(odontologo.getId());


        Odontologo otro = repoOdontologo.buscarPorMatricula(odontologo.getMatricula());

        if (otro != null && !otro.getId().equals(odontologo.getId())) {
            throw new MatriculaDuplicadaException(otro.getMatricula());
        }

        repoOdontologo.actualizar(odontologo);
    }

    public void eliminarOdontologo(Long id){
        Odontologo odontologo = buscarOLanzar(id);

        List<Turno> turnos = repoTurno.buscarPorOdontologo(odontologo);

        for (Turno t: turnos){
            if(t.esFuturo() && t.getEstado()!= EstadoTurno.CANCELADO){
                throw new ClinicaException("El odontologo tiene turnos futuros activos", 206);
            }
        }

        repoOdontologo.eliminar(id);
    }

    // auxiliar
    private Odontologo buscarOLanzar(Long id) {
        Odontologo odontologo = repoOdontologo.buscarPorId(id);
        if (odontologo == null) throw new OdontologoNoEncontradoException(id);
        return odontologo;
    }
}
