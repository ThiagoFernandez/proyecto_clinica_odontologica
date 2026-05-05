package Servicio;

import Modelo.EstadoTurno;
import Modelo.Paciente;
import Modelo.Turno;
import Repositorio.RepositorioPaciente;
import Repositorio.RepositorioTurno;
import java.util.List;
import Exception.DniDuplicadoException;
import Exception.PacienteNoEncontradoException;

public class ServicioPaciente { // encargado de pacientes
    private final RepositorioPaciente repoPaciente;
    private final RepositorioTurno repoTurno;

    public ServicioPaciente(RepositorioPaciente repoPaciente, RepositorioTurno repoTurno){
        this.repoPaciente = repoPaciente;
        this.repoTurno = repoTurno;
    }

    public void registrarPaciente(Paciente paciente) {

        Paciente existente = repoPaciente.buscarPorDni(paciente.getDni());

        if (existente != null) {
            throw new DniDuplicadoException(existente.getDni());
        }

        repoPaciente.guardar(paciente);
    }

    public Paciente buscarPaciente(Long id){
        Paciente paciente = repoPaciente.buscarPorId(id);
        if (paciente == null){
            throw new PacienteNoEncontradoException(id);
        }
        return paciente;
    }

    public Paciente buscarPorDni(String dni) {
        Paciente paciente = repoPaciente.buscarPorDni(dni);
        if (paciente == null) throw new PacienteNoEncontradoException("No existe paciente con DNI: " + dni);
        return paciente;
    }

    public List<Paciente> listarPacientes(){
        return repoPaciente.listar();
    }

    public void modificarPaciente(Paciente paciente) {

        Paciente existente = repoPaciente.buscarPorId(paciente.getId());

        if (existente == null) {
            throw new PacienteNoEncontradoException("No existe este paciente");
        }

        Paciente otro = repoPaciente.buscarPorDni(paciente.getDni());

        if (otro != null && !otro.getId().equals(paciente.getId())) {
            throw new DniDuplicadoException(otro.getDni());
        }

        repoPaciente.actualizar(paciente);
    }

    public void eliminarPaciente(Long id) {

        Paciente paciente = repoPaciente.buscarPorId(id);

        if (paciente == null) {
            throw new PacienteNoEncontradoException(id);
        }

        List<Turno> turnos = repoTurno.buscarPorPaciente(paciente);

        for (Turno t : turnos) {
            if (t.esFuturo() && t.getEstado()!= EstadoTurno.CANCELADO) {
                throw new RuntimeException("El paciente tiene turnos futuros");
            }
        }

        repoPaciente.eliminar(id);
    }

    public List<Paciente> listarPorLocalidad(String localidad){
        return repoPaciente.listarPorLocalidad(localidad);
    }
    public List<Paciente> listarOrdenadosPorApellido(){return repoPaciente.listarOrdenadosPorApellido();}
}
