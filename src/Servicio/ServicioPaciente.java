package Servicio;

import Modelo.EstadoTurno;
import Modelo.Paciente;
import Modelo.Turno;
import Repositorio.RepositorioPaciente;
import Repositorio.RepositorioTurno;
import java.util.List;

public class ServicioPaciente { // encargado de pacientes
    private RepositorioPaciente repoPaciente;
    private RepositorioTurno repoTurno;

    public ServicioPaciente(RepositorioPaciente repoPaciente, RepositorioTurno repoTurno){
        this.repoPaciente = repoPaciente;
        this.repoTurno = repoTurno;
    }

    public void registrarPaciente(Paciente paciente) {

        Paciente existente = repoPaciente.buscarPorDni(paciente.getDni());

        if (existente != null) {
            throw new RuntimeException("DNI duplicado");
        }

        repoPaciente.guardar(paciente);
    }

    public Paciente buscarPaciente(Long id){
        Paciente paciente = repoPaciente.buscarPorId(id);
        if (paciente == null){
            throw new RuntimeException("Paciente no encontrado");
        }
        return paciente;
    }

    public List<Paciente> listarPacientes(){
        return repoPaciente.listar();
    }

    public void modificarPaciente(Paciente paciente) {

        Paciente existente = repoPaciente.buscarPorId(paciente.getId());

        if (existente == null) {
            throw new RuntimeException("Paciente no encontrado");
        }

        Paciente otro = repoPaciente.buscarPorDni(paciente.getDni());

        if (otro != null && !otro.getId().equals(paciente.getId())) {
            throw new RuntimeException("DNI duplicado");
        }

        repoPaciente.actualizar(paciente);
    }

    public void eliminarPaciente(Long id) {

        Paciente paciente = repoPaciente.buscarPorId(id);

        if (paciente == null) {
            throw new RuntimeException("Paciente no encontrado");
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
}
