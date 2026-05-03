package Repositorio;

import Modelo.Odontologo;

import java.util.*;

public class RepositorioOdontologo implements iRepository<Odontologo> {
    Map<Long, Odontologo> odontologos = new HashMap<>();

    @Override
    public void guardar(Odontologo obj){
        odontologos.put(obj.getId(), obj);
    }

    @Override
    public Odontologo buscarPorId(Long id){
        return odontologos.get(id);
    }

    @Override
    public void actualizar(Odontologo obj){
        odontologos.put(obj.getId(), obj);
    }

    @Override
    public void eliminar(Long id){
        odontologos.remove(id);
    }

    @Override
    public List<Odontologo> listar(){
        return new ArrayList<>(odontologos.values());
    }

    public Odontologo buscarPorMatricula(String matricula){
        for (Odontologo o: odontologos.values()){
            if (Objects.equals(o.getMatricula(), matricula)) {
                return o;
            }
        }
        return null;
    }
}
