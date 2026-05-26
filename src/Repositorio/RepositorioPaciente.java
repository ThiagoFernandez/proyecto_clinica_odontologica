package Repositorio;

import Modelo.Domicilio;
import Modelo.Paciente;

import java.util.*;
import java.util.stream.Collectors;

public class RepositorioPaciente implements iRepository<Paciente>{
    Map<Long, Paciente> pacientes = new HashMap<>();

    @Override
    public void guardar(Paciente obj){
        pacientes.put(obj.getId(), obj);
    }

    @Override
    public Paciente buscarPorId(Long id){
        return pacientes.get(id);
    }

    @Override
    public void actualizar(Paciente obj){
        pacientes.put(obj.getId(), obj);
    }

    @Override
    public void eliminar(Long id){
        pacientes.remove(id);
    }

    @Override
    public List<Paciente> listar(){
        return new ArrayList<>(pacientes.values());
    }

    public Paciente buscarPorDni(String dni){
        return pacientes.values().stream()
                .filter(p -> p.getDni().equals(dni))
                .findFirst()
                .orElse(null);
    }

    public List<Paciente> listarPorLocalidad(String localidad) {
        return pacientes.values().stream()
                .filter(p -> p.getDomicilio() != null)
                .filter(p -> localidad.equals(p.getDomicilio().getLocalidad()))
                .collect(Collectors.toList());
    }

    public List<Paciente> listarOrdenadosPorApellido() {
        return pacientes.values().stream()
                .sorted()
                .collect(Collectors.toList());
    }
}