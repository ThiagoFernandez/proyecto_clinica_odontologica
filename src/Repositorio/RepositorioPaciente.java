package Repositorio;

import Modelo.Domicilio;
import Modelo.Paciente;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositorioPaciente implements iRepository<Paciente>{
    Map<Long, Paciente> pacientes = new HashMap<>();

   @Override
    public void guardar(Paciente obj){ // despues tendria q agregar una validacion para q no hayan dos pacientes con todos los datos iguales a excepcion del id
       pacientes.put(obj.getId(), obj);
   }

   @Override
    public Paciente buscarPorId(Long id){ // dar una validacion de si existe el id para evitar exepciones
       return pacientes.get(id);
   }

   @Override
    public void actualizar(Paciente obj){ // podria validar q los datos no sean identicos pero no seria ningun problema q lo sean
       pacientes.put(obj.getId(), obj);
   }

   @Override
    public void eliminar(Long id){ // lo mismo, validar q exista la key para evitar exceptiones
       pacientes.remove((id)); // tendria q ver si actualizo los id de los de la derecha corriendolos todos una posi a la izquierda o simplemente lo dejo asi y se perdio ese Long
   }

   @Override
    public List<Paciente> listar(){
       return new ArrayList<>(pacientes.values());
   }

    public Paciente buscarPorDni(String dni){
       for (Paciente p: pacientes.values()){
           if(p.getDni().equals(dni)){
               return p;
           }
       }
       return null;
   }

    public List<Paciente> listarPorLocalidad(String localidad) {
        List<Paciente> pacientesLocalidad = new ArrayList<>();
        for (Paciente p : pacientes.values()) {
            Domicilio dom = p.getDomicilio();
            if (dom != null && dom.getLocalidad() != null && dom.getLocalidad().equals(localidad)) { // para evitar pacientes con Domicilio null aunque no tendria q haber ninguno null ya q uso el constructor con parametros
                pacientesLocalidad.add(p);
            }
        }
        return pacientesLocalidad;
    }
}
