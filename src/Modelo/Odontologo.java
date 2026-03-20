package Modelo;

public class Odontologo {
    private Long id;
    private String nombre;
    private String apellido;
    private String matricula;

    public Odontologo(Long id, String nombre, String apellido, String matricula ){
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.matricula = matricula;
    }

    public String toString(){
        return "Datos del odontologo " + id + "\n"+
        "nombre: " + nombre + "\n"+
        "apellido: " + apellido + "\n"+
        "matricula: " + matricula;
    }

    // getters
    public Long getId(){
        return this.id;
    }

    public String getNombre(){
        return this.nombre;
    }

    public String getApellido(){
        return this.apellido;
    }

    public String getNombreCompleto(){
        return getNombre() + " " + getApellido();
        // mismo caso q Paciente
    }

    public String getMatricula(){
        return this.matricula;
    }

    // setters

    /** no estoy seguro si conviene dejar que el id del odontologo se pueda modificar, ya que es un dato unico que lo identifica, pero lo dejo por las dudas
     * public void setId(Long id){
     this.id = id;
     }
     */

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setApellido(String apellido){
        this.apellido = apellido;
    }

    public void setMatricula(String matricula){
        this.matricula = matricula;
    }

}
