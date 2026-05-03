package Repositorio;
import java.util.List;

public interface iRepository <T>{
    void guardar(T obj); // Create
    T buscarPorId(Long id); // Read
    void actualizar(T obj); // Update
    void eliminar(Long id); // Delete
    List<T> listar(); // extra: El motivo es que este metodo permite lectura completa para validaciones y visualizacion
}
